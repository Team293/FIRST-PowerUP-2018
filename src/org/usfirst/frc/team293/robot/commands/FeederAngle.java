package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.OI;
import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FeederAngle extends Command {
	double position;
    public FeederAngle(double positionToRotateTo) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.Feeder);
    	position = positionToRotateTo*1.0*2048.0/360.0;
    	requires(Robot.Pinchy);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.Feeder.moveToPosition(position);
    	Robot.Pinchy.unpinch();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.Feeder.isInPosition());
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
