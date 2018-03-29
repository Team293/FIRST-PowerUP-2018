package Autonomouses;

import org.usfirst.frc.team293.robot.commands.AfterburnerHalfThrottle;
import org.usfirst.frc.team293.robot.commands.DriveStraightDistanceChristian;
import org.usfirst.frc.team293.robot.commands.DriveStraightTimeChristian;
import org.usfirst.frc.team293.robot.commands.DriveTurnGyroInPlace;
import org.usfirst.frc.team293.robot.commands.FeederRelease;
import org.usfirst.frc.team293.robot.commands.FeederSetAngle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LeftSide extends CommandGroup {

    public LeftSide(String choice) {
    	if (choice.charAt(0) == 'L'){ //Closer is on our side
    		//addSequential(new DriveStraightDistanceChristian(.75,164)); do this but a bit of a strafe
    		addSequential(new DriveStraightDistanceChristian(.75, 40));
    		addSequential(new DriveTurnGyroInPlace(30, .75));		
    		addSequential(new DriveStraightDistanceChristian(.75, 16));
    		addSequential(new DriveTurnGyroInPlace(-30, -.75));		
    		addSequential(new DriveStraightDistanceChristian(.75, 85));
    		 		
    		addSequential(new DriveStraightTimeChristian(-.3,.25));//slowdown
    		
    		addSequential(new DriveTurnGyroInPlace(-90,-.9));
    		addParallel(new FeederSetAngle(2));
    		addSequential(new DriveStraightTimeChristian(-.75, 2));	
    		addSequential(new FeederRelease(.45));
    	} else if(choice.charAt(1) == 'L'){
    		
    		addSequential(new DriveStraightDistanceChristian(.75,284));//this is small on purpose from 304

    		addSequential(new DriveStraightTimeChristian(-.2,.25));
    		addSequential(new DriveTurnGyroInPlace(80, .75));
    		//addSequential(new DriveStraightDistanceChristian(.5, 6));
    		addParallel(new AfterburnerHalfThrottle(.7));
    		addSequential(new FeederRelease(1));
    	} else {
    		addSequential(new DriveStraightDistanceChristian(.5,164));
    		//addSequential(new DriveTurnGyroInPlace(-90,-.75));
    	}
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
