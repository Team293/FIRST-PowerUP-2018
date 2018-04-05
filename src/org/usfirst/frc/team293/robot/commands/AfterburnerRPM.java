package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.OI;
import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AfterburnerRPM extends Command {
	private double CodesPer100ms;
    public AfterburnerRPM(double InputRPM) {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.afterBurner);
    	CodesPer100ms = (InputRPM*31500);
    	//CodesPer100ms = (InputRPM*30000*4096/600);
    	//CodesPer100ms = (InputRPM*31500);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.afterBurner.EncoderShoot(CodesPer100ms);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.afterBurner.move(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.afterBurner.move(0);
    }
}
