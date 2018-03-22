package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class FeederSensorsMonitor extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	DigitalInput Feederlimit;
	
	public FeederSensorsMonitor(){
		Feederlimit = new DigitalInput(RobotMap.feederBoxLimit);
	//	PinchPhotoSwitch = new DigitalInput(RobotMap.PinchPhotoSwitch);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
   // public boolean getPhotoSwitch(){
    	//return(PinchPhotoSwitch.get());
   // }
/*    public boolean getFeederLimit(){
    	return(Feederlimit.get());
    }*/
}

