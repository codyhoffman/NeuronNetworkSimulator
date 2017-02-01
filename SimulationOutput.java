// SimulationOutput.java
/** Simulation Output printing model
 *  This set of methods provides a service to read input using
 *  a Java {@link Scanner}, and then scheduling events using {@link Simulator}
 *  to produce output in certain intervals given in the input file, then terminate.
 *  @author: Cody J. Hoffman
 *  @version: MP6
 *  @see java.util.Scanner
 *  @see Simulator
 */

import java.util.Scanner;

class SimulationOutput {
	private static float outputInterval = 99.99f;  // interval between output displays
	private static float terminationTime = 99.99f; // time where the simulation should terminate 
	private float time = 0.0f; // beginning time of simulation

	// format textual output
	private static final String noFire = "  |   ";
	private static final String oneFire = "  |-  ";
	private static final String multipleFire = "  |=  ";

	/** Scans the line of the input file contain the output information, and saves
	 *  the values for the interval and termination time, also schedules the
	 *  first event of the output model at time zero to print the header
	 */
	public static void setOutput( Scanner sc ){
		// scan output interval interval
		outputInterval = ScanSupport.nextFloat(
			sc, 
			() -> "-- Invalid Input"
		);
		// scan termination time
		terminationTime = ScanSupport.nextFloat(
			sc,
			() -> "-- Invalid Input"
		);
		
		/* Schedule an event at zero to launch the output process
		 * and output header line
		 */
		Simulator.schedule(
			new Simulator.Event( 0.0f ) {
				void trigger() {
					displayHeader( time );
				}
			}
		);
	}
	public String toString(){
		return(
			"output " + 
			outputInterval + " " +
			terminationTime
		);
	}	
	/** This method takes each name from the list of neurons in the simulation 
	 *  and truncates it to 5 spaces, or adds padding up to 5 spaces, then adds it to the header line
	 *  this method is only called once, it then proceeds to call displayOutput to keep the output 
	 *  printing for the duration of the simulation
	 */
	private static void displayHeader(float time){
		for( Neuron n: NeuronNetwork.neurons ){
			String nm = n.name;

			// truncate names longer than 5
			if( nm.length() > 5) { 
				nm = nm.substring( 0, 5 );
			}

			// output edited name
			System.out.append( nm );

			// output padding up to next column
			if( nm.length() < 5) {
				System.out.append(
					"     ".substring( 0, 5 - nm.length() )
				);
			}
			
			// add a space after the name
			System.out.append( ' ' );
		}
		System.out.println();
		
		// after the header is printed, print first output of values
		displayOutput( time );
	}

	/** for each neuron in the simulation, at the given time, it prints the line of output
	 *  and if the current time is less than the termination time value then a new 
	 *  displayOutput event is scheduled at time + outputInterval
	 */
	private static void displayOutput( float time ) {
		/* Print the first set of output after the first interval
		 */
		if( time > 0.0f) {
			/* for each neuron in the list add the corresponding output to the line
		 	 */
			for(Neuron n: NeuronNetwork.neurons){
				int theCount = n.getCount(); // number of times the neuron fires
				
				// determine which textual output to use	
				if( theCount == 1 ) {
					System.out.append( oneFire );
				}else if( theCount > 1) {
					System.out.append( multipleFire );
				}else if( theCount == 0 ) {
					System.out.append( noFire );
				}else{
					System.out.append( "xxxxx" );
				}
			}
			System.out.println();
		}

		/* if the time is less than the allowed run time, schedule a new event
		 * to print the next set of outputs at time + interval
		 */
		if(time < terminationTime) {
			Simulator.schedule(
				new Simulator.Event( time + outputInterval ) {
					void trigger() {
						displayOutput( time );
					}
				}
			);
		}else{
			// if time exceeds the terminationTime, terminate the program
			System.exit( 0 );
		}
	}
	
}

