package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.OI;
import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTankDefault extends Command {
	private double left;
	private double right;
	private double outleft;
	private double outright;
    public DriveTankDefault() {
    	requires(Robot.driveTrain);// Use requires() here to declare subsystem dependencies
    }

    // Called just before this Command runs the first time
    protected void initialize() {
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

    	right = OI.rightStick.getY();
    	left  = OI.leftStick.getY();
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
    	
    	Robot.driveTrain.feedForwardEncoderDrive((-1*outleft), (-1*outright));
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
