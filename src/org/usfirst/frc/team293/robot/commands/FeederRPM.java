/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team293.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team293.robot.Robot;

/**
 * An example command.  You can replace me with your own command.
 */
public class FeederRPM extends Command {
	private double CodesPer100ms;
	public FeederRPM(double rpm) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.Feeder);
		CodesPer100ms = rpm*4096/600;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.Feeder.moverpm(CodesPer100ms);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		new StopFeeder();
	}
}
