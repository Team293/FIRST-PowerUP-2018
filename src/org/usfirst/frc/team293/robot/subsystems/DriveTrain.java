package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.RobotMap;
import org.usfirst.frc.team293.robot.commands.TankDriveDefault;

import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveTrain extends Subsystem {
	private SpeedController leftMotorOne, leftMotorTwo, leftMotorThree, rightMotorOne, rightMotorTwo, rightMotorThree;

	public PigeonIMU imu;
	private DifferentialDrive drive;
	public Encoder leftEncoder, rightEncoder;
	public boolean reverseDirection=false;
	double leftPowerinitial = 0;
	double rightPowerinitial = 0;
	double leftPower = 0;
	double rightPower = 0;
	double finalPower=.5;
	double pValue=.05;
	double dValue=-0;
	double previousError=0;
	double error;
	double setpoint=0;
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
	
	public DriveTrain(){	//make drivetrain stuff
		leftMotorOne = new VictorSP(RobotMap.leftDrive[0]);
		leftMotorTwo= new VictorSP(RobotMap.leftDrive[1]);
		leftMotorThree= new VictorSP(RobotMap.leftDrive[2]);
		SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftMotorOne, leftMotorTwo, leftMotorThree);
		
		rightMotorOne= new VictorSP(RobotMap.rightDrive[0]);
		rightMotorTwo= new VictorSP(RobotMap.rightDrive[1]);
		rightMotorThree= new VictorSP(RobotMap.rightDrive[2]);
		SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightMotorOne, rightMotorTwo, rightMotorThree);
		
		imu=new PigeonIMU(RobotMap.imu);
    	//imu.EnableTemperatureCompensation(true);
    	
		drive = new DifferentialDrive(leftMotors, rightMotors);	
		
		leftEncoder= new Encoder(RobotMap.leftEncoder[0],RobotMap.leftEncoder[1], false, Encoder.EncodingType.k4X);	//creates encoder with fast sampling and true or false for direction
		rightEncoder= new Encoder(RobotMap.rightEncoder[0],RobotMap.rightEncoder[1], false, Encoder.EncodingType.k4X);
		
		//leftEncoder.setDistancePerPulse(256/(3.14*4));//the amount of ticks to in...still have to find this from P
		//rightEncoder.setDistancePerPulse(256/(3.14*4));//the amount of ticks to in...still have to find this from P
		
		leftEncoder.setDistancePerPulse(3.14/(128.0*3.0));//the amount of ticks to in...still have to find this from P
		rightEncoder.setDistancePerPulse(3.14/(128.0*3.0));//the amount of ticks to in...still have to find this from P
		
		leftEncoder.setSamplesToAverage(5);
		rightEncoder.setSamplesToAverage(5);
	}
 
    public void initDefaultCommand() {       
        setDefaultCommand(new TankDriveDefault());	// Set the default command for a subsystem here.
    }  
    
    
    public void tankdrive(double left, double right){
    	drive.tankDrive(left, right);  
    	double leftRate=leftEncoder.getRate();
    	double rightRate=rightEncoder.getRate();
    	SmartDashboard.putNumber("leftEncoder", leftRate);
    	SmartDashboard.putNumber("rightEncoder", rightRate);
	}
    
    public void reverseDrive(double left, double right){								//Switch Direction we're going
    	drive.tankDrive(-right,-left);
    }
    
    public void squaredTankDrive(double left, double right){
    	drive.tankDrive(left, right,true);
    }
    public void squaredReverseTankDrive(double left, double right){
    	drive.tankDrive(-left, -right,true);
    }
    /*
    public void encoderDriveRedo(double leftStick ,double rightStick){
    	double leftRate=leftEncoder.getRate();
    	double rightRate=rightEncoder.getRate();
    	SmartDashboard.putNumber("leftEncoder", leftRate);
    	SmartDashboard.putNumber("rightEncoder", rightRate);
    	PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
    	SmartDashboard.putNumber("Pigeon", imu.getFusedHeading(fusionStatus));
    	SmartDashboard.putNumber("leftRateSetpoint", leftRateSetpoint);
    	
    	
    	SmartDashboard.putNumber("rightRateSetpoint", rightRateSetpoint);
    }
    */
    public void kennyDrive(double leftStick ,double rightStick){
    	
    	double leftRate=leftEncoder.getRate()/1000;
    	double rightRate=-rightEncoder.getRate()/1000;
    	//double leftRate=leftEncoder.getRate();
    	//double rightRate=-rightEncoder.getRate();
    	SmartDashboard.putNumber("leftEncoder", leftRate);
    	SmartDashboard.putNumber("rightEncoder", rightRate);
    	PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
    	SmartDashboard.putNumber("Pigeon", imu.getFusedHeading(fusionStatus));
    	//SmartDashboard.putNumber("rightEncoderdpp", rightEncoder.getDistancePerPulse());
    	//SmartDashboard.putNumber("rightEncoder", rightEncoder.getDistance());
    	double leftRateSetpoint=-leftStick*130; //125
    	SmartDashboard.putNumber("leftRateSetpoint", -leftStick*125);
    	
    	double rightRateSetpoint=-rightStick*130; //125
    	SmartDashboard.putNumber("rightRateSetpoint", -rightStick*125);
    	drive.tankDrive(-(leftRateSetpoint-leftRate)*0.016,-(rightRateSetpoint-rightRate)*0.016);
    	//drive.tankDrive(-(leftRateSetpoint-leftRate)*0.319,-(rightRateSetpoint-rightRate)*0.319);
    	
    	//double leftRate=leftEncoder.getRate()/1000;
    	//double rightRate=-rightEncoder.getRate()/1000;
    }
    public void encoderDrive(double leftStick ,double rightStick){	
    	
    	double leftRate=leftEncoder.getRate();
    	double rightRate=rightEncoder.getRate();
    	SmartDashboard.putNumber("leftEncoder", leftRate);
    	SmartDashboard.putNumber("rightEncoder", rightRate);
    	PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
    	SmartDashboard.putNumber("Pigeon", imu.getFusedHeading(fusionStatus));
    	//SmartDashboard.putNumber("rightEncoderdpp", rightEncoder.getDistancePerPulse());
    	//SmartDashboard.putNumber("rightEncoder", rightEncoder.getDistance());
    	if (Math.abs(leftStick) < .1){
    		leftRateSetpoint= 0; //125
    	}
    	else{
    		leftRateSetpoint=leftStick*12; //125
    	}
    	if (Math.abs(rightStick) < .1){
    		rightRateSetpoint= 0; //125
    	}
    	else{
    		rightRateSetpoint=rightStick*12; //125
    	}
    	
    	SmartDashboard.putNumber("leftRateSetpoint", leftRateSetpoint);
    	
    	
    	SmartDashboard.putNumber("rightRateSetpoint", rightRateSetpoint);
    	double rightpowerOffset = (rightRateSetpoint-rightRate)*0.002;
    	double leftpowerOffset = (leftRateSetpoint-leftRate)*0.002;
    	SmartDashboard.putNumber("leftoffset", leftpowerOffset);
    	SmartDashboard.putNumber("rightoffset", rightpowerOffset);
    	if (Math.abs(leftpowerOffset+leftPowerinitial)>1){
    		if (Math.signum(leftpowerOffset+leftPowerinitial) == 1){
    		leftPower = 1;
    		}
    		else{
    		leftPower = -1;	
    		}
    		
    		}
    	else{
    		leftPower = leftpowerOffset+leftPowerinitial;
    	}
    	if (Math.abs(rightpowerOffset+rightPowerinitial)>1){
    		if (Math.signum(rightpowerOffset+rightPowerinitial) == 1){
    		rightPower = 1;
    		}
    		else{
    		rightPower = -1;	
    		}
    		
    		}
    	else{
    		rightPower = rightpowerOffset+rightPowerinitial;
    	}
    	drive.tankDrive(leftStick,rightStick);
    	leftPowerinitial = leftPower;
    	rightPowerinitial = rightPower;
    }
    	//drive.tankDrive(-(leftRateSetpoint-leftRate)*0.319,-(rightRateSetpoint-rightRate)*0.319);
    public void feedForwardEncoderDrive(double leftStick ,double rightStick){
    	double leftRate=leftEncoder.getRate();
    	double rightRate=rightEncoder.getRate();
    	SmartDashboard.putNumber("leftEncoder", leftRate);
    	SmartDashboard.putNumber("rightEncoder", rightRate);
    	PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
    	SmartDashboard.putNumber("Pigeon", imu.getFusedHeading(fusionStatus));
    	//SmartDashboard.putNumber("rightEncoderdpp", rightEncoder.getDistancePerPulse());
    	//SmartDashboard.putNumber("rightEncoder", rightEncoder.getDistance());
    	if (Math.abs(leftStick) < .1){
    		leftRateSetpoint= 0; //125
    	}
    	else{
    		leftRateSetpoint=leftStick*10; //125
    	}
    	if (Math.abs(rightStick) < .1){
    		rightRateSetpoint= 0; //125
    	}
    	else{
    		rightRateSetpoint=rightStick*10; //125
    	}
    	
    	SmartDashboard.putNumber("leftRateSetpoint", leftRateSetpoint);
    	
    	
    	SmartDashboard.putNumber("rightRateSetpoint", rightRateSetpoint);
    	double rightpowerOffset = (rightRateSetpoint-rightRate)*0.05;
    	double leftpowerOffset = (leftRateSetpoint-leftRate)*0.05;
    	leftPowerinitial = 1.0*leftRateSetpoint/12.5;
    	rightPowerinitial = 1.0*rightRateSetpoint/10;
    	SmartDashboard.putNumber("leftoffset", leftpowerOffset);
    	SmartDashboard.putNumber("rightoffset", rightpowerOffset);
    	if (Math.abs(leftpowerOffset+leftPowerinitial)>1){
    		if (Math.signum(leftpowerOffset+leftPowerinitial) == 1){
    		leftPower = 1;
    		}
    		else{
    		leftPower = -1;	
    		}
    		
    		}
    	else{
    		leftPower = leftpowerOffset+leftPowerinitial;
    	}
    	if (Math.abs(rightpowerOffset+rightPowerinitial)>1){
    		if (Math.signum(rightpowerOffset+rightPowerinitial) == 1){
    		rightPower = 1;
    		}
    		else{
    		rightPower = -1;	
    		}
    		
    		}
    	else{
    		rightPower = rightpowerOffset+rightPowerinitial;
    	}
    	drive.tankDrive(leftPower,rightPower);
    	//leftPowerinitial = leftPower;
    	//rightPowerinitial = rightPower;
    	//drive.tankDrive(-(leftRateSetpoint-leftRate)*0.319,-(rightRateSetpoint-rightRate)*0.319);
    	
    }
//////////////////////////////Gyro Stuff-->>>///////////////////////////////////////////////
    
    public void velocityStraight(double speed){	///NOT DONE YET speed=-1,1 
    	PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
    	angle=imu.getFusedHeading(fusionStatus);
     	
    	error=(angle-setpoint);
    	
    	double leftRate=(leftEncoder.getRate()/1000);
    	double rightRate=(-rightEncoder.getRate()/1000);
    	
    	double leftRateSetpoint=speed*130;
    	double rightRateSetpoint=speed*130;

    	drive.tankDrive(-(leftRateSetpoint-rightRate)*0.015+angle*.01,-(rightRateSetpoint-leftRate)*0.015-angle*.01);
   
    	
    }
    public boolean goStraightDistanceVelocity(double distance, double velocity){
    	inPosition = false;
    	
    	PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
    	angle=imu.getFusedHeading(fusionStatus);
    	velocityOffsetL = -(velocity - leftEncoder.getRate())*0.01;
    	velocityOffsetR = (velocity - rightEncoder.getRate())*0.01;//(rightEncoder.getRate() + velocity)*.01;
    	angleOffset = angle*.05;
    	offset = (angle-setpoint)*.02;
    	drive.tankDrive(initialL + velocityOffsetL + angleOffset ,initialR + velocityOffsetR);
    	initialL = initialL + velocityOffsetL;
    	initialR = initialR + velocityOffsetR;
    	if (Math.signum(leftEncoder.getDistance()-distance) == 1){
    	inPosition = false;	
    	}
    	return inPosition;
    }
    
    public void resetGyro(){
    	imu.setFusedHeading(0.0, 0);
    	imu.setYaw(0, 0);
    	turning=false;
    	setpoint=0;
    	error=0;
    	angle=0;
    }
    public void resetInitialPower(){
    	initialL = 0;
    	initialR = 0;
    }
    
    public void gyroStraight(double speed){
    	PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
    	imuStatus = (imu.getState() != PigeonIMU.PigeonState.NoComm);
    	if (imuStatus){
     	angle=imu.getFusedHeading(fusionStatus);
     	
    	error=(angle-setpoint);
        
        finalPower=speed+(error*pValue);
        drive.tankDrive(-speed,-finalPower);
 
    	}
    	else{
    		tankdrive(speed,speed);
    	}
    }

    public boolean gyroTurn(double speed, double angle, double rate){
    	PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
    	angle=imu.getFusedHeading(fusionStatus);
    	
    	setpoint+=rate;
    	error=(angle-setpoint);
        
        finalPower=speed+(error*pValue);
        drive.tankDrive(speed,finalPower);
        if (Math.abs(setpoint)>=Math.abs(angle)){
        	turning=true;
        }
        
        
        return turning;
    }
    public boolean newGyroTurnInPlace(double setangle){//assuming left forwards = -1 and backwards = 1
    	inPosition = false;    	
    	PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
    	angle=imu.getFusedHeading(fusionStatus);
    	offset = (setangle - angle)*.01;
    	drive.tankDrive(initialL-offset, initialR+offset);
    	initialL = initialL+offset;
    	initialR = initialR+offset;
    	if (Math.abs(angle-setangle)<6){
    		inPosition = true;
    	}
    		
    	return inPosition;
    }
    public boolean gyroTurnInPlace(double setangle, double rate){
    	turning=false;
    	PigeonIMU.FusionStatus fusionStatus = new PigeonIMU.FusionStatus();
    	angle=imu.getFusedHeading(fusionStatus); ///Gets the angle
    	setpoint+=rate;  //adds the rate into the setpoint to gradually change it
    	error=(angle-setpoint); //finds how far you are off from the setpoint
        
        finalPower=(error*pValue);
        drive.tankDrive(finalPower,-finalPower);
        if (Math.abs(angle)>=Math.abs(setangle)){
        	turning=true;
        }

        return turning;
    }
    
   
    
    
  //////////// ^Gyro Stuff  Encoder stuff--->>>  
    
	public void resetEnc(){
		leftEncoder.reset();
		rightEncoder.reset();
	}
	
	public double[] readEnc(){
		double leftDistance= Math.abs((leftEncoder.getRaw()*3.14*4)/1024);
		double rightDistance=Math.abs((rightEncoder.getRaw()*3.14*4)/1024);
		double[] encoders= {(leftDistance+rightDistance)/2,leftDistance, rightDistance};
		return encoders;
	}

}
