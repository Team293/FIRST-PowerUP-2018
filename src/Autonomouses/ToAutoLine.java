package Autonomouses;

import org.usfirst.frc.team293.robot.Robot;
import org.usfirst.frc.team293.robot.commands.DriveStraightTimed;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ToAutoLine extends CommandGroup {

    public ToAutoLine() {
    	addSequential(new DriveStraightTimed(4.5));
    	//if (Robot.switchLeft == false){
    		//addSequential(new TimedAngleMotor(.5));
    		//addSequential(new )
    	}
    }

