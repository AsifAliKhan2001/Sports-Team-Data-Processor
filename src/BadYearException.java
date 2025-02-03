//--------------------------------------------------------------------
// Assignment 2
// Part: 2
// Written by: Asif Khan 40211000 , Ayesha Mahmood 40189093
//--------------------------------------------------------------------

/**
 * Custom exception class for handling the situation where the year is wrong in a Records.
 */
public class BadYearException extends Exception{
	public BadYearException(String message) {
        super(message);
    }
}
