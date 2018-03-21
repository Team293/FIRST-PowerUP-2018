package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.OI;
import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TankDriveDefault extends Command {
	private double left;
	private double right;
	private double outleft;
	private double outright;
    public TankDriveDefault() {
    	requires(Robot.driveTrain);// Use requires() here to declare subsystem dependencies
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Robot.driveTrain.squaredTankDrive(OI.rightStick.getY(), OI.leftStick.getY());
    	// UGLY define of limit points
    	double in1 = 0.35;
    	double out1 = 0.25;
        // Get raw stick values
    	right = OI.rightStick.getY();
    	left  = OI.leftStick.getY();
    	// Get signs of each
    	double ls = Math.signum(left);
    	double rs = Math.signum(right);
    	// Check each stick if below threshold
    	if ( Math.abs(right) < in1 ) {
    		outright = right * (out1 / in1 ); // low gain range
    	} else { 
    		outright = rs * (out1 + ( (rs * right)- in1 )*(1-out1)/(1-in1));
    	}
    	// Same as left side
    	if ( Math.abs(left) < in1 ) {
    		outleft = left * (out1 / in1 ); // low gain range
    	} else { 
    		outleft = ls * (out1 + ( (ls * left)- in1 )*(1-out1)/(1-in1));
    	}
    	right = outright;
    	left = outleft;
    	
//    	(OI.rightStick.getY() > 0){
//    		right = OI.rightStick.getY()*OI.rightStick.getY();
//    	}
//    	else {
//    		right = -1*OI.rightStick.getY()*OI.rightStick.getY();
//    	}
//    	if (OI.leftStick.getY() > 0){
//    		left = OI.leftStick.getY()*OI.leftStick.getY();
//    	}
    	
//    	else {
//    		left = -1*OI.leftStick.getY()*OI.leftStick.getY();
//    	}
    	/*if (Math.abs(left-right)<=.1){
    		if (OI.leftStick.getY() > 0){
        		left = OI.leftStick.getY()+OI.leftStick.getY();
        	}
        	else {
        		left = -1*OI.leftStick.getY()*OI.leftStick.getY();
    	}
    	*/
    	//Robot.TrainofDriving.tankdrive((-1*left), (-1*right));
    	Robot.driveTrain.feedForwardEncoderDrive((-1*left), (-1*right));
    	//Robot.TrainofDriving.encoderDrive(left, right);
//    	Robot.TrainofDriving.encoderDrive(-1*(outright), -1*(outleft));
    	
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
