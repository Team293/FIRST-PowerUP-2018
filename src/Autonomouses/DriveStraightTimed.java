package Autonomouses;

import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 *
 */
public class DriveStraightTimed extends TimedCommand {

    public DriveStraightTimed(double timeout) {
        super(timeout);
        requires(Robot.driveTrain);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.tankdrive(.5, .5);
    }

    // Called once after timeout
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
