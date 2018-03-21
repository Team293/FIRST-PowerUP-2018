package org.usfirst.frc.team293.robot;


import org.usfirst.frc.team293.robot.commands.AfterburnerAdjustable;
import org.usfirst.frc.team293.robot.commands.AfterburnerFullThrottle;
import org.usfirst.frc.team293.robot.commands.AfterburnerHalfThrottle;
import org.usfirst.frc.team293.robot.commands.AfterburnerRPM;
import org.usfirst.frc.team293.robot.commands.Climb;
import org.usfirst.frc.team293.robot.commands.PincherExtend;
import org.usfirst.frc.team293.robot.commands.Feed;
//import org.usfirst.frc.team293.robot.commands.FeedToExchange;
import org.usfirst.frc.team293.robot.commands.FeedToUpperPosition;
import org.usfirst.frc.team293.robot.commands.FeederAngle;
import org.usfirst.frc.team293.robot.commands.FeederFullDown;
import org.usfirst.frc.team293.robot.commands.FeederFullUp;
import org.usfirst.frc.team293.robot.commands.FeederRPM;
import org.usfirst.frc.team293.robot.commands.FeederRelease;
import org.usfirst.frc.team293.robot.commands.FeederThrottle;
import org.usfirst.frc.team293.robot.commands.FeedtoAfterburnerShoot;
import org.usfirst.frc.team293.robot.commands.FeederMoveAnglePower;
import org.usfirst.frc.team293.robot.commands.ClimberPoleUp;
import org.usfirst.frc.team293.robot.commands.ClimberPoleDown;
import org.usfirst.frc.team293.robot.commands.PincherRetract;
import org.usfirst.frc.team293.robot.commands.RunAutoLogger;
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
	public static Joystick launchpad=new Joystick(2);
	public static Joystick launchpad2=new Joystick(3);
	public OI() {
		// Assign a reference to each joystick's buttons as a vector map by index
		// Note that index 0 is not assigned, so that the numbers match!
		// When we extract these button press states, remember they are boolean: (true/false).
		// For storing in a file, we have to record them as doubles: (0.,1.).
		// The button arrays are thus:
		// org.usfirst.frc.team293.robot.oi.left[], <bla bla>.right[], <bla bla>.launch1[], <bla bla>.launch2[]
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
		JoystickButton[] launch1= {null, 
		new JoystickButton(launchpad, 1),
		new JoystickButton(launchpad, 2),
		new JoystickButton(launchpad, 3),
		new JoystickButton(launchpad, 4),
		new JoystickButton(launchpad, 5),
		new JoystickButton(launchpad, 6),
		new JoystickButton(launchpad, 7),
		new JoystickButton(launchpad, 8),
		new JoystickButton(launchpad, 9),
		new JoystickButton(launchpad, 10),
		new JoystickButton(launchpad, 11),
		new JoystickButton(launchpad, 12) };

		JoystickButton[] launch2= {null, 
		new JoystickButton(launchpad2, 1),
		new JoystickButton(launchpad2, 2),
		new JoystickButton(launchpad2, 3),
		new JoystickButton(launchpad2, 4),
		new JoystickButton(launchpad2, 5),
		new JoystickButton(launchpad2, 6),
		new JoystickButton(launchpad2, 7),
		new JoystickButton(launchpad2, 8),
		new JoystickButton(launchpad2, 9),
		new JoystickButton(launchpad2, 10),
		new JoystickButton(launchpad2, 11),
		new JoystickButton(launchpad2, 12) };
		// The launchpad buttons are "special" as they are identified by explicit name

/*		JoystickButton button1=new JoystickButton(launchpad, 1);
		JoystickButton button2=new JoystickButton(launchpad, 2);
		JoystickButton button3=new JoystickButton(launchpad, 3);
		JoystickButton button4=new JoystickButton(launchpad, 4);
		JoystickButton button5=new JoystickButton(launchpad, 5);
		JoystickButton button6=new JoystickButton(launchpad, 6);
		JoystickButton button7=new JoystickButton(launchpad, 7);
		JoystickButton button8=new JoystickButton(launchpad, 8);
		JoystickButton button9=new JoystickButton(launchpad, 9);	
		JoystickButton button10=new JoystickButton(launchpad, 10);
		JoystickButton button11=new JoystickButton(launchpad, 11);
		JoystickButton button12=new JoystickButton(launchpad, 12);
		JoystickButton button21=new JoystickButton(launchpad2, 1);
		JoystickButton button22=new JoystickButton(launchpad2, 2);
		JoystickButton button23=new JoystickButton(launchpad2, 3);
		JoystickButton button24=new JoystickButton(launchpad2, 4);
		JoystickButton button25=new JoystickButton(launchpad2, 5);
		JoystickButton button26=new JoystickButton(launchpad2, 6);
		JoystickButton button27=new JoystickButton(launchpad2, 7);
		JoystickButton button28=new JoystickButton(launchpad2, 8);
		JoystickButton button29=new JoystickButton(launchpad2, 9);	
		JoystickButton button210=new JoystickButton(launchpad2, 10);
		JoystickButton button211=new JoystickButton(launchpad2, 11);
		JoystickButton button212=new JoystickButton(launchpad2, 12);
*/		
		// OK, everything is defined, now do some command assignments!
		
		// Driver joystick assignments: ---------------------------
		//left[1].whenReleased(new StopFeeder());
		//left[2].toggleWhenPressed(new AfterburnerFullThrottle());
		//left[2].whenReleased(new StopAfterburner());
		//left[3].whileHeld(new AfterburnerAdjustable());
		//left[3].whenReleased(new StopAfterburner());
		//left[4].whileHeld(new AfterburnerRPM(3574.2));
		//left[4].whenReleased(new StopAfterburner());
		//left[5].whenPressed(new CalibrateFeeder());
		//button1.whenPressed(new FeedToExchange());
		
		// First operator joystick: ------------------------
//		button1.whileHeld(new Extend_Cylinder());
//		button1.whenReleased(new Retract_Cylinder());
		launch1[1].whileHeld(new PincherExtend());
		launch1[1].whenReleased(new PincherRetract());
		

		launch1[2].whenPressed(new FeederAngle(0));

		//button3.whileHeld(new FeedToUpperPosition());
		launch1[3].whileHeld(new FeederRelease(-.5));
		launch1[3].whenReleased(new FeederStop());

		launch1[4].whenPressed(new FeederFullDown());

		launch1[5].whileHeld(new FeederThrottle(.5));
		launch1[5].whenReleased(new FeederStop());

		launch1[6].whenPressed(new FeederFullUp());
		
		//button7.whileHeld(new Climb());
		//button7.toggleWhenPressed(new AfterburnerFullThrottle());
		launch1[7].whileHeld(new ClimberReverse());
		launch1[7].whenReleased(new ClimberStop());

		launch1[8].whileHeld(new Climb());	
		launch1[8].whenReleased(new ClimberStop());

		launch1[9].whenPressed(new Feed());
		//button9.whenReleased(new FeedtoAfterburnerShoot());

		//button10.whenPressed(new AfterburnerShoot());
		launch1[10].whenPressed(new AfterburnerRPM(1));
		launch1[10].whenReleased(new AfterburnerStop());

		//button11.whenPressed(new LEDsTest());
		launch1[11].whenPressed(new AfterburnerHalfThrottle(.9));
		launch1[11].whenReleased(new AfterburnerStop());

		launch1[12].whenPressed(new AfterburnerHalfThrottle(.7));
		launch1[12].whenReleased(new AfterburnerStop());

		// Second operator joystick: ---------------------
		launch2[1].whileHeld(new FeederMoveAnglePower());
		launch2[1].whenReleased(new FeederAngleStop());

		launch2[11].toggleWhenPressed(new ClimberPoleUp());
		launch2[12].toggleWhenPressed(new ClimberPoleDown());
//		button22.toggleWhenPressed(new RunAutoLogger());
		
		// Test button options! Remove prior to match play! ----------------
		left[2].toggleWhenPressed(new RunAutoLogger());
		//left[3].whenPressed(new StopAutoLogger());
		
		//right[2].whenPressed(new TankDriveAutoReplay());
		
		//right[1].whileHeld(new MoveServoJoystick());
		
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
}
