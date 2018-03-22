package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightDistanceChristian extends Command {
	double speed;
	double distanceToTravel;
    public DriveStraightDistanceChristian(double speed, double distanceToTravel) {
        // Use requires() here to declare subsystem dependencies
      	requires(Robot.driveTrain);
      	this.speed = speed;
      	this.distanceToTravel = distanceToTravel;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.feedForwardEncoderDrive(.25, .25);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.driveTrain.readEnc()[0]>distanceToTravel;
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
