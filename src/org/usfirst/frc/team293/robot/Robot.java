/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team293.robot;

import org.usfirst.frc.team293.robot.commands.FeederCalibrate;
import org.usfirst.frc.team293.robot.commands.FeederThrottle;
import org.usfirst.frc.team293.robot.commands.DriveForwardPower;
import org.usfirst.frc.team293.robot.commands.RunAutoLogger;
import org.usfirst.frc.team293.robot.subsystems.Afterburner;
//import org.usfirst.frc.team293.robot.subsystems.ClimberRelease;
import org.usfirst.frc.team293.robot.subsystems.DriveTrain;
import org.usfirst.frc.team293.robot.subsystems.FeederAngle;
import org.usfirst.frc.team293.robot.subsystems.FeederShooter;
//import org.usfirst.frc.team293.robot.subsystems.LEDs;
import org.usfirst.frc.team293.robot.subsystems.Pincher;
import org.usfirst.frc.team293.robot.subsystems.Winch;
import org.usfirst.frc.team293.robot.subsystems.autoLogger;

import com.analog.adis16448.frc.ADIS16448_IMU;

import Autonomouses.Center;
import Autonomouses.LeftSide;
import Autonomouses.RightSide;
import Autonomouses.ToAutoLine;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public String gameData;
	public static boolean switchLeft;
	public boolean scaleLeft;
	public static final Afterburner afterBurner = new Afterburner();
	public static final FeederShooter feeder = new FeederShooter();
	public static final Pincher pincher = new Pincher();
	public static final DriveTrain driveTrain =new DriveTrain();

	public static final FeederAngle feederAngle = new FeederAngle();
	public static final PowerDistributionPanel pdp = new PowerDistributionPanel(62);
	public static final Winch Climber = new Winch();
	public static final ADIS16448_IMU imu = new ADIS16448_IMU();
	public static final autoLogger DataLog = new autoLogger();
	public boolean stop = false;

	public static OI oi;
	

	String autonomousChoice;
	Command calibrationCommand;
	Command chosenCommand;
	SendableChooser<String> autoChooser;	//remember auto chooser accepts any object as its type

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 * In this case the sendable chooser is used with Strings and that's used in auto init
	 */
	@Override
	public void robotInit() {	
		oi = new OI();
		pdp.clearStickyFaults();
		calibrationCommand = new FeederCalibrate();		 //moves feeder to reference point 
		calibrationCommand.start();						//(upper limit switch), gets offset angle from encoder		
		
		autoChooser = new SendableChooser();
		autoChooser.addDefault("Dumb Auto", "Dumb Auto");
		autoChooser.addObject("Start Left", "Start Left");
		autoChooser.addObject("Start Center", "Start Center");
		autoChooser.addObject("Start Right", "Start Right");
		
		SmartDashboard.putData("Auto mode", autoChooser);

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard.
	 * The user selects their starting position and that command is run 
	 * along with the game data grabbed in autoInit()
	 */
	@Override
	public void autonomousInit() {
		
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if (gameData.length() > 0){
			autonomousChoice = autoChooser.getSelected();
			if (autonomousChoice == "Dumb Auto"){
				chosenCommand = new ToAutoLine();
			} else if (autonomousChoice == "Start Left") {
				chosenCommand = new LeftSide(gameData);
			} else if (autonomousChoice == "Start Center") {
				chosenCommand = new Center(gameData);
			} else if (autonomousChoice == "Start Right") {
				chosenCommand = new RightSide(gameData);
			}
		} else {
			chosenCommand = new ToAutoLine();
		}	
		chosenCommand.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.

		//log = new RunAutoLogger();
		//log.start();
		if (chosenCommand != null) {
			chosenCommand.cancel();
		}
		
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {

		 Scheduler.getInstance().run();
		}
	

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
