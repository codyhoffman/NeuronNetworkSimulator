// SecondarySynapse.java

import java.util.Scanner;

/** Secondary synapses join neurons to primary synapses
 *  @author: Douglas W. Jones
 *  @author: Andy W.M. Arthur
 *  @version: April 9, 2016
 *  @see Neuron
 *  @see Synapse
 *  @see PrimarySynapse
 *  @see ScanSupport
 *  @see Errors 
 *  @see Simulator
 * 
 *  This code is extracted from the April 6, 2016 version of NeuronNetwork.java,
 *  which, in turn was based on the non-alternative solution to MP3,
 *  as well as a simulation framework from the March 11 lecture notes.
 *  This code is further modified to meet the requirements of MP5.
 */
class SecondarySynapse extends Synapse {
    PrimarySynapse destination;

    public SecondarySynapse( Synapse dst ) {
        // Called from Synapse.newSynapse() and nowhere else
        // All the field initialization and checking is done there,
        // except the following:

        if ( (dst != null)
        &&   (dst instanceof SecondarySynapse) ) {
            Errors.warning(
                this.toString() +
                " -- destination is a secondary synapse"
            );
            destination = null;
        } else {
            destination = (PrimarySynapse) dst;
        }
    }

    // simulation methods
    void fire(float time) {
        destination.strength += this.strength;
    }

    // other methods
    public String toString() {
        return (
            "Synapse " +
            (name != null ? name : "-") +
            " " +
            ( source != null ? source.name : "---" ) +
            " " +
            ( destination != null ? destination.name : "---" ) +
            " " + delay + " " + strength
        );
    }
}
