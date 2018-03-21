package org.usfirst.frc.team293.robot.commands;

import org.usfirst.frc.team293.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class FeederCalibrate extends Command {

    public FeederCalibrate() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.feeder);
    	requires(Robot.pincher);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putBoolean("Calibrating", true);
    	Robot.pincher.unpinch();
    	Robot.feeder.angleMotor.set(ControlMode.PercentOutput, 0.2);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Robot.Feeder.calibrate();
    	
    	
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (Robot.feeder.upperLimit.get() == false){
        Robot.feeder.angleMotor.set(ControlMode.PercentOutput, 0);	
    	return (true);
        }
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.feeder.calibrate(true);
    	SmartDashboard.putBoolean("Calibrating", false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
