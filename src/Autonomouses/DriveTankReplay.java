package Autonomouses;

import java.io.FileNotFoundException;

import org.usfirst.frc.team293.robot.OI;
import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import Autonomouses.ReplayFile;

/**
 *
 */
public class DriveTankReplay extends Command {
	private double left;
	private double right;
	private double outleft;
	private double outright;
	private int fnum;
	private double yaw0;
	
	public ReplayFile replayFile = new ReplayFile();
	
    public DriveTankReplay( int fileNum) {
    	fnum = fileNum;
    	requires(Robot.driveTrain);// Use requires() here to declare subsystem dependencies
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	try {
			replayFile.init(fnum);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // opens file for auto following, gets first two text lines
    	yaw0 = Robot.driveTrain.pigeonImu.getFusedHeading();
    }
    
    /**
     * Method to convert single slope linear input from joystick into linear input using 2 lines of different slope
     * @param right Raw input from right joystick from -1 to 1
     * @param left Raw input from left joystick from -1 to 1
     * @return Array containing processed left and right inputs 
     */
    public double[] stickCooker(double right, double left){
    	
		return new double[] {outright, outleft};
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	right = OI.rightStick.getY();
//    	left  = OI.leftStick.getY();
    	left  = replayFile.chanValues[1]; // joysticks; index 0 is time hack
    	right = replayFile.chanValues[2];
    	double left_enc  = replayFile.chanValues[3]; 
    	double right_enc = replayFile.chanValues[4];
    	double yaw       = replayFile.chanValues[5];
    	double yawRate   = replayFile.chanValues[6];

    	// Define constants for "cooked" joystick drive inputs to give creep/leap response
    	double in1 = 0.35;
    	double out1 = 0.25;
    	double ls = Math.signum(left); //gets signs of both inputs
    	double rs = Math.signum(right);
    	if ( Math.abs(right) < in1 ) {
    		outright = right * (out1 / in1 ); // low gain range
    	} else { 
    		outright = rs * (out1 + ( (rs * right)- in1 )*(1-out1)/(1-in1));
    	}
    	if ( Math.abs(left) < in1 ) {
    		outleft = left * (out1 / in1 ); // low gain range
    	} else { 
    		outleft = ls * (out1 + ( (ls * left)- in1 )*(1-out1)/(1-in1));
    	}
    	
    	// Stick input recreated from file data, now add feedback!
    	double l_enc_err = Robot.driveTrain.leftEncoder.getDistance() - left_enc;
    	double r_enc_err = Robot.driveTrain.rightEncoder.getDistance() - right_enc;
    	double ang_error = (Robot.driveTrain.pigeonImu.getFusedHeading() - yaw0) - yaw;
    	double Kp = 0.0; // make nonzero for feedback!
    	double Ka = 0.0;
    	outleft  += (Kp * l_enc_err) + (Ka * ang_error/2.);
    	outright += (Kp * r_enc_err) - (Ka * ang_error/2.);
    	// Baby won't you drive my car? Yes, I'm going to be a star!
    	Robot.driveTrain.feedForwardEncoderDrive((-1*outleft), (-1*outright));
    	
    	// Button button who's got the button?  Time to make the other stuff move.
    	for (int i=1; i< OI.numButtonFunctions; i++) {
    		OI.theButtons[i].applyButton( (int) replayFile.chanValues[6+i] );
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
