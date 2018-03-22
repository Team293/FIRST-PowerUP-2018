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
			afterBurnerLeft = 3,
			afterBurnerRight = 2,
			feederShooterLeft = 0,
			feederShooterRight = 1,
			feederShooterAngle = 5,
	//////////Relay///////////////
			poleVexMotor = 0,
	//////////Digital Inputs///////
			leftEncoder[] = {0, 1}, 	//Drivetrain Encoders
			rightEncoder[] = {2,3},
			winchPoleLimit = 6,
			imu = 5,
			feederUpperLimit = 9,
			feederLowerLimit = 8,
			feederBoxLimit = 7,
			angleEncoder[] = {4,5},
	////////Pneumatics/////////
			compressor = 0,
			leftCylinderOpen = 0,
			leftCylinderClose = 1,
			rightCylinderOpen = 2,
			rightcylinderClose = 3;
}

