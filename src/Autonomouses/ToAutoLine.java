package Autonomouses;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.commands.DriveStraightDistanceChristian;
import org.usfirst.frc.team293.robot.commands.DriveStraightDumbTimed;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ToAutoLine extends CommandGroup {

    public ToAutoLine() {
    	addSequential(new DriveStraightDistanceChristian(.4,12));
    	//if (Robot.switchLeft == false){
    		//addSequential(new TimedAngleMotor(.5));
    		//addSequential(new )
    	}
    }

