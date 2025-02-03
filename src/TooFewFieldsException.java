//--------------------------------------------------------------------
// Assignment 2
// Part: 1
// Written by: Asif Khan 40211000 , Ayesha Mahmood 40189093
//--------------------------------------------------------------------

/**
 * Custom exception class for handling the situation when there are too few fields in a record.
 */
public class TooFewFieldsException extends Exception{
	public TooFewFieldsException(String message) {
		super(message);
	}
}
