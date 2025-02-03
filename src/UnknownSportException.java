//--------------------------------------------------------------------
// Assignment 2
// Part: 1
// Written by: Asif Khan 40211000 , Ayesha Mahmood 40189093
//-------------------------------------------------------------------
/**
 * Custom exception class for handling the situation when sport type is unknown in a record.
 */
public class UnknownSportException extends Exception{
	public UnknownSportException(String message) {
		super(message);
	}
}
