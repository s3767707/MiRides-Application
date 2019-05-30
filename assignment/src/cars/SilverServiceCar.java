package cars;

import java.util.Arrays;
import java.util.Scanner;

import exception.InvalidBooking;
import exception.InvalidID;
import utilities.DateTime;
import utilities.DateUtilities;

public class SilverServiceCar extends Car{

	private double bookingFee;
	private String[] refreshments;
    private DateTime dateBooked;
	private final double MINIMUM_BOOKING_FEE = 3.0;
	private double myBookingFee;
	private Booking id;
	private Booking firstName;
	private Booking lastName;
	private Booking numPassengers;
	private Booking kilometersTravelled;
	Scanner in=new Scanner(System.in);

	public SilverServiceCar(String regNo, String make, String model, String driverName, int passengerCapacity,
			double bookingFee, String[] refreshments) throws InvalidID {

		super(regNo, make, model, driverName, passengerCapacity);
		this.refreshments = refreshments;
		setBookingFee(bookingFee);
		refreshments=new String[5];
	
	}
	
	public boolean book(String firstName, String lastName, DateTime required, int numPassengers) {
		return super.book(firstName, lastName, required, numPassengers);
	}
	
	public String completeBooking(String firstName, String lastName, DateTime dateOfBooking, double kilometers) {

		return super.completeBooking(firstName, lastName, dateOfBooking, kilometers);
	}
	
	public String completeBooking(String firstName, String lastName, double kilometers) {
		return super.completeBooking(firstName, lastName, kilometers);
	}
	
	public boolean isCarBookedOnDate(DateTime dateRequired) {
	return super.isCarBookedOnDate(dateRequired);
	}
	
	public String getBookingID(String firstName, String lastName, DateTime dateOfBooking) {
	return super.getBookingID(firstName, lastName, dateOfBooking);
	}

	
	public String getDetails() {
		StringBuilder sb = new StringBuilder();

		super.getDetails();
		
		sb.append(String.format("%-15s %s\n", "Refreshments available"));
		for(int i=0;i<refreshments.length;i++) {
		sb.append(String.format("%-15s %s\n", "Item "+i+":", refreshments[i]));	
		}
		
		if(DateUtilities.dateIsNotInPast(dateBooked)==true) {
			System.out.println("CURRENT BOOKINGS");
			sb.append(String.format("%-15s %s\n", "-------------------"));
			sb.append(String.format("%-16s%-20s %s\n", " ", "id:", id));
			sb.append(String.format("%-16s%-20s $%.2f\n", " ", "Booking Fee:", bookingFee));
			sb.append(String.format("%-16s%-20s %s\n", " ", "Pick Up Date:", dateBooked.getFormattedDate()));
			sb.append(String.format("%-16s%-20s %s\n", " ", "Name:", firstName + " " + lastName));
			sb.append(String.format("%-16s%-20s %s\n", " ", "Passengers:", numPassengers));
			if( kilometersTravelled == null)
			{
			sb.append(String.format("%-16s%-20s %s\n", " ", "Travelled:", "N/A"));
			sb.append(String.format("%-16s%-20s %s\n", " ", "Trip Fee:", "N/A"));
			}
			else
			{
				sb.append(String.format("%-16s%-20s %.2f\n", " ", "Travelled:", kilometersTravelled));
				sb.append(String.format("%-16s%-20s %.2f\n", " ", "Trip Fee:", tripFee));
			}
			sb.append(String.format("%-16s%-20s %s\n", " ", "Car Id:", getRegistrationNumber()));
		}
		else if (DateUtilities.dateIsNotInPast(dateBooked)==false) {
			System.out.println("PAST BOOKINGS");
			sb.append(String.format("%-15s %s\n", "-------------------"));
			sb.append(String.format("%-16s%-20s %s\n", " ", "id:", id));
			sb.append(String.format("%-16s%-20s $%.2f\n", " ", "Booking Fee:", bookingFee));
			sb.append(String.format("%-16s%-20s %s\n", " ", "Pick Up Date:", dateBooked.getFormattedDate()));
			sb.append(String.format("%-16s%-20s %s\n", " ", "Name:", firstName + " " + lastName));
			sb.append(String.format("%-16s%-20s %s\n", " ", "Passengers:", numPassengers));
			sb.append(String.format("%-16s%-20s %s\n", " ", "Travelled:", "N/A"));
			sb.append(String.format("%-16s%-20s %s\n", " ", "Trip Fee:", "N/A"));
			sb.append(String.format("%-16s%-20s %.2f\n", " ", "Travelled:", kilometersTravelled));
			sb.append(String.format("%-16s%-20s %.2f\n", " ", "Trip Fee:", tripFee));
			sb.append(String.format("%-16s%-20s %s\n", " ", "Car Id:", getRegistrationNumber()));
			
		}
		return sb.toString();
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		super.toString();
		sb.append(":"+id);
		sb.append(":" + bookingFee);
		if(dateBooked != null)
		{
			sb.append(":" + dateBooked.getEightDigitDate());
		}
		else
		{
			sb.append(":" + "Invalid");
		}
		sb.append(":" + firstName + " " + lastName);
		sb.append(":" + numPassengers);
		sb.append(":" + kilometersTravelled);
		sb.append(":" + tripFee);
		sb.append(":" + getRegistrationNumber());

		return sb.toString();
	}
	
	public String getRegistrationNumber() {
		return super.getRegistrationNumber();
	}

	public String getDriverName() {
		return super.getDriverName();
	}

	public double getTripFee() {
		return super.getTripFee();
	}

	protected boolean hasBookings(Booking[] bookings) {
		return super.hasBookings(bookings);
	}

	private String completeBooking(int bookingIndex, double kilometers) {
		tripFee = 0;
		Booking booking = currentBookings[bookingIndex];
		currentBookings[bookingIndex] = null;
		bookingSpotAvailable = bookingIndex;

		
		double fee = kilometers * (myBookingFee * 0.4);
		tripFee += fee;
		booking.completeBooking(kilometers, fee, myBookingFee);
		
		for (int i = 0; i < pastBookings.length; i++) {
			if (pastBookings[i] == null) {
				pastBookings[i] = booking;
				break;
			}
		}
		String result = String.format("Thank you for riding with MiRide.\nWe hope you enjoyed your trip.\n$"
				+ "%.2f has been deducted from your account.", tripFee);
		tripFee = 0;
		return result;
	}

	
	protected int getBookingByDate(String firstName, String lastName, DateTime dateOfBooking) {
		return super.getBookingByDate(firstName, lastName, dateOfBooking);
	}

	
	public int getBookingByName(String firstName, String lastName) {
	return super.getBookingByName(firstName, lastName);
	}

	
	protected String getRecordMarker() {
		return super.getRecordMarker();
	}

	
	protected boolean numberOfPassengersIsValid(int numPassengers) {
	return numberOfPassengersIsValid(numPassengers);
	}

	
	protected boolean dateIsValid(DateTime date) {
		return DateUtilities.dateIsNotInPast(date) && DateUtilities.dateIsNotMoreThan3Days(date);
	}

	
	protected boolean bookingAvailable() {
		return super.bookingAvailable();
	}

	
	
	public boolean notCurrentlyBookedOnDate(DateTime date) {
	    return super.notCurrentlyBookedOnDate(date);
	}

	
	public void setRegNo(String regNo) throws InvalidID {
		super.setRegNo(regNo);
	}

	
	public void setPassengerCapacity(int passengerCapacity) {
		super.setPassengerCapacity(passengerCapacity);
	}



	private void setBookingFee(double bookingFee) {

		boolean validBookingFee = bookingFee >= MINIMUM_BOOKING_FEE;
		
		if (validBookingFee) {
			this.bookingFee = bookingFee;
		} else {
			this.bookingFee = -1;
			System.out.println("Booking fee must be at least $3.00");
		}
		
		}
	
	private boolean checkNumberOfRefreshments(int number) {
		if(number<3) {
			System.out.println("Error, number of refreshments must be more than 3");
			return false;
		}else {
	return true;
	
	}
}
	
	public boolean refreshmentsNotLessThanThree(String[]refreshments) {
		int count=0;
		refreshments=new String[count];
		if(count>3) {
			return true;
		}
		return false;
	}
	
	private <T> boolean checkForDuplicates(String[]refreshments)
	{
		for (int i = 0; i < refreshments.length; i++) {
			for (int j = i + 1; j < refreshments.length; j++) {
				if (refreshments[i] != null && refreshments[i].equals(refreshments[j])) {
					return true;
				}
			}
		}

		// no duplicate found
		return false;
	}
	
	public boolean validRefreshmentsNumber(String[]refreshments) throws InvalidID
	{
		boolean validRefreshmentNumber = refreshmentsNotLessThanThree(refreshments);
		
		
		if(!validRefreshmentNumber  ) {
			throw new InvalidID("Refreshment list must contains more than three items.");
		}
		                              
		                             
		
		return true;
	}
	
	public boolean validNoDuplicates(String[]refreshments) throws InvalidID
	{
		
		boolean noDuplicates = checkForDuplicates(refreshments);
		                        
		                             
			if( !noDuplicates ) {
				throw new InvalidID(" There must not be any duplicate items in the refreshment list."  );
			
		}
		return true;
	}
	}


