package org.usfirst.frc.team293.robot.subsystems;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import java.io.BufferedWriter;


import org.usfirst.frc.team293.robot.OI;
import org.usfirst.frc.team293.robot.Robot;

// import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * AutoLogger 
 * Records driver controls along with robot drivetrain encoders, yaw angle, and yaw rate.
 * This routine is designed specifically for genrating autonomous trajectory paths for replaying
 * during the autonomous portion of an event.  Several such files may be generated to be saved for
 * replay, depending upon the game situation and strategy desired.  This class also saves the buttons!
 */
public class AutoLogger extends Subsystem {
    // Methods here are called from Commands
	
	// The data is recorded as a CSV file, read back via ReplayFile.java
	public static simpleCsvLogger logObj = new simpleCsvLogger();
	
	DateFormat df;   // holds day-month-year_hour.min.sec
	FileWriter writer;
	public double timestamp = 0;
	public double initTime = 0;
	long startTime; // initial time hack
	long rightNow;  // current time hack
	// initial yaw angle is of no interest for auto trajectories!
	// We start field-relative, not North relative.
	double startYaw; 
	
	String [] names = {"time",
			"joy_left",
			"joy_right",
			"yaw",
			"yaw_rate",
			"enc_left",
			"enc_right",
			"launch1b1",
			"launch1b2",
			"launch1b3",
			"launch1b4",
			"launch1b5",
			"launch1b6",
			"launch1b7",
			"launch1b8",
			"launch1b9",
			"launch1b10",
			"launch1b11",
			"launch1b12",
			"launch2b1",
			"launch2b11",
			"launch2b12",
			};
	String [] units = {"sec",
			"defl",
			"defl",
			"deg",
			"deg/s",
			"in",
			"in",
			"bool",
			"bool",
			"bool",
			"bool",
			"bool",
			"bool",
			"bool",
			"bool",
			"bool",
			"bool",
			"bool",
			"bool",
			"bool",
			"bool",
			"bool"};
	double [] dataChans = new double[22]; // storage vector for file writing
	
	
	public AutoLogger(){
		// Start date ID with year, so they sort well
		df = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ssa");
        df.setTimeZone(TimeZone.getTimeZone("US/Eastern"));    
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());	
    }
    
    public void writeFileStart(){
    	// Calls simpleCsvLogger to open a file, and write first 2 lines
    	logObj.init(names,units);
    	startTime = System.currentTimeMillis();
    	//startYaw = Robot.imu.getAngleX(); // initial yaw angle
    	startYaw = 0.0;
    }
    
    public void writeFileLine(){
    	rightNow = System.currentTimeMillis();
    	timestamp = (double) (rightNow - startTime);
    	dataChans[0] = timestamp/1000.; // use seconds
    	dataChans[1] = 1.0*OI.leftStick.getY(); 
    	dataChans[2] = 1.0*OI.rightStick.getY(); 
    	dataChans[3] = Robot.driveTrain.pigeonImu.getFusedHeading() - startYaw; // RELATIVE yaw angle
    	dataChans[4] = 0.0; // yaw rate
    	dataChans[5] = Robot.driveTrain.leftEncoder.getDistance(); 
    	dataChans[6] = Robot.driveTrain.rightEncoder.getDistance(); 
    // Now get ALL OF THE BUTTONS since we don't know what's active and what isn't!
    	// This rather ugly cast does the following: check boolean if true or not, return 1 or 0,
    	// then recast that integer to a double for storage as data that gets dumped onto the auto log.
    	// Hey, at least I explained this, instead of just being clever.
    	for (int i=0; i<12; i++ ) {
    		dataChans[7+i] =  (double) (OI.launch1[i+1].get() ? 1 : 0);
    	}
    	// The second joystick/launchpad only uses 4 buttons, so we'll limit ourselves to just those.
  //  	dataChans[19] = (double) (OI.launch2[1].get() ? 1 : 0);
   // 	dataChans[20] = (double) (OI.launch2[11].get() ? 1 : 0);
   // 	dataChans[21] = (double) (OI.launch2[12].get() ? 1 : 0);
    // All done for the 2018 robot.  Write all these doubles now!
    	logObj.writeData( dataChans );
    }
    
    public void closeFile(){
    	logObj.close();
    }
}

/*
 *******************************************************************************************
 * Copyright (C) 2017 FRC Team 1736 Robot Casserole - www.robotcasserole.org
 *******************************************************************************************
 *
 * This software is released under the MIT Licence - see the license.txt
 *  file in the root of this repo.
 *
 * Non-legally-binding statement from Team 1736:
 *  Thank you for taking the time to read through our software! We hope you
 *   find it educational and informative! 
 *  Please feel free to snag our software for your own use in whatever project
 *   you have going on right now! We'd love to be able to help out! Shoot us 
 *   any questions you may have, all our contact info should be on our website
 *   (listed above).
 *  If you happen to end up using our software to make money, that is wonderful!
 *   Robot Casserole is always looking for more sponsors, so we'd be very appreciative
 *   if you would consider donating to our club to help further STEM education.
 */


/**
 * DESCRIPTION: <br>
 * Provides an API for FRC 1736 Robot Casserole datalogging on the robot during testing or matches.
 * Will write lines into a CSV file with a unique name between calls to init() and close().
 * output_dir is hardcoded to point to a specific 2016 folder on a flash drive connected to the
 * roboRIO. <br>
 * <br>
 * USAGE:
 * <ol>
 * <li>Instantiate Class</li>
 * <li>Create global variables containing arrays of strings to represent the column (data vector)
 * names and units</li>
 * <li>During teleop init or autonomous init, call the init() function to start logging data to a
 * new file.</li>
 * <li>Once per loop, call the writeData() method with the full list of values to write to file (all
 * must be converted to doubles).</li>
 * <li>During DisabledInit, call the close() method to close out any file which was being written to
 * while the robot was doing something.</li>
 * <li>Post-match or -practice, extract the data logs from the USB drive(maybe using FTP?) and view
 * with excel or your favourite software.</li>
 * </ol>
 * 
 * 
 */

class simpleCsvLogger {

    long log_write_index;
    String log_name = null;
//    String output_dir = "/U/data_captures/"; // USB drive is mounted to /U on roboRIO
//    String output_dir = ""; // USB drive is mounted to /U on roboRIO
    String output_dir = "/home/lvuser/"; // USB drive is mounted to /U on roboRIO
//    String output_dir = "/U/LogFiles/"; // USB drive is mounted to /U on roboRIO
    BufferedWriter log_file = null;
    boolean log_open = false;
    DateFormat df;



    /**
     * Determines a unique file name, and opens a file in the data captures directory and writes the
     * initial lines to it.
     * 
     * @param data_fields A set of strings for signal names to write into the file
     * @param units_fields A set of strings for signal units to write into the file
     * @return 0 on successful log open, -1 on failure
     */
    public int init(String[] data_fields, String[] units_fields) {

        if (log_open) {
            System.out.println("Warning - log is already open!");
//            this.forceSync(); // aborted attempt to synch if we toggle?
//            this.close();      
            return 0;
        }

        log_open = false;
        System.out.println("Initalizing Log file...");
        System.out.println("Warning - Initializing log file.");
        df = new SimpleDateFormat("yyy-MM-dd_HH.mm.ss");
        df.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
        try {
            // Reset state variables
            log_write_index = 0;

            // Determine a unique file name
//            log_name = output_dir + "log_" + getDateTimeString() + ".csv";
//            log_name = "log_" + getDateTimeString() + ".csv";
            log_name = output_dir + "log_" + df.format(new Date()) + ".csv";
            // Open File
            FileWriter fstream = new FileWriter(log_name, true);
            log_file = new BufferedWriter(fstream);

            // Write user-defined header line
            for (String header_txt : data_fields) {
                log_file.write(header_txt + ", ");
            }
            // End of line
            log_file.write("\n");


            // Write user-defined units line
            for (String header_txt : units_fields) {
                log_file.write(header_txt + ", ");
            }
            // End of line
            log_file.write("\n");

        }
        // Catch ALL the errors!!!
        catch (IOException e) {
            System.out.println("Error initializing log file: " + e.getMessage());
            return -1;
        }
        System.out.println("done!");
        log_open = true;
        return 0;

    }



    /**
     * Write a list of doubles to the output file, assuming it's open. Creates a new line in the
     * .csv log file.
     * 
     * @param data_elements Values to write (any number of doubles, each as its own argument).
     *        Should have the same number of arguments here as signal names/units set during the
     *        call to init()
     * @return 0 on write success, -1 on failure.
     */
    public int writeData(double... data_elements) {
        String line_to_write = "";

        if (log_open == false) {
            System.out.println("Error - Log is not yet opened, cannot write!");
            return -1;
        }

        try {

            // Write user-defined data
            for (double data_val : data_elements) {
//                line_to_write = line_to_write.concat(Double.toString(data_val) + ", ");
                line_to_write = line_to_write.concat(String.format("%.4g",data_val) + ", ");            }

            // End of line
            line_to_write = line_to_write.concat("\n");

            // write constructed string out to file
            log_file.write(line_to_write);

        }
        // Catch ALL the errors!!!
        catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
            return -1;
        }

        log_write_index++;
        return 0;
    }



    /**
     * Clears the buffer in memory and forces things to file. Generally a good idea to use this as
     * infrequently as possible (because it increases logging overhead), but definitely use it
     * before the roboRIO might crash without a proper call to the close() method (during brownout,
     * maybe?)
     * 
     * @return Returns 0 on flush success or -1 on failure.
     */
    public int forceSync() {
        if (log_open == false) {
            System.out.println("Error - Log is not yet opened, cannot sync!");
            return -1;
        }
        try {
            log_file.flush();
        }
        // Catch ALL the errors!!!
        catch (IOException e) {
            System.out.println("Error flushing IO stream file: " + e.getMessage());
            return -1;
        }

        return 0;

    }

    /**
     * Closes the log file and ensures everything is written to disk. init() must be called again in
     * order to write to a new file.
     * 
     * @return -1 on failure to close, 0 on success
     */
    public int close() {

        if (log_open == false) {
            System.out.println("Warning - Log is not yet opened, nothing to close.");
            return 0;
        }

        try {
            log_file.close();
            log_open = false;
            System.out.println("Warning - Closing log file.");
        }
        // Catch ALL the errors!!!
        catch (IOException e) {
            System.out.println("Error Closing Log File: " + e.getMessage());
            return -1;
        }
        return 0;

    }


    private String getDateTimeString() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
        df.setTimeZone(TimeZone.getTimeZone("US/Central"));
        return df.format(new Date());
    }

}
