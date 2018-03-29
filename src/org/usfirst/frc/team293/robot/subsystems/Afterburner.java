/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.RobotMap;
import org.usfirst.frc.team293.robot.commands.AfterburnerStop;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The afterburner allows the cube to shoot further
 */
public class Afterburner extends Subsystem {
	
	public TalonSRX lMotor, rMotor;
	
	public Afterburner(){
		lMotor = new TalonSRX(RobotMap.afterBurnerLeft);
		lMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		lMotor.clearStickyFaults(10);
		lMotor.setSensorPhase(false);
		lMotor.config_kF(0, 1023.0/30000.0, 10);
		lMotor.config_kP(0, .07, 10);
		lMotor.config_kD(0, 1.4, 10);
		lMotor.config_IntegralZone(0, 600, 10);
		lMotor.configMaxIntegralAccumulator(0, 600, 10);
		lMotor.configAllowableClosedloopError(0, 20, 10);
		lMotor.configVelocityMeasurementWindow(200, 10);
		rMotor = new TalonSRX(RobotMap.afterBurnerRight);
		rMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		rMotor.setSensorPhase(false);
		rMotor.clearStickyFaults(10);
		rMotor.config_kF(0, 1023.0/30000.0, 10);
		rMotor.config_kP(0, .07, 10);
		rMotor.config_kD(0, 1.4, 10);
		rMotor.configVelocityMeasurementWindow(200, 10);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new AfterburnerStop());
	}
	/**
	 * The moves the afterburners with a percent output
	 * @param The percentage power to run at
	 */
	public void move(double power){
		lMotor.set(ControlMode.PercentOutput, -1*power);
		rMotor.set(ControlMode.PercentOutput, power);
	}
	/**
	 * This moves the afterburners at an rpm
	 * @param rpm The rpm speed from x to y???
	 */
	public void EncoderShoot(double rpm){
		lMotor.set(ControlMode.Velocity, rpm);
		rMotor.set(ControlMode.Velocity, -rpm);
	}
}