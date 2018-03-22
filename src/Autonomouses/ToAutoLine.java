package Autonomouses;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.commands.DriveStraightDumbTimed;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ToAutoLine extends CommandGroup {

    public ToAutoLine() {
    	addSequential(new DriveStraightDumbTimed(4.5));
    	//if (Robot.switchLeft == false){
    		//addSequential(new TimedAngleMotor(.5));
    		//addSequential(new )
    	}
    }

