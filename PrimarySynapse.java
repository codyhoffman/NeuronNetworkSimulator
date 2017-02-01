// PrimarySynapse.java

import java.util.LinkedList;
import java.util.Scanner;

/** Primary Synapses join neurons to neurons
 *  @author: Douglas W. Jones
 *  @author: Andy W.M. Arthur
 *  @author: Cody J. Hoffman
 *  @version: April 9, 2016
 *  @see Neuron
 *  @see Synapse
 * 
 *  This code is extracted from the April 6, 2016 version of NeuronNetwork.java,
 *  which, in turn was based on the non-alternative solution to MP3,
 *  as well as a simulation framework from the March 11 lecture notes.
 *  This code is further modified to meet the requirements of MP5.
 */
class PrimarySynapse extends Synapse {
    Neuron destination;

    public PrimarySynapse( Neuron dst ) {
        // Called from Synapse.newSynapse() and nowhere else
        // All the field initialization and checking is done there,
        // except the following:
        destination = dst;
    }

    // simulation methods
    void fire(float time) {
        // adjust the voltage of the destination
        destination.kick(time, this.strength);
    }

    // other methods
    public String toString() {
        return (
            "Synapse " +
            ( name != null ? name : "-" ) +
            " " +
            ( source != null ? source.name : "---" ) +
            " " +
            ( destination != null ? destination.name : "---" ) +
            " " + delay + " " + strength
        );
    }
}
