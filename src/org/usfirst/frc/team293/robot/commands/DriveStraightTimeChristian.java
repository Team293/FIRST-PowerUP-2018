package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightTimeChristian extends Command {
		double timeout;
		private double speed;
	/**
	 * @param speed The speed from -1 to 1 that gets converted to encoders
	 * @param seconds the time in seconds to drive
	 */
    public DriveStraightTimeChristian(double speed, double seconds) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
        timeout = seconds;
        this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	setTimeout(timeout);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.gyroStraight(.5);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
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
