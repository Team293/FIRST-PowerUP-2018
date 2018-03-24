package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The Winch has an extension rod for the hook that stops on a banner sensor
 *  as well as a climbing mechanism with two 775 pros.
 */
public class Winch extends Subsystem {

		private Spark Climber1;
		private Spark Climber2;
		private VictorSP Release;	
		private DigitalInput forwardLimitSwitch;
		
	public Winch() {
		Climber1 = new Spark(RobotMap.climbMotors[0]);
		Climber2 = new Spark(RobotMap.climbMotors[1]);
		Release = new VictorSP(RobotMap.poleMotor);
		forwardLimitSwitch = new DigitalInput(RobotMap.winchPoleLimit);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
     //   setDefaultCommand(new ClimberStop());
    }
    /**
     * Move allows the climber to climb.
     * @param pwm Is the speed to set the climber motors
     */
    public void move(double pwm) {
    	Climber1.set(pwm);
    	Climber2.set(pwm);
    }
    /**
     * Move allows the climber to reverse inward.
     * @param pwm Is the speed to set the climber motors
     */
    public void reverse(double pwm) {
    	Climber1.set(pwm);
    	Climber2.set(pwm);
    }  
    /**
     * This raises the pole with the hook
     */
    public void Up() {
    	Release.set(.1);
    }
    /**
     * This lowers the pole with the hook
     */
    public void down() {
    	Release.set(-.1);
    }
    /**
     * This allows the hook to extend correctly
     * @return The value of the sensor
     */
    public boolean windOut() {  	
    	return forwardLimitSwitch.get();
    }
    /**
     * This stops the motor from winding.
     */
    public void stopWind() {
    	Release.set(0);
    }
}


