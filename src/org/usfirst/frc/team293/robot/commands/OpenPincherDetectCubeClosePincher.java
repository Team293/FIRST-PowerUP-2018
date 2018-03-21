package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OpenPincherDetectCubeClosePincher extends Command {

    public OpenPincherDetectCubeClosePincher() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.Pinchy);
    	requires(Robot.FeedSensors);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.Pinchy.unpinch();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       // return (Robot.FeedSensors.getPhotoSwitch());
    	return(true);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.Pinchy.pinch();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
