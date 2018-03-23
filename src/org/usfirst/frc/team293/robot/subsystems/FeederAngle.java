package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;
import org.usfirst.frc.team293.robot.commands.FeederSetAngle;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class FeederAngle extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private DigitalInput upperLimit;
	private DigitalInput lowerLimit;
	private TalonSRX angleMotor;

	private final double[] positionTarget = {1,50,50,110};	//THESE ARE TEMP
	private double setpoint;
	private double kP = 0.01;
	public Encoder angleEncoder;
	public FeederAngle(){
		upperLimit = new DigitalInput(RobotMap.feederUpperLimit);
		lowerLimit = new DigitalInput(RobotMap.feederLowerLimit);
		angleMotor = new TalonSRX(RobotMap.feederShooterAngle);
		angleEncoder = new Encoder(RobotMap.angleEncoder[0],RobotMap.angleEncoder[1], false, Encoder.EncodingType.k4X);
		angleEncoder.setReverseDirection(true);
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
       // setDefaultCommand(new FeederSetAngle(1));
    }
    /**
     * This runs at the init of the robot and brings the arm up to the top limit
     * @return
     */
	public boolean calibrate() {
		angleMotor.set(ControlMode.PercentOutput, .1);	//I assume a positive is an up
		return upperLimit.get();
	}
	/**
	 * Once it hits the top limit, then reset the encoder.
	 */
	public void resetEncoder(){
		angleEncoder.reset();
	}
	/**
	 * Unless we're at the designated position and at the limit, then go to the specified angle
	 * @param index to go to
	 */
	public void setAngleSetpoint(int index) {
		setpoint = positionTarget[index];
		SmartDashboard.putNumber("Angle Setpoint", setpoint);
		if (upperLimit.get() && index == 0) {
			angleMotor.set(ControlMode.PercentOutput, 0);
		} else if (lowerLimit.get() && index == 3) {
			angleMotor.set(ControlMode.PercentOutput, 0);
		} else {
			angleMotor.set(ControlMode.PercentOutput,  (setpoint - angleEncoder.get() )* kP);	//I assume a positive is an up
		}
	}
	
	public void moveAnglePower(double power){	//this is just power and needs to change
		angleMotor.set(ControlMode.PercentOutput, power);
	}
}

