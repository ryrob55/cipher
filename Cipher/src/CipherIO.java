import java.io.*;

/**
 * CipherIO - handles user input
 * 
 * 
 * @author Ryan Robertson
 * @version October 10, 2012
 * 
 * Acknowledgments: I acknowledge that I have neither given nor received
 * 					assistance for this assignment except as noted below:
 *
 */

public class CipherIO 
{
	private BufferedReader reader;
	
	/**
	 * constructor
	 * @throws IOException 
	 */
	public CipherIO() throws IOException
	{
		
		reader = new BufferedReader( new InputStreamReader(System.in ) );

	} // constructor 
	
	/**
	 * display - Overloaded method - displays String to user
	 *
	 * @param the String to display
	 */
	public void display( String s )
	{
	    
		System.out.print( s );

	} // method display
	
	/**
	 * get - Gets input from the user
	 *
	 * @return the string entered by the user
	 */
	public String get() throws IOException
	{
	    
		return reader.readLine();

	} // method get
	
} // class CipherIO