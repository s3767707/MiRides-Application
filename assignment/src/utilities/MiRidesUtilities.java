package utilities;

import exception.InvalidID;

public class MiRidesUtilities 
{
	private final static int ID_LENGTH = 6;
	
	public static String isRegNoValid(String regNo) throws InvalidID
	{
		int regNoLength = regNo.length();
		if(regNoLength != ID_LENGTH)
		{
			throw new InvalidID( "Error: registration number must be 6 characters");
		}
		boolean letters = regNo.substring(0,3).matches("[a-zA-Z]+");
		if (!letters) {
			throw new InvalidID("Error: The registration number should begin with three alphabetical characters.");
		}
		boolean numbers = regNo.substring(3).matches("[0-9]+");
		if (!numbers) {
			throw new InvalidID( "Error: The registration number should end with three numeric characters.");
		}
		return regNo;
	}

	public static String isPassengerCapacityValid(int passengerCapacity) throws IndexOutOfBoundsException
	{
		if(passengerCapacity > 0 && passengerCapacity < 10)
		{
			throw new IndexOutOfBoundsException("OK");
		}
		else
		{
			throw new IndexOutOfBoundsException( "Error: Passenger capacity must be between 1 and 9.");
		}
	}
	
	public static String isStandardFeeValid(double standardFee)
	{
		if(standardFee >3.0) {
			return "OK" ;
		}
		else
		{
			return "Eror: Standard fee must be more than $3.00.";
		}
	}
	
	public static String isServiceTypeValid(String serviceType)
	{
		if(serviceType.contains("SS") && serviceType.contains("SD")) {
			return "OK" ;
		}
		else
		{
			return "Error: Service type can only be SS or SD." ;
		}
	}
}
