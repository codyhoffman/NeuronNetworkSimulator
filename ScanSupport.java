// ScanSupport.java

import java.util.regex.Pattern;
import java.util.Scanner;

/** Input scanning support methods
 *  This bundle of static methods provides services that read input using
 *  a Java {@link Scanner}, and if the input does not meet the stated
 *  requirement, report that using a call to the {@code warning} method
 *  in {@link Errors}.
 *  @author Douglas W. Jones 
 *  @author: Andy W.M. Arthur
 *  @version 4/11/2016
 *  @see java.util.Scanner
 *  @see Errors
 *
 *  This code is extracted from the April 6, 2016 version of NeuronNetwork.java,
 *  which, in turn was based on the non-alternative solution to MP3,
 *  as well as a simulation framework from the March 11 lecture notes.
 *  This code is further modified to meet the requirements of MP5.
 */
public class ScanSupport {
    /** Interface allowing error messages passed as lambda expressions
     *  The {@code ErrorMessage} interface is used implicitly in the
     *  parameter lists to methods of class {@code ScanSupport},
     *  but is rarely explicitly mentioned.
     *  The implicit mechanism for passing error message strings creates
     *  a subclass of {@code ErrorMessage} for each message.
     */
    public interface ErrorMessage {
        /** Return the part of the error message text giving the context
         *  each call to a {@code ScanSupport} method will typically
         *  provide a different implementation of this function.
         *  @return string
         */
        abstract String myString();
    }

    /** Force there to be a line end here, complain if not
     *  @param sc  the {@link Scanner} from which the input text is
     *             being scanned
     *  @param message  the {@link ErrorMessage} to use if the input text
     *             is not currently positioned at a line end.
     *  @return void
     *  Typically, the {@code message} parameter is given as a lambda
     *  expression, for example: {@code LineEnd(sc,()->"Line:"+n);}
     *  The use of a lambda expression here means that the computations
     *  (for example, string concatenations) are not done unless there
     *  is not a line end where one was expected.
     */
    public static void lineEnd( Scanner sc, ErrorMessage message ) {
        String skip = sc.nextLine();
        if (!"".equals( skip )) {
            // Bug:  do we want to allow comments here
            Errors.warning(
                message.myString() +
                " -- expected a newline"
            );
        }
        // Bug:  what if sc.nextLine() was illegal (illegal state)
    }

    /* really private to nextName */
    private static final Pattern name = Pattern.compile( "[A-Za-z]\\w*" );

    /** Get the next name, or complain if there isn't one
     *  @param sc  the {@link Scanner} from which the input text is
     *             being scanned
     *  @param message  the {@link ErrorMessage} to use if the input text
     *             is not currently positioned at a line end.
     *  @return String
     *  Typically, the {@code message} parameter is given as a lambda
     *  expression, for example: {@code NextName(sc,()->"Line:"+n);}
     *  See {@link lineEnd} for the reason a lambda expression is used here.
     *  Names are defined as a letter followed by any number of letters
     *  or digits using Java's {@link Pattern} recognition mechanisms.
     *  @see java.util.regex.Pattern
     */
    public static String nextName( Scanner sc, ErrorMessage message ) {
        if (sc.hasNext( name )) {
            return sc.next( name );
        } else {
            Errors.warning(
                message.myString() +
                " -- expected a name"
            );
            return null;
        }
    }

    /** Get the next int, or complain if there isn't one
     *  @param sc  the {@link Scanner} from which the input text is
     *             being scanned
     *  @param message  the {@link ErrorMessage} to use if the input text
     *             is not currently positioned at a line end.
     *  @return int
     *  Typically, the {@code message} parameter is given as a lambda
     *  expression, for example: {@code NextName(sc,()->"Line:"+n);}
     *  See {@link lineEnd} for the reason a lambda expression is used here.
     */
    public static int nextInt( Scanner sc, ErrorMessage message ) {
        if (sc.hasNextInt()) {
            return sc.nextInt();
        } else {
            Errors.warning(
                message.myString() +
                " -- expected an integer"
            );
            return 99;
        }
    }

    /** Get the next float, or complain if there isn't one
     *  @param sc  the {@link Scanner} from which the input text is
     *             being scanned
     *  @param message  the {@link ErrorMessage} to use if the input text
     *             is not currently positioned at a line end.
     *  @return float
     *  Typically, the {@code message} parameter is given as a lambda
     *  expression, for example: {@code NextName(sc,()->"Line:"+n);}
     *  See {@link lineEnd} for the reason a lambda expression is used here.
     */
    public static float nextFloat( Scanner sc, ErrorMessage message ) {
        if (sc.hasNextFloat()) {
            return sc.nextFloat();
        } else {
            Errors.warning(
                message.myString() +
                " -- expected a number"
            );
            return 99.99f;
        }
    }
}
