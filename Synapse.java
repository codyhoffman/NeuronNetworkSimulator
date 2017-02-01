// Synapse.java

import java.util.regex.Pattern;
import java.util.Scanner;

/** Synapses join neurons and come in several flavors
 *  @author: Douglas W. Jones
 *  @author: Andy W.M. Arthur
 *  @version: April 9, 2016
 *  @see Neuron
 *  @see Synapse
 *  @see PrimarySynapse
 *  @see SecondarySynapse
 *  @see ScanSupport
 *  @see Errors 
 *  @see Simulator
 * 
 *  This code is extracted from the April 6, 2016 version of NeuronNetwork.java,
 *  which, in turn was based on the non-alternative solution to MP3,
 *  as well as a simulation framework from the March 11 lecture notes.
 */
abstract class Synapse {
    // default values below for errors with incompletely defined synapses
    Neuron source;          // source for this synapse
    Float delay = 99.99f;
    Float strength = 99.99f;
    String name = null;     // name of this synapse, if it has one

    public static class IllegalNameEx extends Exception {}

    // really private to Synapse initializer
    private static final Pattern noName = Pattern.compile( "-" );

    // generic initializer
    static Synapse newSynapse( Scanner sc ) throws IllegalNameEx {
        // proxies for fields until we know the type of this synapse
        String myName = null;
        Neuron mySource = null;

        // only one of the following proxies will be non-null
        Neuron myPrimaryDest = null;
        Synapse mySecondaryDest = null;

        // the Synapse we're allocating
        Synapse mySynapse = null;
        
        // scan and process one synapse
        if (sc.hasNext( noName )) { // unnamed synapse
            sc.next( noName );
        } else { // named synapse, process the name
            myName = ScanSupport.nextName(
                sc,
                () -> "Synapse ???"
            );
            if (myName == null) {
                // nextName() already reported syntax error
                sc.nextLine();
                throw new IllegalNameEx ();
            }
            if ((NeuronNetwork.findNeuron( myName ) != null)
            ||  (NeuronNetwork.findSynapse( myName ) != null)) {
                Errors.warning(
                    "Synapse " + myName +
                    " -- duplicate declaration"
                );
                sc.nextLine();
                throw new IllegalNameEx();
            }
        }

        // the following is needed because of limits of java lambda
        final String finalName = myName;

        String sourceName = ScanSupport.nextName(
            sc,
            () -> (
                "Synapse " +
                (finalName != null ? finalName : "-") +
                " ???"
            )
        );
        String dstName = ScanSupport.nextName(
            sc,
            () -> (
                "Synapse " +
                (finalName != null ? finalName : "-") +
                " " +
                (sourceName != null ? sourceName : "---") +
                " ???"
            )
        );
        mySource = NeuronNetwork.findNeuron( sourceName );
        myPrimaryDest = NeuronNetwork.findNeuron( dstName );
        if (myPrimaryDest == null) {
            mySecondaryDest = NeuronNetwork.findSynapse( dstName );
            mySynapse = new SecondarySynapse( mySecondaryDest );
        } else {
            mySynapse = new PrimarySynapse( myPrimaryDest );
        }

        // the following is needed because of limits of java lambda
        final Synapse finalSynapse = mySynapse;

        finalSynapse.name = finalName;
        finalSynapse.source = mySource;
    
        finalSynapse.delay = ScanSupport.nextFloat(
            sc,
            () -> finalSynapse.toString()
        );
        finalSynapse.strength = ScanSupport.nextFloat(
            sc,
            () -> finalSynapse.toString()
        );
        ScanSupport.lineEnd(
            sc,
            () -> finalSynapse.toString()
        );

        // check correctness of fields
        if ((sourceName != null) && (mySource == null)) {
            Errors.warning(
                finalSynapse.toString() +
                " -- no such source"
            );
        }
        if ( (dstName != null)
        &&   (myPrimaryDest == null)
        &&   (mySecondaryDest == null) ) {
            Errors.warning(
                finalSynapse.toString() +
                " -- no such destination"
            );
        }
        if (finalSynapse.delay < 0.0f) {
            Errors.warning(
                finalSynapse.toString() +
                " -- illegal negative delay"
            );
            finalSynapse.delay = 99.99f;
        }
        if (finalSynapse != null && finalSynapse.source != null) {
            finalSynapse.source.synapses.add(finalSynapse);
        }
        return finalSynapse;
    }

    // simulation methods
    abstract void fire(float time);

    // other methods
    public abstract String toString();
}
