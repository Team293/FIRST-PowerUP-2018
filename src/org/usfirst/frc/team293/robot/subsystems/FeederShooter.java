package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;
import org.usfirst.frc.team293.robot.commands.FeederStop;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
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
	private DigitalInput Feederlimit;
	///below is variables for angle

	public FeederShooter() {
		Feederlimit = new DigitalInput(RobotMap.feederBoxLimit);
		
		lMotor = new TalonSRX(RobotMap.feederShooterLeft);
		lMotor.setSensorPhase(false);
		rMotor = new TalonSRX(RobotMap.feederShooterRight);
		rMotor.setSensorPhase(true);

		
		lMotor.config_kF(0, 1023.0/13653.0, 10);
		lMotor.config_kP(0, 0.04, 10);
		lMotor.config_kI(0, 0 , 10);
		lMotor.config_kD(0, 0, 10);		
		lMotor.config_IntegralZone(0, 20, 10);
		lMotor.configMaxIntegralAccumulator(0, 400, 10);
		
		rMotor.config_kF(0, 1023.0/13653.0, 10);
		rMotor.config_kP(0, 0.04, 10);
		rMotor.config_kI(0, 0 , 10);
		rMotor.config_kD(0, 0, 10);
		
		rMotor.config_IntegralZone(0, 20, 10);
		rMotor.configMaxIntegralAccumulator(0, 400, 10);
	}
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new FeederStop());
	}
	/**
	 * Shoots the cube
	 * @param power percentage to each shooter motor
	 */
	public void shoot(double power) {
		lMotor.set(ControlMode.PercentOutput, power);
		rMotor.set(ControlMode.PercentOutput, power);
	}
	public void shootRPM(double cp100ms) {
		lMotor.set(ControlMode.Velocity, cp100ms);
		rMotor.set(ControlMode.Velocity, cp100ms);
	}
	/**
	 * 
	 */
    public boolean getFeederLimit(){
    	return(Feederlimit.get());
    }
}