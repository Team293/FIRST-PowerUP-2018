package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightDistanceChristian extends Command {
	private double speed;
	double distanceToTravel;
    public DriveStraightDistanceChristian(double speed, double distanceToTravel) {
        // Use requires() here to declare subsystem dependencies
      	requires(Robot.driveTrain);
      	this.speed = speed;
      	this.distanceToTravel = distanceToTravel;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.resetEnc();
    	Robot.driveTrain.resetGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (distanceToTravel - Robot.driveTrain.readEnc()[0] < 8){  //if we're at the last 8 inches
    		Robot.driveTrain.gyroStraight(-.35*speed);
    	} else {
    		Robot.driveTrain.gyroStraight(-speed);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.driveTrain.readEnc()[0]>(distanceToTravel - speed*10);
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
