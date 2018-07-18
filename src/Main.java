import java.util.Scanner;

/**
 * Class Main - This program simulates a turing machine that accepts strings
 *                  of the form a^n b^n c^n.
 *
 *                  IMPORTANT: This TM starts at the farthest left character of
 *                  the input and assumes any characters more than 2 spaces in either
 *                  direction from the first string it encounters are not part of
 *                  the same input.
 *
 *                  (For example aabbcc__xx will accept as the first string
 *                  the TM encounters will be aabbcc, and xx__aabbcc will
 *                  reject as xx will be the first string the TM encounters.)
 *
 * @author Lucas Burns
 *
 * @version 2018 July 18 Really just burned the whole thing to the ground to make it feel better
 */
public class Main
{
    final static String SPACER = "==========================================";
    final static String RET = "\n";
    public static TuringState TS;

    /**
     * Main - Gets user input, prepares input and pointer strings for execution.
     *
     * @param args Command line inputs
     */
    public static void main(String[] args)
    {
        // Initial output to inform user
        System.out.println("This program simulates a Turing Machine which accepts strings of the form a^n b^n c^n.");
        System.out.println("NOTE: Underscores represent spaces in execution, and the \"^\" below the tape is the TM head.");
        System.out.print("Please enter an input string: ");

        // Take next input string
        Scanner scan = new Scanner(System.in);

        // Clean up input string
        String input = scan.nextLine().trim().toLowerCase();
        
        TS = new TuringState(input, "^", 0);
        
        String intro = SPACER + RET + "Initial state:" + RET + "Press [ENTER] to advance simulation.";
        
        if (TS.input.length() == 0)
        {
            System.out.println(intro);
            TS.printState();

            // But alas, you entered nothing (or only whitespace)
            // and so nothing happens. Good job. I mean, it *technically* works,
            // but uh... why would you even do that?
            qAccept();
            System.exit(0);
        }
        
        System.out.println(intro);
        TS.printState();

        // Begin simulation
        q0();
    }

    /**
     * q0 - Initial state of TM execution.
     *
     * Transitions: (# -> #, Right):    q0
     *              (a -> #, Right):    q1
     *              (_ -> _, Right):    qAccept
     *              (other):            qReject
     *
     * IMPORTANT: Only state which can transition to qAccept.
     */
    public static void q0()
    {
        // Self loop conditions
    	char c = TS.input.charAt(TS.index);
        if(c == '#')
        {
            // Move pointer right + retrieve outputs
            TS.right();
            TS.printState();

            // Loop back to state q0
            q0();
        }
        // Next state condition
        else if (c == 'a')
        {
            System.out.println("Marked character " + c + " with #.");

            // Mark character with #
            TS.markChar();
            TS.printState();

            // Move pointer right + retrieve outputs
            TS.right();
            TS.printState();

            q1();
        }
        else if (c == '_')
        {
            TS.right();
            TS.printState();

            // Move to qAccept
            qAccept();
        }
        else
        {
            qReject();
        }
    }

    /**
     * q1 - Second state of TM execution.
     *
     * Transitions: ([a, #] -> [a, #], Right):  q1
     *              (b -> #, Right):            q2
     *              (other):                    qReject
     */
    public static void q1()
    {
        // Self loop conditions
    	char c = TS.input.charAt(TS.index);
        if (c == 'a' || c == '#')
        {
            // Move pointer right + retrieve outputs
            TS.right();
            TS.printState();

            q1();
        }
        // Next state condition
        else if (c == 'b')
        {
            System.out.println("Marked character " + c + " with #.");

            // Mark character with #
            TS.markChar();

            TS.printState();

            // Move pointer right + retrieve outputs
            TS.right();
            TS.printState();

            q2();
        }
        else
        {
            qReject();
        }
    }

    /**
     * q2 - Third state of TM execution.
     *
     * Transitions: ([b, #] -> [b, #], Right):  q2
     *              (c -> #, Right):            q3
     *              (other):                    qReject
     */
    public static void q2()
    {
        // Self loop conditions
    	char c = TS.input.charAt(TS.index);
        if (c == 'b' || c == '#')
        {
            // Move pointer right + retrieve outputs
            TS.right();
            TS.printState();

            q2();
        }
        // Next state condition
        else if (c == 'c')
        {
            System.out.println("Marked character " + c + " with #.");

            // Mark character with #
            TS.markChar();

            TS.printState();

            // Move pointer right + retrieve outputs
            TS.right();
            TS.printState();

            q3();
        }
        else
        {
            qReject();
        }
    }

    /**
     * q3 - Fourth state of TM execution.
     *
     * Transitions: ([c, _] -> [c, _], Left):   q4
     *              (other):                    qReject
     */
    public static void q3()
    {
        // Reset condition
    	char c = TS.input.charAt(TS.index);
        if (c == 'c' || c == '_')
        {
            // Move pointer left + retrieve outputs
            TS.left();
            TS.printState();

            q4();
        }
        else
        {
            qReject();
        }
    }

    /**
     * q4 - Fifth state of TM execution.
     *
     * Transitions: ([b, #] -> [b, #], Left):   q4
     *              (a -> a, Left):             q5
     *              (_ -> _, Right):            q0
     *              (other):                    qReject
     */
    public static void q4()
    {
        // Self loop conditions
    	char c = TS.input.charAt(TS.index);
        if (c == 'b' || c == '#')
        {
            // Move pointer left + retrieve outputs
            TS.left();
            TS.printState();

            q4();
        }
        // Return to first state condition
        else if (c == 'a')
        {
            // Move pointer left + retrieve outputs
            TS.left();
            TS.printState();

            q5();
        }
        else if (c == '_')
        {
            // Move pointer left + retrieve outputs
            TS.right();
            TS.printState();

            q0();
        }
        else
        {
            qReject();
        }
    }

    /**
     * q5 - Sixth state of TM execution.
     *
     * Transitions: (a -> a, Left):     q5
     *              (# -> #, Right):    q0
     *              (other):            qReject
     */
    public static void q5()
    {
        // Self loop conditions
    	char c = TS.input.charAt(TS.index);
        if (c == 'a')
        {
            // Move pointer right + retrieve outputs
            TS.left();
            TS.printState();

            q5();
        }
        // Next state condition
        else if (c == '#')
        {
            // Move pointer right + retrieve outputs
            TS.right();
            TS.printState();

            q0();
        }
        else
        {
            qReject();
        }
    }

    /**
     * qAccept - Accept state of TM execution. This state halts execution and
     *              prints a message to indicate that execution was successful.
     */
    public static void qAccept()
    {
        System.out.println("Result of execution: Accept");
        System.out.println("Your original input: " + TS.initialIn);
    }

    /**
     * qReject - Reject state of TM execution. This state halts execution and
     *              prints a message indicating what character caused the halt.
     *
     * @param input String currently on the TM tape.
     * @param TS.index Location of character that caused halt.
     */
    public static void qReject()
    {
    	char c = TS.input.charAt(TS.index);
    	String rejectStr = "Result of execution: Reject" + RET + 
    			"Your original input: " + TS.initialIn + RET + 
    			"Halt on character: " + c;
        System.out.println(rejectStr);
    }
}
