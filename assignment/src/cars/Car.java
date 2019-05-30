package cars;

import exception.InvalidBooking;
import exception.InvalidID;
import utilities.DateTime;
import utilities.DateUtilities;
import utilities.MiRidesUtilities;

public class Car {

	public String regNo;
	public String make;
	public String model;
	public String driverName;
	public int passengerCapacity;

	protected Booking[] currentBookings;
	protected Booking[] pastBookings;
	protected boolean available;
	protected int bookingSpotAvailable = 0;
	protected double tripFee = 0;
	protected DateUtilities du;

	private final double STANDARD_BOOKING_FEE = 1.5;
	protected final int MAXIUM_PASSENGER_CAPACITY = 10;
	protected final int MINIMUM_PASSENGER_CAPACITY = 1;
	
	public Car() {
		
	}

	public Car(String regNo, String make, String model, String driverName, int passengerCapacity) throws InvalidID {
		setRegNo(regNo); // Validates and sets registration number
		setPassengerCapacity(passengerCapacity); // Validates and sets passenger capacity

		this.make = make;
		this.model = model;
		this.driverName = driverName;
		available = true;
		currentBookings = new Booking[5];
		pastBookings = new Booking[10];
	}

	public boolean book(String firstName, String lastName, DateTime required, int numPassengers) {
		boolean booked = false;
		available = bookingAvailable();
		boolean dateAvailable = notCurrentlyBookedOnDate(required);
		boolean dateValid = dateIsValid(required);
		boolean validPassengerNumber = numberOfPassengersIsValid(numPassengers);

		if (available && dateAvailable && dateValid && validPassengerNumber) {
			tripFee = STANDARD_BOOKING_FEE;
			Booking booking = new Booking(firstName, lastName, required, numPassengers, this);
			currentBookings[bookingSpotAvailable] = booking;
			bookingSpotAvailable++;
			booked = true;
		}
		return booked;
	}

	public String completeBooking(String firstName, String lastName, DateTime dateOfBooking, double kilometers) {

		int bookingIndex = getBookingByDate(firstName, lastName, dateOfBooking);

		if (bookingIndex == -1) {
			return "Booking not found.";
		}

		return completeBooking(bookingIndex, kilometers);
	}

	public String completeBooking(String firstName, String lastName, double kilometers) {
		int bookingIndex = getBookingByName(firstName, lastName);

		if (bookingIndex == -1) {
			return "Booking not found.";
		} else {
			return completeBooking(bookingIndex, kilometers);
		}
	}

	public boolean isCarBookedOnDate(DateTime dateRequired) {
		boolean carIsBookedOnDate = false;
		for (int i = 0; i < currentBookings.length; i++) {
			if (currentBookings[i] != null) {
				if (DateUtilities.datesAreTheSame(dateRequired, currentBookings[i].getBookingDate())) {
					carIsBookedOnDate = true; 
				}
			}
		}
		return carIsBookedOnDate;
	}

	public String getBookingID(String firstName, String lastName, DateTime dateOfBooking) {
		System.out.println();
		for (int i = 0; i < currentBookings.length; i++) {
			if (currentBookings[i] != null) {
				Booking booking = currentBookings[i];
				boolean firstNameMatch = booking.getFirstName().toUpperCase().equals(firstName.toUpperCase());
				boolean lastNameMatch = booking.getLastName().toUpperCase().equals(lastName.toUpperCase());
				int days = DateTime.diffDays(dateOfBooking, booking.getBookingDate());
				if (firstNameMatch && lastNameMatch && days == 0) {
					return booking.getID();
				}
			}
		}
		return "Booking not found";
	}

	public String getDetails() {
		StringBuilder sb = new StringBuilder();

		sb.append(getRecordMarker());
		sb.append(String.format("%-15s %s\n", "Reg No:", regNo));
		sb.append(String.format("%-15s %s\n", "Make & Model:", make + " " + model));

		sb.append(String.format("%-15s %s\n", "Driver Name:", driverName));
		sb.append(String.format("%-15s %s\n", "Capacity:", passengerCapacity));

		if (bookingAvailable()) {
			sb.append(String.format("%-15s %s\n", "Available:", "YES"));
		} else {
			sb.append(String.format("%-15s %s\n", "Available:", "NO"));
		}

		return sb.toString();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(regNo + ":" + make + ":" + model);
		if (driverName != null) {
			sb.append(":" + driverName);
		}
		sb.append(":" + passengerCapacity);
		if (bookingAvailable()) {
			sb.append(":" + "YES");
		} else {
			sb.append(":" + "NO");
		}

		return sb.toString();
	}

	public String getRegistrationNumber() {
		return regNo;
	}

	public String getDriverName() {
		return driverName;
	}

	public double getTripFee() {
		return tripFee;
	}

	protected boolean hasBookings(Booking[] bookings) {
		boolean found = false;
		for (int i = 0; i < bookings.length; i++) {
			if (bookings[i] != null) {
				found = true;
			}
		}
		return found;
	}

	private String completeBooking(int bookingIndex, double kilometers) {
		tripFee = 0;
		Booking booking = currentBookings[bookingIndex];
		currentBookings[bookingIndex] = null;
		bookingSpotAvailable = bookingIndex;

		
		double fee = kilometers * (STANDARD_BOOKING_FEE * 0.3);
		tripFee += fee;
		booking.completeBooking(kilometers, fee, STANDARD_BOOKING_FEE);
		
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
		System.out.println();
		for (int i = 0; i < currentBookings.length; i++) {
			if (currentBookings[i] != null) {
				Booking booking = currentBookings[i];
				boolean firstNameMatch = booking.getFirstName().toUpperCase().equals(firstName.toUpperCase());
				boolean lastNameMatch = booking.getLastName().toUpperCase().equals(lastName.toUpperCase());
				boolean dateMatch = DateUtilities.datesAreTheSame(dateOfBooking, currentBookings[i].getBookingDate());
				if (firstNameMatch && lastNameMatch && dateMatch) {
					return i;
				}
			}
		}
		return -1;
	}

	
	public int getBookingByName(String firstName, String lastName) {
		for (int i = 0; i < currentBookings.length; i++) {
			if (currentBookings[i] != null) {
				boolean firstNameMatch = currentBookings[i].getFirstName().toUpperCase()
						.equals(firstName.toUpperCase());
				boolean lastNameMatch = currentBookings[i].getLastName().toUpperCase().equals(lastName.toUpperCase());
				if (firstNameMatch && lastNameMatch) {
					return i;
				}
			}
		}
		return -1;
	}

	
	protected String getRecordMarker() {
		final int RECORD_MARKER_WIDTH = 60;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < RECORD_MARKER_WIDTH; i++) {
			sb.append("_");
		}
		sb.append("\n");
		return sb.toString();
	}

	
	protected boolean numberOfPassengersIsValid(int numPassengers) {
		if (numPassengers >= MINIMUM_PASSENGER_CAPACITY && numPassengers < MAXIUM_PASSENGER_CAPACITY
				&& numPassengers <= passengerCapacity) {
			return true;
		}
		return false;
	}

	
	private boolean dateIsValid(DateTime date) {
		return DateUtilities.dateIsNotInPast(date) && DateUtilities.dateIsNotMoreThan7Days(date);
	}

	
	protected boolean bookingAvailable() {
		for (int i = 0; i < currentBookings.length; i++) {
			if (currentBookings[i] == null) {
				if (i == currentBookings.length - 1) {
					available = false;
				} else {
					available = true;
				}
				bookingSpotAvailable = i;
				return true;
			}
		}
		return false;
	}

	
	
	public boolean notCurrentlyBookedOnDate(DateTime date) {
		boolean foundDate = true;
		for (int i = 0; i < currentBookings.length; i++) {
			if (currentBookings[i] != null) {
				int days = DateTime.diffDays(date, currentBookings[i].getBookingDate());
				if (days == 0) {
					return false;
				}
			}
		}
		return foundDate;
	}

	
	public void setRegNo(String regNo) throws InvalidID {
		if (!MiRidesUtilities.isRegNoValid(regNo).contains("Error:")) {
			this.regNo = regNo;
		} else {
			this.regNo = "Invalid";
		}
	}

	
	public void setPassengerCapacity(int passengerCapacity) {
		boolean validPasengerCapcity = passengerCapacity >= MINIMUM_PASSENGER_CAPACITY
				&& passengerCapacity < MAXIUM_PASSENGER_CAPACITY;

		if (validPasengerCapcity) {
			this.passengerCapacity = passengerCapacity;
		} else {
			this.passengerCapacity = -1;
		}
	}
	
			
		
		public boolean currentBookingNotMoreThanFive() {
			int count=0;
			currentBookings=new Booking[count];
			if(count<6) {
				return true;
			}
			return false;
		}
		public boolean currentBookingNotMoreThanFive(int count) throws InvalidBooking {
			boolean bookingSize = currentBookingNotMoreThanFive();
			 
			if(!bookingSize) {
				throw new InvalidBooking("Booking already reach limit.");
			}
			
			return true;
			
		}

			public boolean validBookingBookOnDate(DateTime date) throws InvalidBooking
			{
				boolean dateAvailable = notCurrentlyBookedOnDate(date);
				 
				if(!dateAvailable) {
					throw new InvalidBooking("Car is already booked on this date.");
				}
				
				return true;
			}
			
			public boolean validBookingPastDate(DateTime date) throws InvalidBooking
			{
				boolean pastDate=du.dateIsNotInPast( date);
				if(!pastDate) {
					throw new InvalidBooking(" Booked date is in the past.");
				}
				return true;
		}
			
			public boolean validBookingLessThan7Days(DateTime date) throws InvalidBooking
			{
				boolean lessThan7Days = du.dateIsNotMoreThan7Days( date);
				if(!lessThan7Days) {
					throw new InvalidBooking("Book date cannot be 7 days in advance.");
				}
				return true;
		}	
			
			public  boolean dateIsNotMoreThan3Days(DateTime date) throws InvalidBooking {
				boolean within3Days=du.dateIsNotMoreThan3Days(date);
				DateTime today = new DateTime();
				DateTime nextWeek = new DateTime(3);
				
				int daysInFuture = DateTime.diffDays(nextWeek, date);
				if(daysInFuture >0 && daysInFuture <4)
				{
					within3Days = true;
					throw new InvalidBooking("Book date cannot be 3 days in advance.");
				}
				return within3Days;
			} 
}

