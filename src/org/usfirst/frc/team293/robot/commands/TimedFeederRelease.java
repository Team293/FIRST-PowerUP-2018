package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class TimedFeederRelease extends TimedCommand {
	double rpm;
    public TimedFeederRelease(double timeout, double FeederCP100MS) {
        super(timeout);
        requires(Robot.feeder);
		requires(Robot.FeedSensors);
		rpm = FeederCP100MS;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.feeder.shoot(rpm);
    }

    // Called once after timeout
    protected void end() {
    	Robot.feeder.shoot(0);
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	new FeederStop();
    }
}
