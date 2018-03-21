package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This subsystem controls the Feeder shooter arm as well as the feeder motors themselves?
 * TBH I'm not sure how this robot works somebody help me here.
 */
public class FeederShooter extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private TalonSRX lMotor, rMotor;
	public TalonSRX angleMotor;
	public DigitalInput upperLimit;
	public DigitalInput lowerLimit;
	private double positionTarget;
	private int upperBoundReset = ((int)((1.0*105.0*2048.0/360.0)+.5));
	
	public FeederShooter() {
		upperLimit = new DigitalInput(RobotMap.feederUpperLimit);
		lowerLimit = new DigitalInput(RobotMap.feederLowerLimit);
		lMotor = new TalonSRX(RobotMap.feederShooterLeft);
		lMotor.setSensorPhase(true);
		rMotor = new TalonSRX(RobotMap.feederShooterRight);
		rMotor.setSensorPhase(false);
		angleMotor = new TalonSRX(RobotMap.feederShooterAngle);
		lMotor.config_kF(0, .08, 10);
		lMotor.config_kP(0, 0.1, 10);
		lMotor.config_kI(0, 0.05 , 10);
		lMotor.config_kD(0, 0.5, 10);
		
		lMotor.config_IntegralZone(0, 20, 10);
		lMotor.configMaxIntegralAccumulator(0, 400, 10);
		
		rMotor.config_kF(0, .084, 10);
		rMotor.config_kP(0, 0.1, 10);
		rMotor.config_kI(0, .05 , 10);
		rMotor.config_kD(0, .5, 10);
		
		rMotor.config_IntegralZone(0, 20, 10);
		rMotor.configMaxIntegralAccumulator(0, 400, 10);
		
		//Set up angle motor for closed loop position control
		angleMotor.config_kF(0, 0.0, 10);
		angleMotor.config_kP(0, 0.4, 10);
		angleMotor.config_kI(0, 0, 10);
		angleMotor.config_kD(0, 0.0, 10);
		
		angleMotor.config_IntegralZone(0, 30, 10);
		angleMotor.configMaxIntegralAccumulator(0, 100, 10);
		
		angleMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		
		angleMotor.setSensorPhase(true);
		angleMotor.setInverted(false);

		angleMotor.configNominalOutputForward(0, 10);
		angleMotor.configNominalOutputReverse(0, 10);
		angleMotor.configPeakOutputForward(1, 10);
		angleMotor.configPeakOutputReverse(-1, 10);

	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new ExampleCommand());
	}
	public void shoot(double power) {
		lMotor.set(ControlMode.PercentOutput, power);
		rMotor.set(ControlMode.PercentOutput, power);
	}
	public void calibrate(boolean up) {
		if (up) {
			angleMotor.setSelectedSensorPosition(upperBoundReset, 0, 10);
		} else {
			angleMotor.setSelectedSensorPosition(0, 0, 10);
		}
	}
	
	public void moveToPosition(double position) {
		boolean upperLimitStatus = upperLimit.get();
		boolean lowerLimitStatus = lowerLimit.get();
		positionTarget = position;
		if ((!upperLimitStatus) || (!lowerLimitStatus)) {
			
			if (!upperLimitStatus){
					if (positionTarget > angleMotor.getSelectedSensorPosition(0)) {
						angleMotor.set(ControlMode.Position, positionTarget);
					} else {
						angleMotor.set(ControlMode.PercentOutput, 0);
					}
			} else {
					if (position < angleMotor.getSelectedSensorPosition(0)) {
						angleMotor.set(ControlMode.Position, positionTarget);
					} else {
						angleMotor.set(ControlMode.PercentOutput, 0);
					}
				}
			
		}
		else {
			angleMotor.set(ControlMode.Position, positionTarget);
		}
		
		
	}
	public void moveAngular(double position){
		if (!upperLimit.get() && !lowerLimit.get()) {			
			angleMotor.set(ControlMode.Position, position);
			System.out.println(angleMotor.getSelectedSensorPosition(0));
		}
	}
	
	public void moveAnglePower(double power){	//this is just power and needs to change
		angleMotor.set(ControlMode.PercentOutput, power);
	}
	public boolean isInPosition(){
		return(Math.abs(angleMotor.getSelectedSensorPosition(0) - positionTarget) <= 20);
	}
	public void moverpm(double rpm){
		lMotor.set(ControlMode.Velocity, rpm);
		rMotor.set(ControlMode.Velocity, rpm);
	}
}