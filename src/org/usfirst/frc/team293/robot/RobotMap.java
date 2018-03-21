package org.usfirst.frc.team293.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	public static int[]   
			rightDrive={7,8,9}, 	//Victors
			leftDrive={4,5,6};
	
	public static int[] leftEncoder={0, 1}, 	//Drivetrain Encoders
			rightEncoder={2,3};
	
	public static int imu = 5;
	public static int FeederLimit = 7;
	public static int PinchPhotoSwitch = 5;
	
	public static int[] climbMotors={1,2};
	
	public static int Release = 0;	
	
	public static int L_Feeder = 7,		//Feeder motors
			R_Feeder = 4,
			Angle_Feeder = 6;
	
	public static int L_Shooter = 3,
			R_Shooter = 5;
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;

}

