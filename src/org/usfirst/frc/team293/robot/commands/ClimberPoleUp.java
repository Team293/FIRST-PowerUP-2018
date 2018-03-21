package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimberPoleUp extends Command {
	boolean sensorDetect;
    public ClimberPoleUp() {
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.Climber.Up();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	sensorDetect = Robot.Climber.windOut();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return sensorDetect;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.Climber.stopWind();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.Climber.stopWind();
    }
}
