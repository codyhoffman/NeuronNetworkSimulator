// Simulator.java

import java.util.PriorityQueue;

/** Framework for discrete event simulation.
 *  @author: Douglas W. Jones
 *  @author: Cody J. Hoffman
 *  @version: April 21, 2016
 * 
 *  This code is based on the April 20, 2016 lecture notes.
 */
class Simulator {

    /** Users create new subclasses of event for each thing that happens
     */
    public static abstract class Event {

        /** The time of this event, read only within subclasses
         */
        protected final float time; // the time of this event

        /** The only way to create a new event
         *  @param t, the time at which the new event will be triggered
         *  This may only be called to initialize a subclass of Event,
         *  typically, it will be an anonymous subclass.
         */
        public Event( float t ) {
            time = t;               // initializer (the only way to set time)
        }

	/** Each subclass must give a specific trigger method
	 *  This method will be called when the event is triggered;
	 *  the code of Trigger may refer to {@code time}, the event time.
	 */
        abstract void trigger();    // what to do at that time
    }

    private static PriorityQueue <Event> eventSet
    = new PriorityQueue <Event> (
        (Event e1, Event e2) -> Float.compare( e1.time, e2.time )
    );

    /** Called to trigger the event at the given time
     *  @param e, the event to be triggered, with its time.
     */
    public static void schedule( Event e ) {
        eventSet.add( e );
    }

    /** Run the discrete event simulation
     *  Prior to calling {@code run}, the user should {@code schedule}
     *  some initial {@code Event}s.  The simulation will run until either
     *  no events remain or until some event terminates the program.
     */
    static void run() {
        while (!eventSet.isEmpty()) {
            Event e = eventSet.remove();
            e.trigger( );
        }
    }
}
