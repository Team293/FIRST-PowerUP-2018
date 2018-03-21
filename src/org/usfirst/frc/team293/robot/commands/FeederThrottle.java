/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team293.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team293.robot.OI;
import org.usfirst.frc.team293.robot.Robot;

/**
 * An example command.  You can replace me with your own command.
 */
	
public class FeederThrottle extends Command {
	double rpm;
	public FeederThrottle(double FeederCP100MS) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.feeder);
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
		//if (Robot.FeedSensors.getFeederLimit()==true){
			Robot.feeder.shoot(rpm);
		//}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() { 
		return (!(Robot.FeedSensors.getFeederLimit()));
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.feeder.shoot(0);
		//Command FeedUp = new FeederFullUp();
		//FeedUp.start();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		new StopFeeder();
	}
}
