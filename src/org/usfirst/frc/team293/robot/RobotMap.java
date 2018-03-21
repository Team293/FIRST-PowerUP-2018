package org.usfirst.frc.team293.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//////////Motor Controllers//////////////
	public static final int 	//Victors for the Drivetrain
			rightDrive[] = {7,8,9}, 	
			leftDrive[] = {4,5,6},
			climbMotors[] = {1,2},
			afterBurner = 3,
			feederShooterLeft = 0,
			feederShooterRight = 1,
			feederShooterAngle = 5,
	//////////Relay///////////////
			poleVexMotor = 0,
	//////////Digital Inputs///////
			leftEncoder[] = {0, 1}, 	//Drivetrain Encoders
			rightEncoder[] = {2,3},
			winchPoleLimit = 5,
			imu = 5,
			feederUpperLimit = 9,
			feederLowerLimit = 8,
	////////Pneumatics/////////
			compressor = 0,
			leftCylinderOpen = 0,
			leftCylinderClose = 1,
			rightCylinderOpen = 2,
			rightcylinderClose = 3;
	//public static int PinchPhotoSwitch = 5;
	
	
	
	//public static int Release = 0;	
	
	public static int 
			L_Feeder = 7,		//Feeder motors
			R_Feeder = 4,
			Angle_Feeder = 6;
	
	public static int L_Shooter = 3,
			R_Shooter = 5;

}

