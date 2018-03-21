package org.usfirst.frc.team293.robot.subsystems;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.usfirst.frc.team293.robot.OI;
import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The autologger class logs the data we need to play back an auto
 */
public class autoLogger extends Subsystem {

	DateFormat df;
	FileWriter writer;
	public double timestamp = 0;
	//public Timer time;
	public double initTime = 0;
	public autoLogger() {
		df = new SimpleDateFormat("dd-MMM-yyyy_hh.mm.ssa");
        df.setTimeZone(TimeZone.getTimeZone("US/Eastern"));
        
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
    /*
     * Write file header
     */
    public void writeFileStart() {
    	//initTime = time.get();
    	try {
	    	timestamp = 0;
	    	writer = new FileWriter("/home/lvuser/robotlogger"+df.format(new Date())+".csv");
			writer.append("timeStamp");
			writer.append(',');
			writer.append("leftRateSetpoint");
			writer.append(',');
			writer.append("rightRateSetpoint");
			writer.append(',');
			writer.append("angleSetpoint");
			writer.append(',');
			writer.append("angleRate");
			writer.append('\n');
	
			writer.flush();
			//writer.close();
    	}
		catch(IOException e) {
			e.printStackTrace();
		}
    }
    /**
     * Write data 
     */
    public void writeFileLine() {
    	try {
			writer.append(Double.toString(timestamp));
			writer.append(',');
			writer.append(Double.toString(-1.0*OI.leftStick.getY()));
			writer.append(',');
			writer.append(Double.toString(-1.0*OI.rightStick.getY()));
			writer.append(',');
			writer.append(Double.toString(Robot.imu.getYaw()));
			writer.append(',');
			writer.append(Double.toString(Robot.imu.getRate()));
			writer.append(',');
			writer.append(Double.toString(Robot.driveTrain.leftEncoder.getDistance()));
			writer.append(',');
			writer.append(Double.toString(Robot.driveTrain.rightEncoder.getDistance()));
			writer.append(',');
			writer.append(Double.toString(Robot.driveTrain.leftEncoder.getDistance()));
			writer.append(',');
			writer.append(Double.toString(Robot.driveTrain.leftEncoder.getDistance()));
			writer.append('\n');
	
			writer.flush();
			timestamp = timestamp+.02;
			//writer.close();
    	}
		catch (IOException e) {
			e.printStackTrace();
		}
    }
    /**
     * Closes the file and catches if an error occurs
     */
    public void closeFile() {
    	try {
    		writer.close();
    	}  	
    	catch(IOException e) {
    		e.printStackTrace();
    	}
    }
}

