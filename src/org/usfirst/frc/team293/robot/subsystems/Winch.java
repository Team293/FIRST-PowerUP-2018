package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Winch extends Subsystem {

		private Spark Climber1;
		private Spark Climber2;
		private Relay Release;	
		private DigitalInput forwardLimitSwitch;
	public Winch(){
		Climber1 = new Spark(RobotMap.climbMotors[0]);
		Climber2 = new Spark(RobotMap.climbMotors[1]);
		Release = new Relay(0);
		forwardLimitSwitch = new DigitalInput(5);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void move(double pwm){
    	Climber1.set(pwm);
    	Climber2.set(pwm);
    }
    public void reverse(double pwm){
    	Climber1.set(-pwm);
    	Climber2.set(-pwm);
    }  
    public void Up(){
    	Release.set(Relay.Value.kReverse);
    }
    public void down(){
    	Release.set(Relay.Value.kForward);
    }
    public boolean windOut(){  	
    	return forwardLimitSwitch.get();
    }
    public void stopWind(){
    	Release.set(Relay.Value.kOff);
    }
}


