#README:
###Using Main.java in Turing machine project:

Open the whole project in IntelliJ (although I think Main.java can be opened by itself
in Eclipse). If no SDK is defined, use 1.6 or higher. I have tested this with 1.6 and 1.8,
I have no idea if any older versions will work, although I assume they will as the only
import is Scanners for user input.

Main.java is located in /src/Turing/

###TIPS:
~ Leading underscores (which represent spaces) and any leading or trailing whitespace in
input strings are trimmed to ensure that the TM begins at the leftmost character of the
actual input.

~ Pound characters represent characters that have been marked in execution, so while they
are valid input, they will make the program mark you as a cheater for trying to trick it.

~ The TM assumes any characters more than 2 spaces in either direction from the first
string it encounters are not part of the same input. (For example aabbcc__xx will accept
as the first string the TM encounters will be aabbcc, and xx__aabbcc will reject as xx
will be the first string the TM encounters.)

~ To test inputs without having to press enter every time to advance the simulation, comment
out lines 432 - 447.

- Lucas Burns
7 Dec 2015