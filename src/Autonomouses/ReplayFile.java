package Autonomouses;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * ReplayFile reads in the desired autonomous file number and extracts
 * recorded motor. controls, encoders, and AHRS sensor data for autonomous mode.
 * NOTE: This is a "home grown" CSV reader that wants to see a SPECIFIC FORMAT,
 * which appends a comma and space after each double, then appends a newline at the end of
 * each row of data.  The newline is read in separately as an explicit character to avoid 
 * getting "fancy" with the options for parsing, or re-writing the logger routine.  Sorry.
 * We are building this for speed, since the file I/O has to happen in real-time autonomous!
 * Channels are currently: time, 6 drivetrain values, and 15 buttons (total of 22).
 */
public class ReplayFile {

	Scanner scanner;
	File fp;
	long startTime;

	boolean onTime = true; // checks that we don't read and playback the file too quickly
	double nextDouble;
	double timeHack; // first column time value at current line in file

	public double chanValues[] = new double[22]; // time, 6 drivetrain chans, 15 buttons

	public ReplayFile() {
	}
	
	public void init( int fnum ) throws FileNotFoundException {
		// Opens up file for reading, and records current time
		// fnum is autonomous file number, used in file name

		// autoFile is the playback file name
		String autoFile = new String("/home/lvuser/recordedAuto" + fnum + ".csv");
		System.out.println("Looking for file: " + autoFile);
		// scanner is the object for extracting the values
		try {
			fp = new File( autoFile );
		} catch (Error e) { // ugly ASCII blast to monitor of file I/O problem
			System.out.println("**************************************");
			System.out.println("**************************************");
			System.out.println("**************************************");
			System.out.println("**************************************");
			System.out.println("     Issues with opening file???");
			System.out.println("**************************************");
			System.out.println("**************************************");
			System.out.println("**************************************");
			System.out.println("**************************************");
		}
		scanner = new Scanner(fp);

		// Let scanner know that the numbers are separated by a comma or a newline, as it is a .csv file
		scanner.useDelimiter(", "); // comma then space
		// First read in the two lines of text to get to the numerics.
		// Ideally, this might check the number of text blobs to count channel numbers.
		scanner.nextLine(); // the channel names
		scanner.nextLine(); // the channel units
		// Lets set start time to the current time you begin autonomous
		startTime = System.currentTimeMillis();	
	}
	
	public int playBack() {
		// Sequentially read the lines of the file by first extracting the time value.
		// This is compared to how long we've been processing this file, and if it
		// is a time in the future, we wait until we've caught up with the file to
		// continue reading the rest of the values at that line in the file.
		double t_delta;
		int EOF = 0; // flag for end of file (returned from call)
		int numChannels = 22; // current files have t, then 6 drivetrain channels
		                      // followed by 15 (count 'em!) buttons
		
		//if recordedAutoXX.csv has a double to read next, then read it
		if (scanner.hasNextDouble())
		{	
			EOF = 0; // more to read!
			// if we have waited the recorded amount of time assigned to each
			// respective motor value, then move on to the next time value
			// prevents the macro playback from getting ahead of itself and writing
			// different motor values too quickly.  HOWEVER, we don't trap for being
			// too slow - yet.  That would have us keep reading the file to catch up.
			if(onTime)
			{
				//take next time value
				timeHack = scanner.nextDouble();
			}
			
			// Get time recorded for values minus how far into replaying it we are
			// If not negative or zero, hold up, as we have "future" data
			t_delta = timeHack - (System.currentTimeMillis()-startTime)/1000.;
			
			//if we are on time, then set motor values
			if (t_delta <= 0)
			{
				chanValues[0] = t_delta; // first channel is time
				// Get the remaining doubles on this line of the file
				for ( int i=1; i<numChannels; i++) {
					chanValues[i] = scanner.nextDouble();
				}
				try {
					scanner.nextLine(); // read in the EOL character!
				} catch (Error e) {
					// do nothing, this is EOF!
				}
				// All the channels available, so make it dance!
				
				//go to next time point
				onTime = true;
			}
			//else don't change the values of the motors until we are "onTime"
			else
			{
				onTime = false;
			}
		}
		//end play, there are no more values to find
		else
		{
			if (scanner != null) 
			{
				EOF = 1;
				scanner.close();
//				scanner = null;
				// Maybe set final channel values to zero??
				for ( int i=0; i<numChannels; i++) {
					chanValues[i] = 0.0;
				}				
			}
		}
		return EOF;
	}
	
	public void close() {
		scanner.close();
		scanner = null;
	}
}
