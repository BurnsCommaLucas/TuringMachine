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
 * @version 1 Dec 2015 Original functionality.
 */
public class Main
{
	// Spacer to be printed in between each printout of the state
    final static String SPACER = "------------------------------------------";
    
    // Static variable to hold the original input without having to pass it through a bunch
    public static String initialIn = "";

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

        // Save original input for later
        initialIn = input;

        // Initialize the TM pointer
        String pointer = "^";
        int index = 0;

        // Give the user the benefit of the doubt
        boolean cheater = false;

        // Iterate through the input string and add spaces to the pointer to match length
        for (int i = 0; i < input.length(); i++)
        {
            // If the input string has # characters (marked by TM), call out the user for cheating.
            if (input.charAt(i) == '#' && !cheater)
            {
                System.out.println("I see you trying to be clever. Throwing in your own pre-marked symbols. No cheating.");
                System.out.println("Let's see what happens when we run your string anyway. Cheater.");
                cheater = true;
            }
            pointer += ' ';
        }

        // Trim leading underscores
        int counter = 0;
        for (int i = 0; i < input.length(); i++)
        {
            if (input.charAt(i) == '_')
            {
                counter++;
            }
            else
            {
                break;
            }
        }

        input = input.substring(counter);

        input = input.trim();

        // Intro
        System.out.println(SPACER);
        System.out.println("Initial state:");
        System.out.println("Press [ENTER] to advance simulation.");
        printState(input, pointer);

        // Begin simulation
        q0(input, pointer, index);
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
     *
     * @param input String currently on the TM tape.
     * @param pointer String showing where the TM execution head is.
     * @param index Numerical location of the TM execution head.
     */
    public static void q0(String input, String pointer, int index)
    {
        // Self loop conditions
        if(input.charAt(index) == '#')
        {
            // Move pointer right + retrieve outputs
            String[] output = right(input, pointer, index);
            input = output[0];
            pointer = output[1];
            index = output[2].length();
            
            // Print state of Turing machine with helper
            printState(input, pointer);

            // Loop back to state q0
            q0(input, pointer, index);
        }
        // Next state condition
        else if (input.charAt(index) == 'a')
        {
            System.out.println("Marked character " + input.charAt(index) + " with #.");

            // Mark character with #
            input = input.substring(0, index) + '#' + input.substring(index + 1);

            printState(input, pointer);

            // Move pointer right + retrieve outputs
            String[] output = right(input, pointer, index);
            input = output[0];
            pointer = output[1];
            index = output[2].length();
            printState(input, pointer);

            q1(input, pointer, index);
        }
        else if (input.charAt(index) == '_')
        {
            String[] output = right(input, pointer, index);
            input = output[0];
            pointer = output[1];
            index = output[2].length();

            // Print state of turing machine with helper
            printState(input, pointer);

            // Move to qAccept
            qAccept();
        }
        else
        {
            qReject(input, index);
        }
    }

    /**
     * q1 - Second state of TM execution.
     *
     * Transitions: ([a, #] -> [a, #], Right):  q1
     *              (b -> #, Right):            q2
     *              (other):                    qReject
     *
     * @param input String currently on the TM tape.
     * @param pointer String showing where the TM execution head is.
     * @param index Numerical location of the TM execution head.
     */
    public static void q1(String input, String pointer, int index)
    {
        // Self loop conditions
        if (input.charAt(index) == 'a' || input.charAt(index) == '#')
        {
            // Move pointer right + retrieve outputs
            String[] output = right(input, pointer, index);
            input = output[0];
            pointer = output[1];
            index = output[2].length();
            printState(input, pointer);

            q1(input, pointer, index);
        }
        // Next state condition
        else if (input.charAt(index) == 'b')
        {
            System.out.println("Marked character " + input.charAt(index) + " with #.");

            // Mark character with #
            input = input.substring(0, index) + '#' + input.substring(index + 1);

            printState(input, pointer);

            // Move pointer right + retrieve outputs
            String[] output = right(input, pointer, index);
            input = output[0];
            pointer = output[1];
            index = output[2].length();
            printState(input, pointer);

            q2(input, pointer, index);
        }
        else
        {
            qReject(input, index);
        }
    }

    /**
     * q2 - Third state of TM execution.
     *
     * Transitions: ([b, #] -> [b, #], Right):  q2
     *              (c -> #, Right):            q3
     *              (other):                    qReject
     *
     * @param input String currently on the TM tape.
     * @param pointer String showing where the TM execution head is.
     * @param index Numerical location of the TM execution head.
     */
    public static void q2(String input, String pointer, int index)
    {
        // Self loop conditions
        if (input.charAt(index) == 'b' || input.charAt(index) == '#')
        {
            // Move pointer right + retrieve outputs
            String[] output = right(input, pointer, index);
            input = output[0];
            pointer = output[1];
            index = output[2].length();
            printState(input, pointer);

            q2(input, pointer, index);
        }
        // Next state condition
        else if (input.charAt(index) == 'c')
        {
            System.out.println("Marked character " + input.charAt(index) + " with #.");

            // Mark character with #
            input = input.substring(0, index) + '#' + input.substring(index + 1);

            printState(input, pointer);

            // Move pointer right + retrieve outputs
            String[] output = right(input, pointer, index);
            input = output[0];
            pointer = output[1];
            index = output[2].length();
            printState(input, pointer);

            q3(input, pointer, index);
        }
        else
        {
            qReject(input, index);
        }
    }

    /**
     * q3 - Fourth state of TM execution.
     *
     * Transitions: ([c, _] -> [c, _], Left):   q4
     *              (other):                    qReject
     *
     * @param input String currently on the TM tape.
     * @param pointer String showing where the TM execution head is.
     * @param index Numerical location of the TM execution head.
     */
    public static void q3(String input, String pointer, int index)
    {
        // Reset condition
        if (input.charAt(index) == 'c' || input.charAt(index) == '_')
        {
            // Move pointer left + retrieve outputs
            String[] output = left(input, pointer, index);
            input = output[0];
            pointer = output[1];
            index = output[2].length();
            printState(input, pointer);

            q4(input, pointer, index);
        }
        else
        {
            qReject(input, index);
        }
    }

    /**
     * q4 - Fifth state of TM execution.
     *
     * Transitions: ([b, #] -> [b, #], Left):   q4
     *              (a -> a, Left):             q5
     *              (_ -> _, Right):            q0
     *              (other):                    qReject
     *
     * @param input String currently on the TM tape.
     * @param pointer String showing where the TM execution head is.
     * @param index Numerical location of the TM execution head.
     */
    public static void q4(String input, String pointer, int index)
    {
        // Self loop conditions
        if (input.charAt(index) == 'b' || input.charAt(index) == '#')
        {
            // Move pointer left + retrieve outputs
            String[] output = left(input, pointer, index);
            input = output[0];
            pointer = output[1];
            index = output[2].length();
            printState(input, pointer);

            q4(input, pointer, index);
        }
        // Return to first state condition
        else if (input.charAt(index) == 'a')
        {
            // Move pointer left + retrieve outputs
            String[] output = left(input, pointer, index);
            input = output[0];
            pointer = output[1];
            index = output[2].length();
            printState(input, pointer);

            q5(input, pointer, index);
        }
        else if (input.charAt(index) == '_')
        {
            // Move pointer left + retrieve outputs
            String[] output = right(input, pointer, index);
            input = output[0];
            pointer = output[1];
            index = output[2].length();
            printState(input, pointer);

            q0(input, pointer, index);
        }
        else
        {
            qReject(input, index);
        }
    }

    /**
     * q5 - Sixth state of TM execution.
     *
     * Transitions: (a -> a, Left):     q5
     *              (# -> #, Right):    q0
     *              (other):            qReject
     *
     * @param input String currently on the TM tape.
     * @param pointer String showing where the TM execution head is.
     * @param index Numerical location of the TM execution head.
     */
    public static void q5(String input, String pointer, int index)
    {
        // Self loop conditions
        if (input.charAt(index) == 'a')
        {
            // Move pointer right + retrieve outputs
            String[] output = left(input, pointer, index);
            input = output[0];
            pointer = output[1];
            index = output[2].length();
            printState(input, pointer);

            q5(input, pointer, index);
        }
        // Next state condition
        else if (input.charAt(index) == '#')
        {
            // Move pointer right + retrieve outputs
            String[] output = right(input, pointer, index);
            input = output[0];
            pointer = output[1];
            index = output[2].length();
            printState(input, pointer);

            q0(input, pointer, index);
        }
        else
        {
            qReject(input, index);
        }
    }

    /**
     * qAccept - Accept state of TM execution. This state halts execution and
     *              prints a message to indicate that execution was successful.
     */
    public static void qAccept()
    {
        System.out.println("Result of execution: Accept");
        System.out.println("Your original input: " + initialIn);
    }

    /**
     * qReject - Reject state of TM execution. This state halts execution and
     *              prints a message indicating what character caused the halt.
     *
     * @param input String currently on the TM tape.
     * @param index Location of character that caused halt.
     */
    public static void qReject(String input, int index)
    {
        System.out.println("Result of execution: Reject");
        System.out.println("Your original input: " + initialIn);
        System.out.println("Halt on character: " + input.charAt(index));
    }

    /**
     * printState - Prints the current state of TM execution with a spacer line
     *                  for clarity. Waits for user input to continue execution.
     *
     * @param input String currently on the TM tape.
     * @param pointer Pointer string with TM execution head.
     */
    public static void printState(String input, String pointer)
    {
    	// Print Input string, pointer, and spacer
        System.out.println(input);
        System.out.println(pointer);
        System.out.println(SPACER);
        
        // Var to hold execution until user prompts for next state
        boolean isDone = false;
        Scanner scan = new Scanner(System.in);

        while (!isDone)
        {
        	String done = scan.nextLine();

        	if (done.equals(""))
        	{
        		isDone = true;
        	}
        	else
        	{
        		isDone = true;
        	}
        }
    }

    /**
     * left - Method to move pointer carrot one space to the left. If necessary,
     *          adds an underscore character to the left of the input string, and a space
     *          to the right of the pointer string.
     *
     * @param input String currently on the TM tape.
     * @param pointer Pointer string with TM execution head.
     * @param index Numerical location of TM execution head.
     * @return Array of three strings: input string, pointer string, index (in spaces).
     */
    public static String[] left(String input, String pointer, int index)
    {
        System.out.println("Moved left from character " + input.charAt(index) + '.');
        
        // Move index one left
        index -= 1;

        for (int i = 0; i < input.length(); i++)
        {
            if (pointer.charAt(i) == '^')
            {
                // If the carrot was at the far left, add an underscore to the
                // left of input and a space to the right of pointer
                if (i == 0)
                {
                    input = '_' + input;
                    pointer += ' ';

                    // Reset index to 0 so we don't get out of bounds exception
                    index = 0;

                    break;
                }

                // Construct the new pointer string from the old one, modified
                String tempLeft = pointer.substring(0, i-1);
                String tempLeft1 = pointer.substring(i + 1);
                pointer = tempLeft + "^ " + tempLeft1;

                break;
            }
        }

        // Output array (I really didn't want to make arraylists so the index is expressed as 
        // a number of spaces equal to the index
        String[] output = new String[3];
        output[0] = input;
        output[1] = pointer;
        String outTemp = "";
        for (int i = 0; i < index; i++)
        {
            outTemp += ' ';
        }
        output[2] = outTemp;

        return output;
    }

    /**
     * right - Method to move pointer carrot one space to the right. If necessary,
     *          adds an underscore character to the right of the input string, and a space
     *          to the left of the pointer string.
     *
     * @param input String currently on the TM tape.
     * @param pointer Pointer string with TM execution head.
     * @param index Numerical location of TM execution head.
     * @return Array of three strings: input string, pointer string, index (in spaces).
     */
    public static String[] right(String input, String pointer, int index)
    {
        System.out.println("Moved right from character " + input.charAt(index) + '.');
        
        // Move index one right
        index += 1;
        
        // Find the pointer carrot in the pointer string and move it right
        for (int i = 0; i < input.length(); i++)
        {
            if (pointer.charAt(i) == '^')
            {
                // If the carrot was at the far right, add an underscore to input
                if (i == input.length() - 1)
                {
                    input += '_';
                }

                // Construct the new pointer string from the old one, modified
                String tempRight = pointer.substring(0, i);
                String tempRight1 = pointer.substring(i+1, input.length());
                pointer = tempRight + " ^" + tempRight1;

                break;
            }
        }

        String[] output = new String[3];
        output[0] = input;
        output[1] = pointer;
        String outTemp = "";
        for (int i = 0; i < index; i++)
        {
            outTemp += ' ';
        }
        output[2] = outTemp;

        return output;
    }
}
