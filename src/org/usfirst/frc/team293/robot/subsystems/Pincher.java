package org.usfirst.frc.team293.robot.subsystems;

import org.usfirst.frc.team293.robot.RobotMap;
import org.usfirst.frc.team293.robot.commands.PincherRetract;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The pincher opens and closes to grab a cube
 */
public class Pincher extends Subsystem {

	private Compressor comp;
	private Solenoid lCylinderOpen;
	private Solenoid lCylinderClose;
	private Solenoid rCylinderOpen;
	private Solenoid rCylinderClose;
	
	public Pincher() {
		comp = new Compressor(RobotMap.compressor);
		lCylinderOpen = new Solenoid(RobotMap.leftCylinderOpen);
		lCylinderClose = new Solenoid(RobotMap.leftCylinderClose);
		rCylinderOpen = new Solenoid(RobotMap.rightCylinderOpen);
		rCylinderClose = new Solenoid(RobotMap.rightcylinderClose);
		comp.setClosedLoopControl(true);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new PincherRetract());
    }
    /**
     * Pinches the cube
     */
    public void pinch() {
    	lCylinderOpen.set(true);
    	lCylinderClose.set(false);	
    	rCylinderOpen.set(true);
    	rCylinderClose.set(false);
    }
    /**
     * Releases the cube
     */
    public void unpinch() {
    	lCylinderOpen.set(false);
    	lCylinderClose.set(true);	
    	rCylinderOpen.set(false);
    	rCylinderClose.set(true);	
    }
}

