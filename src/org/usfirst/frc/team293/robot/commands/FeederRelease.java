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
    		requires(Robot.Feeder);
    		requires(Robot.FeedSensors);
    		rpm = FeederCP100MS;
    	}

    	// Called just before this Command runs the first time
    	@Override
    	protected void initialize() {
    	}

    	// Called repeatedly when this Command is scheduled to run
    	@Override
    	protected void execute() {
    		//Robot.Feeder.moverpm(OI.launchpad.getThrottle()*12200);
    			Robot.Feeder.shoot(rpm);
    		}
    	

    	// Make this return true when this Command no longer needs to run execute()
    	@Override
    	protected boolean isFinished() { 
    		return (!(Robot.FeedSensors.getFeederLimit()));
    	}

    	// Called once after isFinished returns true
    	@Override
    	protected void end() {
    		Robot.Feeder.shoot(0);
    	}

    	// Called when another command which requires one or more of the same
    	// subsystems is scheduled to run
    	@Override
    	protected void interrupted() {
    		new StopFeeder();
    	}
    }
