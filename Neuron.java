// Neuron.java

import java.util.LinkedList;
import java.util.Scanner;
import java.lang.Math;

/** Neurons, joined by synapses, are the active components of a neuron network.
 *  @author: Douglas W. Jones
 *  @author: Andy W.M. Arthur
 *  @author: Cody J. Hoffman
 *  @version: April 21, 2016
 *  @see Synapse
 *  @see ScanSupport
 *  @see Errors 
 *  @see Simulator
 * 
 *  This code is a modified version of the code from April 13, distributed
 *  as part of the framework for MP5; the modifications incorporate the
 *  alternative simulation framework discussed on April 20.
 */

class Neuron {
    String name;                // name of this neuron
    private int fireCount = 0;  // number of times this neuron fired

    public static class IllegalNameEx extends Exception {}

    // default values below for errors with incompletely defined neurons
    private float threshold = 99.99f;// voltage at which the neuron fires
    private float voltage = 99.99f; // voltage at the given time
    private float time = 0.0f;  // (see above)

    // the outputs of this neuron
    public LinkedList <Synapse> synapses = new LinkedList<Synapse>();

    // initializer
    public Neuron( Scanner sc ) throws IllegalNameEx {
        // scan and process one neuron
        String name = ScanSupport.nextName(
            sc,
            () -> "Neuron ???"
        );
        if (name == null) { // nextName() already reported syntax error
            sc.nextLine();
            throw new IllegalNameEx ();
        }
        this.name = name;
        if ( (NeuronNetwork.findNeuron( name ) != null)
        ||   (NeuronNetwork.findSynapse( name ) != null) ) {
            Errors.warning(
                "Neuron " + name +
                " -- duplicate declaration"
            );
            sc.nextLine();
            throw new IllegalNameEx();
        }
        threshold = ScanSupport.nextFloat(
            sc,
            () -> Neuron.this.toString()
        );
        voltage = ScanSupport.nextFloat(
            sc,
            () -> Neuron.this.toString()
        );

        //  if voltage exceeds threshold (non-inclusive) the neuron fires
        if (voltage > threshold){
            Simulator.schedule(
                new Simulator.Event( 0.0f ) {
                    void trigger() {
                        Neuron.this.fire( time );
                    }
                }
            );
        }

        ScanSupport.lineEnd(
            sc,
            () -> Neuron.this.toString()
        );
    }

    // simulation methods
    void fire(float time) {
        fireCount = fireCount + 1;
        this.voltage = 0.0f;
        for(Synapse s: synapses) {
            Simulator.schedule(
                new Simulator.Event( time + s.delay ) {
                    void trigger() {
                        s.fire( time );
                    }
                }
            );
        }
    }

    /** This method is called by incoming synapses.
     */
    void kick(float time, float strength) {
        float v1 = voltage;
        // v2 = v1 e^(t1–t2) + s
        voltage = (v1 * (float)Math.exp( this.time - time )) + strength;
        this.time = time;
        if( voltage > threshold) this.fire( time );
    }

    /** Get the current count and reset the count
     */
    int getCount() {
        int r = fireCount;
        fireCount = 0;
        return r;
    }

    // other methods
    public String toString() {
        return (
            "Neuron " +
            name +
            " " +
            threshold +
            " " +
            voltage
        );
    }
}
