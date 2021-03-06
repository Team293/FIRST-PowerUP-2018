package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTurnGyroInPlace extends Command {
		double angle;
		double rate;
		boolean isFinished = false;
		/**
		 * 
		 * @param angle the total angle to travel
		 * @param rate Is the amount of degrees per iteration
		 */
    public DriveTurnGyroInPlace(double angle, double rate) {
    	this.angle = angle;
    	this.rate = rate;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.resetGyro();
    	Robot.driveTrain.resetEnc();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	isFinished = Robot.driveTrain.gyroTurnInPlace(angle, rate);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.tankdrive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
