//--------------------------------------------------------------------
// Assignment 2
// Part: 2
// Written by: Asif Khan 40211000 , Ayesha Mahmood 40189093
//--------------------------------------------------------------------

/**
 * Custom exception class for handling the situation when there is a bad record in a Records.
 */
public class BadRecordException extends Exception{
	public BadRecordException(String message) {
        super(message);
    }
}
