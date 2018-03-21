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
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class Afterburner extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	//private VictorSP L_motor, R_motor;
	
	private TalonSRX L_motor, R_motor;
	
	public Afterburner(){
		//L_motor = new VictorSP(RobotMap.L_Feeder);
		//R_motor = new VictorSP(RobotMap.R_Feeder);
		L_motor = new TalonSRX(3);
		L_motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 1000);
		L_motor.clearStickyFaults(10);
		L_motor.setSensorPhase(false);
		L_motor.config_kF(0, .0422, 10);
		L_motor.config_kP(0, .15, 10);
		L_motor.config_kD(0, .4, 0);
		R_motor = new TalonSRX(2);
		R_motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 1000);
		R_motor.setSensorPhase(false);
		R_motor.clearStickyFaults(10);
		R_motor.config_kF(0, .0422, 10);
		R_motor.config_kP(0, .15, 10);
		R_motor.config_kD(0, .4, 0);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new ExampleCommand());
	}
	public void move(double power){
		//L_motor.set(power);
		//R_motor.set((-1)*power);
		L_motor.set(ControlMode.PercentOutput, -1*power);
	//	SmartDashboard.putNumber("L_motor Current", );
		//System.out.println("LeftpositionReadout" + L_motor.getSelectedSensorVelocity(0));
	//	System.out.println("LeftVelocityReadout" + L_motor.getSelectedSensorVelocity(0));
		R_motor.set(ControlMode.PercentOutput, power);
	//	System.out.println("RightVelocityReadout" + R_motor.getSelectedSensorVelocity(0));
	}
	public void EncoderShoot(double rpm){
		//L_motor.set(power);
		//R_motor.set((-1)*power);
		
		L_motor.set(ControlMode.Velocity, rpm);
		//System.out.println("LeftVelocityReadout" + L_motor.getSelectedSensorVelocity(0));
		R_motor.set(ControlMode.Velocity, -rpm);
		//System.out.println("RightVelocityReadout" + R_motor.getSelectedSensorVelocity(0));
		//System.out.println("RPM Control!" );
	}
}