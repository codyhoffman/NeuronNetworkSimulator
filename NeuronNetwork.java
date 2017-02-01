// NeuronNetwork.java

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.lang.Math;

/** NeuronNetwork is the main class that builds the whole model
 *  @author: Douglas W. Jones
 *  @author: Andy W.M. Arthur
 *  @author: Cody J. Hoffman
 *  @version: April 13, 2016
 *  @see Neuron
 *  @see Synapse
 *  @see Errors
 *  @see Simulator
 * 
 *  This code is extracted from the April 6, 2016 version of NeuronNetwork.java,
 *  which, in turn was based on the non-alternative solution to MP3,
 *  as well as a simulation framework from the March 11 lecture notes.
 *  This code is further modified to meet the requirements of MP5.
 */
public class NeuronNetwork {

    // the sets of all neurons and synapses
    static LinkedList <Neuron> neurons
        = new LinkedList <Neuron> ();
    static LinkedList <Synapse> synapses
        = new LinkedList <Synapse> ();

    /** Look up s in neurons, find that Neuron if it exists
     *  return null if not.
     */
    public static Neuron findNeuron( String s ) {
        /* special case added because scan-support can return null */
        if (s == null) return null;

        /* search the neuron list */
        for (Neuron n: neurons) {
            if (n.name.equals(s)) {
                return n;
            }
        }
        return null;
    }

    /** Look up s in synapses, find that Synapse if it exists
     *  return null if not.
     */
    public static Synapse findSynapse( String s ) {
        /* special case added because scan-support can return null */
        if (s == null) return null;

        /* search the synapse list */
        for (Synapse sy: synapses) {
            if ((sy.name != null) && (sy.name.equals(s))) {
                return sy;
            }
        }
        return null;
    }

    /** Initialize the neuron network by scanning its description
     */
    static void initializeNetwork( Scanner sc ) {
        while (sc.hasNext()) {
            String command = sc.next();
            if ("neuron".equals( command )) {
                try {
                    neurons.add( new Neuron( sc ) );
                } catch (Neuron.IllegalNameEx e) {}
            } else if ("synapse".equals( command )) {
                try {
                    synapses.add( Synapse.newSynapse(sc) );
                } catch (Synapse.IllegalNameEx e) {}
            } else if ("output".equals( command )) {
                SimulationOutput.setOutput( sc );
            } else {
                Errors.warning( command + " -- what is that" );
                sc.nextLine();
            }
        }
    }

    /** Print out the neuron network from the data structure
     */
    static void printNetwork() {
        for (Neuron n:neurons) {
            System.out.println( n.toString() );
        }
        for (Synapse s:synapses) {
            System.out.println( s.toString() );
        }
    }

    /** Main program
     * @see initializeNetwork
     * @see printNetwork
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            Errors.fatal( "missing file name" );
        }
        if (args.length > 1) {
            Errors.fatal( "too many arguments" );
        }
        try {
            initializeNetwork( new Scanner(new File(args[0])) );
        } catch (FileNotFoundException e) {
            Errors.fatal( "file not found: " + args[0] );
        }
        if (Errors.errCount == 0){
            Simulator.run();
        } else {
            printNetwork();
        }
    }
}
