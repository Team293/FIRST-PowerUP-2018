package Autonomouses;

import edu.wpi.first.wpilibj.command.Command;

/**
 * ButtonLogic
 * This class stores the commmand and operational logic that is "connected" to a joystick button,
 * as assigned within the OI.java class.  The idea is that a recorded button press can be processed
 * with this class to execute (or not) the appropriate command function from a replay file.
 * The goal is to provide button-press replay functionality from a file that has recorded
 * actual robot motion and button pressing when executing a path and command history that is to
 * be replayed during an autonomous period.  Goals are good.
 * @author bob
 *
 */
public class ButtonLogic {
//	int assignedButton; // integer for desired button assignment
	int buttonState = 0; // button state recorded at completion of this task's method
	int opState = 0; // records operating (1) or dormant (0) state of associated command for toggles
	Command cmd = null; // operating command for execution (or not) from recorded button state
	int logicType; // stored operational mode for executing this command
	public final static int WHILEHELD = 1;
	public final static int WHENPRESSED = 2;
	public final static int WHENRELEASED = 3;
	public final static int TOGGLEWHENPRESSED = 4;

	// Creator assigns internal variables
	public ButtonLogic() {
	}

	// "Workalike" methods for assigning functionality that look like JoystickButton methods
	public void whileHeld( Command theCmd ) {
		cmd = theCmd;
		logicType = WHILEHELD;
	}
	public void whenPressed( Command theCmd ) {
		cmd = theCmd;
		logicType = WHENPRESSED;
	}
	public void whenReleased( Command theCmd ) {
		cmd = theCmd;
		logicType = WHENRELEASED;
	}
	public void toggleWhenPressed( Command theCmd ) {
		cmd = theCmd;
		logicType = TOGGLEWHENPRESSED;
	}
	
	// Execution fires commands based on button state
	public void applyButton( int newButtonState ) {
		// If we didn't assign a command (the button is not used?),
		// then just exit.
		if ( null == cmd ) {
			return;
		}
		// Processing logic uses a switch statement
		switch (logicType) {
		
		case WHILEHELD:
			if ( newButtonState > 0 ) {
				cmd.start();
			}
			break;
			
		case WHENPRESSED:
			if (( buttonState == 0 )&&( newButtonState == 1 )) { // rising edge
				cmd.start();
			}
			break;
			
		case WHENRELEASED:
			if (( buttonState == 1 )&&( newButtonState == 0 )) { // falling edge
				cmd.start();
			}
			break;

		case TOGGLEWHENPRESSED:
			if (( buttonState == 0 )&&( newButtonState == 1 )) { // rising edge
				if ( opState == 0 ) {
					cmd.start();
					opState = 1;
				} else {
					cmd.cancel();
					opState = 0;
				}
			}
			break;
		default:
		}
		buttonState = newButtonState;
	}
	
}
