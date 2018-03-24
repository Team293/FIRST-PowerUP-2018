package org.usfirst.frc.team293.robot;


import org.usfirst.frc.team293.robot.commands.AfterburnerHalfThrottle;
import org.usfirst.frc.team293.robot.commands.AfterburnerRPM;
import org.usfirst.frc.team293.robot.commands.Climb;
import org.usfirst.frc.team293.robot.commands.PincherExtend;
import org.usfirst.frc.team293.robot.commands.Feed;
//import org.usfirst.frc.team293.robot.commands.FeedToExchange;
import org.usfirst.frc.team293.robot.commands.FeederRelease;
import org.usfirst.frc.team293.robot.commands.FeederSetAngle;
import org.usfirst.frc.team293.robot.commands.FeederThrottle;
import org.usfirst.frc.team293.robot.commands.FeederMoveAnglePower;
import org.usfirst.frc.team293.robot.commands.ClimberPoleUp;
import org.usfirst.frc.team293.robot.commands.ClimberPoleDown;
import org.usfirst.frc.team293.robot.commands.PincherRetract;
import org.usfirst.frc.team293.robot.commands.RunAutoLogger;

import Autonomouses.ButtonLogic;

//import org.usfirst.frc.team293.robot.commands.StopAutoLogger;
//import org.usfirst.frc.team293.robot.commands.MoveServoJoystick;
import org.usfirst.frc.team293.robot.commands.AfterburnerStop;
import org.usfirst.frc.team293.robot.commands.FeederAngleStop;
import org.usfirst.frc.team293.robot.commands.ClimberStop;
import org.usfirst.frc.team293.robot.commands.FeederStop;
import org.usfirst.frc.team293.robot.commands.ClimberReverse;
//import org.usfirst.frc.team293.robot.commands.TankDriveAutoReplay;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public static Joystick leftStick = new Joystick(0);
	public static Joystick rightStick = new Joystick(1);
	public static Joystick annaLeft=new Joystick(2);
	public static Joystick annaRight=new Joystick(3);
	public static JoystickButton[] launch1;
	public static JoystickButton[] launch2;
	//public static ButtonLogic[] theButtons = new ButtonLogic[30];
	public static int numButtonFunctions = 0;
	public OI() {
		// Assign a reference to each joystick's buttons as a vector map by index
		// Note that index 0 is not assigned, so that the numbers match!
		// When we extract these button press states, remember they are boolean: (true/false).
		// For storing in a file, we have to record them as doubles: (0.,1.).
		// The button arrays are thus:
		// org.usfirst.frc.team293.robot.oi.left[], <bla bla>.right[], <bla bla>.launch1[], <bla bla>.launch2[
		JoystickButton[] left= {null,new JoystickButton(leftStick,1), 
				   new JoystickButton(leftStick,2), 
				   new JoystickButton(leftStick,3), 
				   new JoystickButton(leftStick,4), 
				   new JoystickButton(leftStick,5), 
				   new JoystickButton(leftStick,6), 
				   new JoystickButton(leftStick,7), 
				   new JoystickButton(leftStick,8), 
				   new JoystickButton(leftStick,9), 
				   new JoystickButton(leftStick,10),};
		JoystickButton[] right= {null,new JoystickButton(rightStick,1), 
				   new JoystickButton(rightStick,2), 
				   new JoystickButton(rightStick,3), 
				   new JoystickButton(rightStick,4), 
				   new JoystickButton(rightStick,5), 
				   new JoystickButton(rightStick,6), 
				   new JoystickButton(rightStick,7), 
				   new JoystickButton(rightStick,8), 
				   new JoystickButton(rightStick,9), 
				   new JoystickButton(rightStick,10),};
	
		// Note that for 2018, the launchpad(s) were replaced by 2 more joysticks.
		// As such, we will do the same "vectorization" of their objects, as it helps
		// with recording for use in autonomous replay.
		launch1 = new JoystickButton[] {null, 
		new JoystickButton(annaLeft, 1),
		new JoystickButton(annaLeft, 2),
		new JoystickButton(annaLeft, 3),
		new JoystickButton(annaLeft, 4),
		new JoystickButton(annaLeft, 5),
		new JoystickButton(annaLeft, 6),
		new JoystickButton(annaLeft, 7),
		new JoystickButton(annaLeft, 8),
		new JoystickButton(annaLeft, 9),
		new JoystickButton(annaLeft, 10),
		new JoystickButton(annaLeft, 11),
		new JoystickButton(annaLeft, 12) };

		launch2 = new JoystickButton[] {null, 
		new JoystickButton(annaRight, 1),
		new JoystickButton(annaRight, 2),
		new JoystickButton(annaRight, 3),
		new JoystickButton(annaRight, 4),
		new JoystickButton(annaRight, 5),
		new JoystickButton(annaRight, 6),
		new JoystickButton(annaRight, 7),
		new JoystickButton(annaRight, 8),
		new JoystickButton(annaRight, 9),
		new JoystickButton(annaRight, 10),
		new JoystickButton(annaRight, 11),
		new JoystickButton(annaRight, 12) };
		//theButtons[1] = new ButtonLogic(null, 0, 0);
		
		
		//launch1[1].whileHeld(new PincherExtend());
		launch1[1].whileHeld(new PincherExtend());
		launch1[1].whenReleased(new PincherRetract());
		//theButtons[1].whileHeld(new PincherExtend());
		
		launch1[2].whenPressed(new FeederSetAngle(1));
		
		launch1[3].whileHeld(new FeederRelease(-.9));
		//theButtons[3].whileHeld(new FeederRelease(-.9));

		launch1[4].whenPressed(new FeederSetAngle(3));
		//theButtons[4].whenPressed(new FeederSetAngle(3));

		launch1[5].whileHeld(new FeederThrottle(.5));
		//theButtons[5].whileHeld(new FeederThrottle(.5));

		launch1[6].whenPressed(new FeederSetAngle(0));
		//theButtons[6].whenPressed(new FeederSetAngle(0));

		//button7.whileHeld(new Climb());
		//button7.toggleWhenPressed(new AfterburnerFullThrottle());
		launch1[7].whileHeld(new ClimberReverse());
		launch1[7].whenReleased(new ClimberStop());

		//theButtons[7].whileHeld(new ClimberReverse());

		launch1[8].whileHeld(new Climb());	
		launch1[8].whenReleased(new ClimberStop());
		//theButtons[8].whileHeld(new Climb());	

		launch1[9].toggleWhenPressed(new Feed());
		//theButtons[9].toggleWhenPressed(new Feed());
		//button9.whenReleased(new FeedtoAfterburnerShoot());

		//button11.whenPressed(new LEDsTest());
		launch1[11].whenPressed(new AfterburnerHalfThrottle(.9));
		launch1[11].whenReleased(new AfterburnerHalfThrottle(0));
		//theButtons[11].toggleWhenPressed(new AfterburnerHalfThrottle(.9));

		launch1[12].whenPressed(new AfterburnerHalfThrottle(.7));
		launch1[12].whenReleased(new AfterburnerHalfThrottle(0));
		//theButtons[12].toggleWhenPressed(new AfterburnerHalfThrottle(.7));

		// Second operator joystick: ---------------------
		launch2[1].whileHeld(new FeederMoveAnglePower());
		//theButtons[13].whileHeld(new FeederMoveAnglePower());
		
		launch2[1].whenReleased(new FeederAngleStop());
		// NOTE! We don't mimic this in the autonomous button feature (2nd assignment!)
		
		launch2[8].whenPressed(new FeederSetAngle(1));
		//theButtons[14].whenPressed(new FeederSetAngle(1));

		launch2[11].toggleWhenPressed(new ClimberPoleUp());
		launch2[12].toggleWhenPressed(new ClimberPoleDown());

		//numButtonFns = 15; // includes 0 (null) ops
		
		// Test button options! Remove prior to match play! ----------------
	//	left[2].toggleWhenPressed(new RunAutoLogger());
	//	left[3].whenPressed(new StopAutoLogger());
		
	//	right[2].whenPressed(new TankDriveAutoReplay());
		
	//	right[1].whileHeld(new MoveServoJoystick());
		
	}
}
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	// Toggle the command on when the button is first pressed (<cmd>.init), then
	// stop it cold when the button is pressed a second time (<cmd>.interrupted).
	// button.toggleWhenPressed(new ExampleCommand());
	// Yeah, that info is kinda hidden in the docs!

