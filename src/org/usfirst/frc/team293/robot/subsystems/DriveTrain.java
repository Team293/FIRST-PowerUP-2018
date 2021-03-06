package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;
import org.usfirst.frc.team293.robot.commands.DriveTankDefault;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The Drivetrain class is a mess.  I believe we're using only one method in here?  Why is there walls of variables?
 * @author Team293
 *
 */
public class DriveTrain extends Subsystem {
	private SpeedController leftMotorOne, leftMotorTwo, leftMotorThree, rightMotorOne, rightMotorTwo, rightMotorThree;

	public PigeonIMU pigeonImu;
	private DifferentialDrive drive;
	public Encoder leftEncoder, rightEncoder;
	public boolean reverseDirection = false;
	double leftPowerinitial = 0;
	double rightPowerinitial = 0;
	double leftPower = 0;
	double rightPower = 0;
	double finalPower = .5;
	double pValue = .05;
	double dValue = -0;
	double previousError = 0;
	double error;
	double setpoint = 0;
	double derivative;
	double angle;
	double offsetGyro;
	double offset = 0;
	boolean inPosition = false;
	double leftRateSetpoint = 0.0;
	double rightRateSetpoint = 0.0;
	double initialL;
	double initialR;
	public boolean forward = true;
	double velocityOffsetL;
	double velocityOffsetR;
	double angleOffset;
	
	public boolean imuStatus;
	public boolean direction=false;
	
	public boolean turning=false;
	/**
	 * 
	 */
	public DriveTrain(){	//make drivetrain stuff
		leftMotorOne = new VictorSP(RobotMap.leftDrive[0]);
		leftMotorTwo = new VictorSP(RobotMap.leftDrive[1]);
		leftMotorThree = new VictorSP(RobotMap.leftDrive[2]);
		SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftMotorOne, leftMotorTwo);
		
		rightMotorOne = new VictorSP(RobotMap.rightDrive[0]);
		rightMotorTwo = new VictorSP(RobotMap.rightDrive[1]);
		//rightMotorThree = new VictorSP(RobotMap.rightDrive[2]);
		SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightMotorOne, rightMotorTwo);
		
		pigeonImu = new PigeonIMU(RobotMap.imu);    	
		
		drive = new DifferentialDrive(leftMotors, rightMotors);	
		
		leftEncoder = new Encoder(RobotMap.leftEncoder[0],RobotMap.leftEncoder[1], false, Encoder.EncodingType.k4X);	//creates encoder with fast sampling and true or false for direction
		rightEncoder = new Encoder(RobotMap.rightEncoder[0],RobotMap.rightEncoder[1], false, Encoder.EncodingType.k4X);
		
		//leftEncoder.setDistancePerPulse(256/(3.14*4));//the amount of ticks to in...still have to find this from P
		//rightEncoder.setDistancePerPulse(256/(3.14*4));//the amount of ticks to in...still have to find this from P
	
		
		leftEncoder.setDistancePerPulse(3.14/(128.0*3.0));
		rightEncoder.setDistancePerPulse(3.14/(128.0*3.0));
		
		leftEncoder.setSamplesToAverage(5);
		rightEncoder.setSamplesToAverage(5);
		drive.setSafetyEnabled(false);
	}
	/**
	 * 
	 */
    public void initDefaultCommand() {       
        setDefaultCommand(new DriveTankDefault());	// Set the default command for a subsystem here.
    }  
    /**
     * TankDrive the robot
     * @param left power command
     * @param right power command
     */
    public void tankdrive(double left, double right){
    	drive.tankDrive(left, right); 
    	SmartDashboard.putNumber("leftEncRate", leftEncoder.getRate());
    	SmartDashboard.putNumber("rightEncRate", rightEncoder.getRate());
	}
    /**
     * Reverse TankDrive the robot
     * @param left power command
     * @param right power command
     */
    public void reverseDrive(double left, double right){								//Switch Direction we're going
    	drive.tankDrive(-right,-left);
    }
    /**
     * This is a wrecked version of my old drivetrain class
     * @param leftStick input value -1 to 1
     * @param rightStick input value -1 to 1
     */
 /*   public void kennyDrive(double leftStick ,double rightStick){
    	
    	double leftRate = leftEncoder.getRate()/1000;
    	double rightRate = -rightEncoder.getRate()/1000;
    	//double leftRate=leftEncoder.getRate();
    	//double rightRate=-rightEncoder.getRate();
    	SmartDashboard.putNumber("leftEncoder", leftRate);
    	SmartDashboard.putNumber("rightEncoder", rightRate);
    	PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
    	SmartDashboard.putNumber("Pigeon", imu.getFusedHeading(fusionStatus));
    	double leftRateSetpoint=-leftStick*130; //125
    	SmartDashboard.putNumber("leftRateSetpoint", -leftStick*125);
    	
    	double rightRateSetpoint=-rightStick*130; //125
    	SmartDashboard.putNumber("rightRateSetpoint", -rightStick*125);
    	drive.tankDrive(-(leftRateSetpoint-leftRate)*0.016,-(rightRateSetpoint-rightRate)*0.016);
    	//drive.tankDrive(-(leftRateSetpoint-leftRate)*0.319,-(rightRateSetpoint-rightRate)*0.319);
    	
    	//double leftRate=leftEncoder.getRate()/1000;
    	//double rightRate=-rightEncoder.getRate()/1000;
    }*/
  
    /**
     * Method for driving the robot based using 2 joystick inputs positive is forward
     * using proportional feedback from encoders
     * @param leftStick Output from left joystick processed within TankDriveDefault, input value -1 to 1. Serves as a percentage of full speed
     * @param rightStick Output from right joystick processed within TankDriveDefault, input value -1 to 1. Serves as a percentage of full speed
     */

    public void feedForwardEncoderDrive(double leftStick ,double rightStick){
    	
    	PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
    	double angle=pigeonImu.getFusedHeading(fusionStatus); ///Gets the angle
    	SmartDashboard.putNumber("angle of IMU!!!", angle);
    	
    	double leftRate = leftEncoder.getRate();
    	double rightRate = rightEncoder.getRate();

    	if (Math.abs(leftStick) < .1){ 
    		leftRateSetpoint= 0; //if within deadband, setpoint is set to 0
    	}
    	else{
    		leftRateSetpoint=leftStick*10; //multiplies leftstick input by full speed to obtain setpoint
    	}
    	if (Math.abs(rightStick) < .1){ 
    		rightRateSetpoint= 0; //if within deadband, setpoint is set to 0
    	}
    	else{
    		rightRateSetpoint=rightStick*10; //multiplies rightstick input by full speed to obtain setpoint
    	}
    	
    	
    	double rightpowerOffset = (rightRateSetpoint-rightRate)*0.05; //calculates offset percent power added to motor based on error between setpoint and current rate
    	double leftpowerOffset = (leftRateSetpoint-leftRate)*0.05;    //calculates offset percent power added to motor based on error between setpoint and current rate
    	
    	leftPowerinitial = leftRateSetpoint/10;
    	rightPowerinitial = rightRateSetpoint/10;
    	
    	leftPower = leftpowerOffset+leftPowerinitial; //used to limit output to between -1 and 1
    	
    	if (leftPower>1) {
    		leftPower = 1;
    	} else if (leftPower < -1) {
    		leftPower = -1;
    	}
    	
    	rightPower = rightpowerOffset+rightPowerinitial;
    	if (rightPower > 1) {
			rightPower = 1;
    	} else if (rightPower < -1) {
    		rightPower = -1;	
		}
    		   	
    	drive.tankDrive(leftPower,rightPower);	

    }

    
    //////////////////////////////Gyro Stuff-->>>///////////////////////////////////////////////
    /**
     * Drives straight using the encoders and a rate to drive with gyro
     * @param speed to drive at I believe from -1 to 1
     */
    public void velocityStraight(double speed){
    	PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
    	angle=pigeonImu.getFusedHeading(fusionStatus);
     	
    	//error=(angle-setpoint);
    	error = angle;
    	
    	//double leftRate=(leftEncoder.getRate()/500);
    	//double rightRate=(-rightEncoder.getRate()/500);
    	double leftRate=(leftEncoder.getRate());
    	double rightRate=(rightEncoder.getRate());
    	
    	double leftRateSetpoint=speed*10;
    	double rightRateSetpoint=speed*10;

    	drive.tankDrive((leftRateSetpoint-leftRate)*0.015+leftRateSetpoint/10.5+angle*.01,(rightRateSetpoint-rightRate)*0.015+rightRateSetpoint/10.5-angle*.01);
    }
    /**
     * Drives straight using just the gyro and a power command to one wheel
     * @param speed The power to drive
     */
    public void gyroStraight(double speed) {
    	SmartDashboard.putNumber("Average of drive encoder", readEnc()[0]);
    	SmartDashboard.putNumber("Left drive encoder", readEnc()[1]);
    	SmartDashboard.putNumber("Right drive encoder", readEnc()[2]);
    	SmartDashboard.putNumber("Distance of Drive of encoder", leftEncoder.getDistance());
    	PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
    	imuStatus = (pigeonImu.getState() != PigeonIMU.PigeonState.NoComm);
    	if (imuStatus) {
	     	angle = pigeonImu.getFusedHeading(fusionStatus);
	     	
	    	error = (angle-setpoint);
	        
	        finalPower=speed+(error*pValue);
	        drive.tankDrive(-speed,-finalPower); 
    	}
    	else {
    		tankdrive(speed,speed);
    	}
    }
    /**
     * This is from 2017, allows the robot to drive in place. 
     * @param setangle the amount to turn
     * @param rate the angular rate to turn at per loop (rate < 3 please)
     * @return	if it's done or not
     */
    public boolean gyroTurnInPlace(double setangle, double rate){

    	turning = false;
    	PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
    	angle=pigeonImu.getFusedHeading(fusionStatus); ///Gets the angle
    	SmartDashboard.putNumber("angle of IMU!!!", angle);
    	setpoint+=rate;  //adds the rate into the setpoint to gradually change it
    	SmartDashboard.putNumber("IMU setpoint", setpoint);
    	error=(angle-setpoint); //finds how far you are off from the setpoint
        SmartDashboard.putNumber("Error of IMU!!!!", error);
        finalPower=(error*pValue);
        
        if (finalPower>.9){
        	finalPower = .9;
        } else if (finalPower < -.9){
        	finalPower = -.9;
        }
        drive.tankDrive(finalPower,-finalPower);
        if (Math.abs(angle)>=Math.abs(setangle)){
        	turning = true;
        }
        return turning;
    }
 
  //////////// ^Gyro Stuff  Encoder stuff--->>>  
    /**
     * Resets the encoders to 0
     */
	public void resetEnc() {
		leftEncoder.reset();
		rightEncoder.reset();
	}
	/**
	 * Resets the gyro and related values
	 */
    public void resetGyro() {
    	pigeonImu.setFusedHeading(0.0, 0);
    	pigeonImu.setYaw(0, 0);
    	turning = false;
    	setpoint = 0;
    	error = 0;
    	angle = 0;
    }

    /**
     * This reads the distance traveled (accounts for wheel diameter)
     * @return encoder array containing distances
     */
	public double[] readEnc() {
		double leftDistance = Math.abs((leftEncoder.getRaw()*3.14*4)/512);
		double rightDistance = Math.abs((rightEncoder.getRaw()*3.14*4)/512);
		double[] encoders = {(leftDistance+rightDistance)/2,leftDistance, rightDistance};
		return encoders;
	}
}
