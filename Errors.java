// Errors.java
/** Error reporting methods.
 *  @author: Douglas W. Jones
 *  @author: Andy W.M. Arthur
 *  @version: April 9, 2016
 * 
 *  This code is extracted from the April 6, 2016 version of NeuronNetwork.java,
 *  which, in turn was based on the non-alternative solution to MP3,
 *  as well as a simulation framework from the March 11 lecture notes.
 */

class Errors {
    public static int errCount = 0;
    public static void fatal( String message ) {
        errCount++;
        System.err.println( "Fatal error: " + message );
        System.exit( 1 );
    }
    public static void warning( String message ) {
        errCount++;
        System.err.println( "Error: " + message );
    }
}
