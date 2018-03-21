package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.commands.SendDataToSmartDashboard;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class SmartDashboardMonitorOutputs extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new SendDataToSmartDashboard());
    }
    public void sendToSmartDashboard() {
    	
    }
}

