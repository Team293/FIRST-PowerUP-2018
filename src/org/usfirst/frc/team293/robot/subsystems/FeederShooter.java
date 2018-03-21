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
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class FeederShooter extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private TalonSRX L_motor, R_motor;
	public TalonSRX Angle_motor;
	public DigitalInput upperlimit;
	public DigitalInput lowerlimit;
	private double positionTarget;
	private int absoluteUpperBound;
	private int upperBoundReset = ((int)((1.0*105.0*2048.0/360.0)+.5));
	//private TalonSRX L_motor, R_motor;
	
	public FeederShooter(){
		upperlimit = new DigitalInput(9);
		lowerlimit = new DigitalInput(8);
		L_motor = new TalonSRX(0);
		L_motor.setSensorPhase(true);
		R_motor = new TalonSRX(1);
		R_motor.setSensorPhase(false);
		Angle_motor = new TalonSRX(5);
		L_motor.config_kF(0, .08, 10);
		L_motor.config_kP(0, 0.1, 10);
		L_motor.config_kI(0, 0.05 , 10);
		L_motor.config_kD(0, 0.5, 10);
		
		L_motor.config_IntegralZone(0, 20, 10);
		L_motor.configMaxIntegralAccumulator(0, 400, 10);
		
		R_motor.config_kF(0, .084, 10);
		R_motor.config_kP(0, 0.1, 10);
		R_motor.config_kI(0, .05 , 10);
		R_motor.config_kD(0, .5, 10);
		
		R_motor.config_IntegralZone(0, 20, 10);
		R_motor.configMaxIntegralAccumulator(0, 400, 10);
		
		//Set up angle motor for closed loop position control
		Angle_motor.config_kF(0, 0.0, 10);
		Angle_motor.config_kP(0, 0.4, 10);
		Angle_motor.config_kI(0, 0, 10);
		Angle_motor.config_kD(0, 0.0, 10);
		
		Angle_motor.config_IntegralZone(0, 30, 10);
		Angle_motor.configMaxIntegralAccumulator(0, 100, 10);
		
		Angle_motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		
		Angle_motor.setSensorPhase(true);
		Angle_motor.setInverted(false);

		Angle_motor.configNominalOutputForward(0, 10);
		Angle_motor.configNominalOutputReverse(0, 10);
		Angle_motor.configPeakOutputForward(1, 10);
		Angle_motor.configPeakOutputReverse(-1, 10);

	}
	
		
	

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new ExampleCommand());
	}
	public void shoot(double power){
		L_motor.set(ControlMode.PercentOutput, power);
		R_motor.set(ControlMode.PercentOutput, power);

	}
	public void calibrate(boolean up){

		if (up == true){
			Angle_motor.setSelectedSensorPosition(upperBoundReset, 0, 10);
		}
		else{
			Angle_motor.setSelectedSensorPosition(0, 0, 10);
		}
	}
	
	public void moveToPosition(double position) {

		boolean upperLimit = upperlimit.get();
		boolean lowerLimit = lowerlimit.get();
		positionTarget = position;
		if ((upperLimit == false) || (lowerLimit == false)) {
			
			if (upperLimit == false){
					if (positionTarget > Angle_motor.getSelectedSensorPosition(0)){
						Angle_motor.set(ControlMode.Position, positionTarget);
					}
					else{
						Angle_motor.set(ControlMode.PercentOutput, 0);
					}
			}
			else{
					if (position < Angle_motor.getSelectedSensorPosition(0)){
						Angle_motor.set(ControlMode.Position, positionTarget);
					}
					else{
						Angle_motor.set(ControlMode.PercentOutput, 0);
					}
				}
			
		}
		else {
			Angle_motor.set(ControlMode.Position, positionTarget);
		}
		
		
	}
	public void moveAngular(double position){
		if ((upperlimit.get() == false) && (lowerlimit.get() == false)) {
		
		Angle_motor.set(ControlMode.Position, position);
		System.out.println(Angle_motor.getSelectedSensorPosition(0));
		}
	}
	
	public void moveAnglePower(double power){	//this is just power and needs to change
		Angle_motor.set(ControlMode.PercentOutput, power);
	}
	public boolean isInPosition(){
		return(Math.abs(Angle_motor.getSelectedSensorPosition(0)-positionTarget)<=20);
	}
	public void moverpm(double rpm){
		L_motor.set(ControlMode.Velocity, rpm);
		R_motor.set(ControlMode.Velocity, rpm);
	}
}