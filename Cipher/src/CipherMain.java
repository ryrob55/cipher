import java.io.IOException;

/**
 * CipherMain -
 * 
 * 
 * @author Ryan Robertson
 * @version October 10, 2012
 * 
 * Acknowledgments: I acknowledge that I have neither given nor received
 * 					assistance for this assignment except as noted below:
 *
 */
public class CipherMain 
{
	/**
	 * @param args
	 * @throws IOException 
	 * @throws NumberFormatException 
	 */
	public static void main(String[] args) throws IOException 
	{
		CipherController control = new CipherController();
		
		// input at args 0 and 1 sets params for a and b
		int a = Integer.parseInt( args[0] );
		int b = Integer.parseInt( args[1] );
		
		control.showOptions(a, b);
		
	} // method main
	
} // class CipherMain
