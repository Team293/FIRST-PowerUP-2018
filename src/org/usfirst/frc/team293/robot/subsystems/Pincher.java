package org.usfirst.frc.team293.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pincher extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private Compressor comp;
	private Solenoid l_cylinder_open;
	private Solenoid l_cylinder_close;
	private Solenoid r_cylinder_open;
	private Solenoid r_cylinder_close;
	public Pincher(){
		comp = new Compressor(0);
		l_cylinder_open = new Solenoid(0);
		l_cylinder_close = new Solenoid(1);
		r_cylinder_open = new Solenoid(2);
		r_cylinder_close = new Solenoid(3);
		comp.setClosedLoopControl(true);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void pinch(){
    	l_cylinder_open.set(true);
    	//System.out.println("Extending!");
    	l_cylinder_close.set(false);	
    	r_cylinder_open.set(true);
    	r_cylinder_close.set(false);
    }
    public void unpinch(){
    	l_cylinder_open.set(false);
    	l_cylinder_close.set(true);	
    	r_cylinder_open.set(false);
    	r_cylinder_close.set(true);	
    	//System.out.println("Retracting");
    }
}

