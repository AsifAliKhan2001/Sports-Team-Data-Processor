//--------------------------------------------------------------------
// Assignment 2
// Part: 1
// Written by: Asif Khan 40211000 , Ayesha Mahmood 40189093
//-------------------------------------------------------------------

/**
 * Custom exception class for handling the situation when there are too many fields in a record.
 */
public class TooManyFieldsException extends Exception{
	public TooManyFieldsException(String message) {
		super(message);
 }
}
