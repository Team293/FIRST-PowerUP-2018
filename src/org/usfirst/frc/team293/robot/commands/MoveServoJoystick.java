package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.OI;
import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */

public class MoveServoJoystick extends Command {
	private boolean isFinished;
    public MoveServoJoystick() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.Climber.Up();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	isFinished = Robot.Climber.windOut();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.Climber.stopWind();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

