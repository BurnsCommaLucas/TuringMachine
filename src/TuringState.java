import java.util.Scanner;

public class TuringState extends Object{
	public String initialIn;
	
	public String input;
	public String pointer;
	public int index;
	
	public TuringState() {
		this.initialIn = "";
		
		this.input = "";
		this.pointer = "";
		this.index = -1;
	}
	
	public TuringState(String input, String pointer, int index) {
		this.initialIn = input;
		this.index = index;

        // Give the user the benefit of the doubt
        boolean cheater = false;

        // Iterate through the input string and add spaces to the pointer to match length
        for (int i = 0; i < input.length(); i++)
        {
            // If the input string has # characters (marked by TM), call out the user for cheating.
            if (input.charAt(i) == '#' && !cheater)
            {
            	// For shame
                System.out.println("I see you trying to be clever. Throwing in your own pre-marked symbols." + Main.RET + 
                		"Let's see what happens when we run your string anyway. Cheater.");
                cheater = true;
            }
            pointer += ' ';
        }
        this.pointer = pointer;

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
        
        this.input = input.substring(counter).trim();
	}
	
	public void left() {
		this.LR("left");
	}
	
	public void right() {
		this.LR("right");
	}

    /**
     * LR - Method to move pointer carrot one space to the left or right.
     * 
     * @param LR Numerical representation of left or right execution (0 = Left, 1 = Right).
     */
    private void LR(String dir)
    {
        System.out.println("Moved " + dir + " from character " + this.input.charAt(this.index) + '.');
        
        // Move index one left or right
        this.index += dir.equals("right") ? 1 : -1;
        
        // If the carrot was at the far right, add an underscore to input
        int pIdx = this.pointer.indexOf('^');
        if (pIdx == this.input.length() - 1 && dir.equals("right")) {
        	this.input += '_';
        	this.index = this.input.length() - 1;
        }
        else if (pIdx == 0 && dir.equals("left")) {
        	this.input = '_' + this.input;
        	this.pointer += ' ';
        	this.index = 0;
        }
        
        // Construct the new pointer string from the old one, modified
        String left = this.pointer.substring(0, pIdx);
        String right = this.pointer.substring(pIdx + 1);
        this.pointer = left + (dir.equals("right") ? " ^" : "^ ") + right;
    }
    
    /**
     * markChar - Marks the character at the current index with a #
     */
    public void markChar() {
        this.input = this.input.substring(0, this.index) + '#' + this.input.substring(this.index + 1);
    }

    /**
     * printState - Prints the current state of TM execution with a spacer line
     *                  for clarity. Waits for user input to continue execution.
     */
    public void printState()
    {
    	// Print Input string, pointer, and spacer
        System.out.println(this.input + Main.RET + this.pointer + Main.RET + Main.SPACER);
        
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
        		isDone = true; // This seems wrong but it works as much as I want it to
        	}
        }
    }
}