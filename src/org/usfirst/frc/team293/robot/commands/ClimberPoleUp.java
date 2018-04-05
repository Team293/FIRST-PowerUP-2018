package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimberPoleUp extends Command {
	private boolean sensorDetect;
	private boolean detectingSensor;
    public ClimberPoleUp() {
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	detectingSensor = false;
    	Robot.winch.Up();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	sensorDetect = Robot.winch.windOut();
    	if (detectingSensor == false && sensorDetect == false){
    		detectingSensor = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       /* if (detectingSensor == true && sensorDetect == true){
        	return true;
        }
        else if (detectingSensor == false && sensorDetect == true){
        	return false;
        }
        else if (detectingSensor == false && sensorDetect == false){
        	detectingSensor = true;
        	return false;
        }
        else {
        	return false;
        }*/
    	if (detectingSensor == false) {
    		return false;
    	}
    	else{
    		return sensorDetect;
    	}	
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.winch.stopWind();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.winch.stopWind();
    }
}
