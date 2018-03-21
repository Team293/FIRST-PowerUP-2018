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
import org.usfirst.frc.team293.robot.subsystems.FeederSensorsMonitor;
import org.usfirst.frc.team293.robot.subsystems.FeederShooter;
//import org.usfirst.frc.team293.robot.subsystems.LEDs;
import org.usfirst.frc.team293.robot.subsystems.Pincher;
import org.usfirst.frc.team293.robot.subsystems.Winch;
import org.usfirst.frc.team293.robot.subsystems.autoLogger;

import com.analog.adis16448.frc.ADIS16448_IMU;

import Autonomouses.MiddleToAutoLine;
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

	public static final FeederSensorsMonitor FeedSensors = new FeederSensorsMonitor();
	public static final PowerDistributionPanel pdp = new PowerDistributionPanel(62);
	public static final Winch Climber = new Winch();
	public static final ADIS16448_IMU imu = new ADIS16448_IMU();
	public static final autoLogger DataLog = new autoLogger();
	public boolean stop = false;

	public static OI m_oi;
	

	Command m_autonomousCommand;
	Command calibrationCommand;
	//Command log;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		CameraServer.getInstance().startAutomaticCapture();
		pdp.clearStickyFaults();
		m_chooser.addDefault("Default Auto", m_autonomousCommand);
		m_chooser.addObject("To Auto Line", new ToAutoLine());
		
		SmartDashboard.putData("Auto mode", m_chooser);
		 //moves feeder to reference point (upper limit switch), gets offset angle from encoder
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
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if (gameData.length() > 0){
			if (gameData.charAt(0) == 'L'){
				switchLeft = true;	
			}
			else{
				switchLeft = true;	
			}
		}
		m_autonomousCommand = m_chooser.getSelected();
		/*
		
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		//new CalibrateFeeder();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		SmartDashboard.putBoolean("switchLeft?", switchLeft);
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		calibrationCommand = new FeederCalibrate();
		calibrationCommand.start();
		//log = new RunAutoLogger();
		//log.start();
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
	//	if(OI.switch4dn)
	//	while ((OI.switch4dn.getRawValue()==false) && stop == false){
			
		
		SmartDashboard.putNumber("Feeder Direction", OI.leftStick.getThrottle());
		SmartDashboard.putNumber("LeftDrive1Current", pdp.getCurrent(15));
		SmartDashboard.putNumber("Gyro-X", imu.getAngleX());
	    SmartDashboard.putNumber("Gyro-Y", imu.getAngleY());
	    SmartDashboard.putNumber("Gyro-Z", imu.getAngleZ());
	    SmartDashboard.putNumber("Gyro-Z", imu.getAngleZ());
	    
	    SmartDashboard.putNumber("Accel-Y", imu.getAccelY());
	    SmartDashboard.putNumber("Accel-Z", imu.getAccelZ());
	    SmartDashboard.putNumber("Accel-X", imu.getAccelX());
	    SmartDashboard.putNumber("Pitch", imu.getPitch());
	    SmartDashboard.putNumber("Roll", imu.getRoll());
	    SmartDashboard.putNumber("Yaw", imu.getYaw());
	    
	    SmartDashboard.putNumber("Pressure: ", imu.getBarometricPressure());
	    SmartDashboard.putNumber("Temperature: ", imu.getTemperature()); 
		
		// SmartDashboard.putBoolean("Photoswitch", FeedSensors.getPhotoSwitch());
		 SmartDashboard.putBoolean("feederupper", feeder.upperLimit.get());
		 SmartDashboard.putBoolean("feederlower", feeder.lowerLimit.get());
		 SmartDashboard.putBoolean("FeederLimit", FeedSensors.getFeederLimit());
		 //SmartDashboard.putNumber("feederangle", (Robot.Feeder.Angle_motor.getSelectedSensorPosition(0)/2048.0*360.0));
		 SmartDashboard.putNumber("feederangle", (Robot.feeder.angleMotor.getSelectedSensorPosition(0)));
		 Scheduler.getInstance().run();
		}
	

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
