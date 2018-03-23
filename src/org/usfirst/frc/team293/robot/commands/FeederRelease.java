package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.OI;
import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FeederRelease extends Command {
    	double rpm;
    	public FeederRelease(double FeederCP100MS) {
    		// Use requires() here to declare subsystem dependencies
    		requires(Robot.feeder);
    		rpm = FeederCP100MS;
    	}

    	// Called just before this Command runs the first time
    	@Override
    	protected void initialize() {
    	}

    	// Called repeatedly when this Command is scheduled to run
    	@Override
    	protected void execute() {
    			Robot.feeder.shoot(rpm);
    	}
    	

    	// Make this return true when this Command no longer needs to run execute()
    	@Override
    	protected boolean isFinished() { 
    		return false;
    	}

    	// Called once after isFinished returns true
    	@Override
    	protected void end() {
    		Robot.feeder.shoot(0);
    	}

    	// Called when another command which requires one or more of the same
    	// subsystems is scheduled to run
    	@Override
    	protected void interrupted() {
    		Robot.feeder.shoot(0);
    	}
    }
