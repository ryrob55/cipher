import java.io.*;
import java.math.*;

/**
 * CipherController - this class encrypts and decrypts the specified file 
 * entered in by the user. It also checks to make sure that the file is valid
 * and that it exists. Before it does any of this though, it makes sure that 
 * the parameters entered into args[0] and args[1] are valid and can ensure that
 * the gcd of a and 26 equals 1 using the Euclidean Algorithm.
 * 
 * 
 * @author Ryan Robertson
 * @version October 10, 2012
 * 
 * Acknowledgments: I acknowledge that I have neither given nor received
 * 					assistance for this assignment except as noted below:
 *
 *                  TA Wes
 */

public class CipherController 
{
    CipherIO io;
    BufferedReader reader; 

    private String input;
    private char[] letter = {'a','b', 'c','d','e','f','g','h','i','j','k',
            'l','m','n','o','p','q','r','s','t','u','v', 
            'w','x','y','z'};

    /**
     * constructor
     * 
     * @throws IOException
     */
    public CipherController() throws IOException
    {
        io = new CipherIO();

    } // constructor

/***************************** Public Methods ********************************/	

    /**
     * showOptions - displays the menu to the user as long as the gcd of a and
     * 26 = 1. 
     * 
     * @param a
     * @param b
     * @throws NumberFormatException
     * @throws IOException
     */
    public void showOptions(int a, int b) throws NumberFormatException, IOException
    {
        // tests that the gcd of a and 26 are equal to 1
        if(getGCD(a, 26) != 1)
        {
            io.display("Invalid Value of a, gcd does not equal 1. Goodbye!\n");
            System.exit(1);

        } // end if 

        int choice; // user input of the menu
        
        // displays menu until the user enters a string or invalid character
        try
        {
            // display menu and allow user to loop through until they quit
            do
            {   
                io.display("\nWelcome to the Lucky Cipher Program, ");
                io.display("It's Magically Delicious!\n\n");
                io.display("Choose from the following options:\n");
                io.display("1) Encrypt a file\n");
                io.display("2) Decrypt a file\n");
                io.display("3) Exit\n");
                io.display("> ");
                choice = Integer.parseInt(io.get());

                if( choice == 1 )
                    encryptFile(a, b);
                else if( choice == 2 )
                    decryptFile(a, b);
                else if( choice >= 3 || choice <= 1  )
                {
                    io.display("\nGoodbye!\n");
                    choice = 0;

                } // end else if

            }while(choice >= 1 && choice <= 3);
            
        } // end try
        
        catch(Exception e)
        {
            io.display("Invalid Menu Choice. Goodbye!\n");
            System.exit(1);
            
        } // end catch
        
    } // method showOptions


/**************************** Private Methods ********************************/

    /**
     * decryptFile - decrypts the cipher-text file
     * 
     * @return
     * @throws IOException
     */
    private void decryptFile(int a, int b) throws IOException
    {   
        // since Big Integer requires a string as its parameter the int value a
        // must be converted to a string here
        String value = Integer.toString( a );
        String file;

        // the BigInteger Class helps with the inverse of a here instead of 
        // calculating it out manually. Found how to do this through google
        BigInteger num = new BigInteger(value);
        BigInteger modValue = new BigInteger("26");

        // user inputs the file to decrypt 
        io.display("Enter in the cypher-text file to decrypt: ");
        input = io.get();

        try
        {
            reader = new BufferedReader(new FileReader(input));
            
        } // end try
        
        catch(Exception e)
        {
            io.display("File does not exist or not found. Exiting...\n" );
            System.exit(1);
            
        } // end catch

        String myFile = reader.readLine();

        if(isValid(myFile))
        {
            file = myFile.toLowerCase();
            int length = file.length();
            String plaintext = new String();
            char current;

            for(int i = 0; i < length; i++)
            {
                current = file.charAt(i);

                if (current == ' ')
                    plaintext += ' ';
                else
                {    
                    int val = num.modInverse( modValue ).intValue() * 
                            (getIndex(current) - b );
                    int r = val % 26;
                    if (r < 0)
                    {
                        r += 26;

                    } // end if

                    plaintext+=letter[r]; 

                } // end else

            } // end for

            io.display("\nOriginal Message: " + myFile);
            io.display("\nPlaintext Message: " + plaintext.toUpperCase() + "\n");

        } // end if 

        else
        {
            io.display("\nOriginal Message: " + myFile);
            io.display("\nError! Not all characters were letters only! " +
                    "Exiting...\n");
            System.exit( 1 );

        } // end else that prints error message

        // closes the BufferedReader
        reader.close();

    } // method decryptFile

    /**
     * encryptFile - encrypts the plaintext file using affine cipher
     * 
     * @throws IOException 
     */
    private void encryptFile(int a, int b) throws IOException
    {
        String file;
        io.display("Enter in the plaintext file to encrypt: ");
        input = io.get();

        try
        {
            reader = new BufferedReader(new FileReader(input));
            
        } // end try
        
        catch(Exception e)
        {
            io.display("File does not exist or not found. Exiting...\n");
            System.exit(1);
            
        } // end catch
        
        String myFile = reader.readLine();

        if(isValid(myFile))
        {
            file = myFile.toLowerCase();
            int length = file.length();
            String ciphertext = new String();
            char current;

            for(int i = 0; i < length; i++)
            {
                current = file.charAt(i);

                if(current == ' ')
                    ciphertext += ' ';
                else
                    ciphertext += letter[ (a * getIndex(current) + b) % 26 ];

            } // end for

            // displays the original message
            io.display("\nOriginal Message: " + myFile);

            // displays the encrypted message in all upper case letters
            io.display("\nEncrypted Message: " + ciphertext.toUpperCase() + 
                    "\n");

        } // end if to check if file contains valid chars

        else
        {
            io.display("\nOriginal Message: " + myFile);
            io.display("\nError! Not all characters were letters only! " +
                    "Exiting...\n");
            System.exit( 1 );

        } // end else that prints error message

        // closes BufferedReader
        reader.close(); // closes FileReader

    } // method encryptFile

    /**
     * getIndex - returns the index in the array of letters
     * 
     * @param pos
     * @return
     */
    private int getIndex(char pos)
    {
        int index = 0;

        for(int i = 0; i < letter.length; i++)
        {
            if (letter[i] == pos)
            {
                index = i;

            } // end if 

        } // end for

        return index; 

    } // method getIndex

    /**
     * getGCD - checks the gcd of int a using Euclidean Algorithm
     * 
     * @param a
     * @param x
     * @return
     */
    private int getGCD(int a, int x)
    {
        x = 26;

        if (a == 0)
            return x;

        while(x != 0)
        {
            if( a > x )
                a = a - x;
            else
                x = x - a;

        } // end while

        return a;

    } // method getGCD

    /**
     * isValid - checks the file that the user specifies for invalid chars
     * 
     * @param myFile
     * @return
     */
    private boolean isValid(String myFile)
    {
        boolean isValid = true;

        String[] invalid = {"1","2","3","4","5","6","7","8","9","0","!","@","#",
                "$","%","^","&","*","(",")","`","~","-","_","=","+",
                "[","]","{","}","|",";",":","<",">","?","/" };

        for(int i = 0; i < invalid.length; i++)
            if(myFile.contains(invalid[i]))
                return false;


        return isValid;

    } // method isValid

} // class CipherController