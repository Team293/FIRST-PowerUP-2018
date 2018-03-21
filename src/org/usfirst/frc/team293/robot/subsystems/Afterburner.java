/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The afterburner allows the cube to shoot further
 */
public class Afterburner extends Subsystem {
	
	private TalonSRX lMotor, rMotor;
	
	public Afterburner(){
		lMotor = new TalonSRX(RobotMap.afterBurner);
		lMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 1000);
		lMotor.clearStickyFaults(10);
		lMotor.setSensorPhase(false);
		lMotor.config_kF(0, .0422, 10);
		lMotor.config_kP(0, .15, 10);
		lMotor.config_kD(0, .4, 0);
		rMotor = new TalonSRX(2);
		rMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 1000);
		rMotor.setSensorPhase(false);
		rMotor.clearStickyFaults(10);
		rMotor.config_kF(0, .0422, 10);
		rMotor.config_kP(0, .15, 10);
		rMotor.config_kD(0, .4, 0);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new ExampleCommand());
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