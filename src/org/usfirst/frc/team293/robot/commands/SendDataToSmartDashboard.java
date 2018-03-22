package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command logs various data to the smartdashboard and always runs
 */
public class SendDataToSmartDashboard extends Command {

    public SendDataToSmartDashboard() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("Drive Encoder Left", Robot.driveTrain.leftEncoder.getRaw());
    	SmartDashboard.putNumber("Drive Encoder Right", Robot.driveTrain.rightEncoder.getRaw());
    	SmartDashboard.putNumber("Drive IMU", Robot.driveTrain.imu.getFusedHeading());
    	SmartDashboard.putNumber("Feeder Angle Encoder from reset", Robot.feederAngle.angleEncoder.get());
    	SmartDashboard.putNumber("Auto Distance Driven", Robot.driveTrain.readEnc()[0]);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
