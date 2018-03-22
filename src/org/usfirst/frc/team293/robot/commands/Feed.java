package org.usfirst.frc.team293.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Feed extends CommandGroup {

    public Feed() {
    	addSequential(new PincherRetract());
    	addSequential(new FeederSetAngle(3));
       
        
    }
}
