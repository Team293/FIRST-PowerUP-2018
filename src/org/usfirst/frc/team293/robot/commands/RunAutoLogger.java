package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunAutoLogger extends Command {

    public RunAutoLogger() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.dataLogger);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.dataLogger.writeFileStart();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.dataLogger.writeFileLine();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       // return (Robot.DataLog.time.get()-Robot.DataLog.initTime>=15);
    	return (false);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.dataLogger.closeFile();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run.  This *may* get called if we have set
    // a ToggleWhenPressed option for launching and stopping this code ... ?
    protected void interrupted() {
    	Robot.dataLogger.closeFile();
    }
}
