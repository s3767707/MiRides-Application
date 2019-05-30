package app;

import java.util.Scanner;

import cars.Booking;
import cars.Car;
import cars.SilverServiceCar;
import exception.InvalidID;
import utilities.DateTime;
import utilities.MiRidesUtilities;


public class MiRidesApplication
{
	private Car car=new Car();
	private Car[] cars = new Car[15];
	private SilverServiceCar[] SScars=new SilverServiceCar[12];
	private int itemCount = 0;
	private String[] availableCars;
	private Scanner console=new Scanner(System.in);
    private Booking[] book;

	public MiRidesApplication()
	{
		//seedData();
	}
	
	public String createCar(String id, String make, String model, String driverName, int numPassengers) throws InvalidID 
	{
		try {
		 isValidId(id);
		}catch(InvalidID e)
		{		
		System.out.println(e);
		
			if(e.contains("Error:"))
		
		{
			return isValidId(id);
		}
		}
		if(!checkIfCarExists(id)) {
			cars[itemCount] = new Car(id, make, model, driverName, numPassengers);
			itemCount++;
			return "New Car added successfully for registion number: " + cars[itemCount-1].getRegistrationNumber();
		}
		return "Error: Already exists in the system.";
	}
	
	public String createCar(String id, String make, String model, String driverName, int numPassengers, double bookingFee, String[] refreshments) throws InvalidID 
	{
		try {
			 isValidId(id);
			}catch(InvalidID e)
			{		
			System.out.println(e);
			
				if(e.contains("Error:"))
			
			{
				return isValidId(id);
			}
			}
		if(!checkIfCarExists(id)) {
			cars[itemCount] = new SilverServiceCar(id, make, model, driverName, numPassengers,bookingFee,refreshments);
			itemCount++;
			return "New Car added successfully for registion number: " + cars[itemCount-1].getRegistrationNumber();
		}
		return "Error: Already exists in the system.";
	}

	public String[] book(DateTime dateRequired)
	{
		int numberOfAvailableCars = 0;
		// finds number of available cars to determine the size of the array required.
		for(int i=0; i<cars.length; i++)
		{
			if(cars[i] != null)
			{
				if(!cars[i].isCarBookedOnDate(dateRequired))
				{
					numberOfAvailableCars++;
				}
			}
		}
		if(numberOfAvailableCars == 0)
		{
			String[] result = new String[0];
			return result;
		}
		availableCars = new String[numberOfAvailableCars];
		int availableCarsIndex = 0;
		// Populate available cars with registration numbers
		for(int i=0; i<cars.length;i++)
		{
			
			if(cars[i] != null)
			{
				if(!cars[i].isCarBookedOnDate(dateRequired))
				{
					availableCars[availableCarsIndex] = availableCarsIndex + 1 + ". " + cars[i].getRegistrationNumber();
					availableCarsIndex++;
				}
			}
		}
		return availableCars;
	}
	
	public String book(String firstName, String lastName, DateTime required, int numPassengers, String registrationNumber)
	{
		Car car = getCarById(registrationNumber);
		if(car != null)
        {
			if(car.book(firstName, lastName, required, numPassengers))
			{

				String message = "Thank you for your booking. \n" + car.getDriverName() 
		        + " will pick you up on " + required.getFormattedDate() + ". \n"
				+ "Your booking reference is: " + car.getBookingID(firstName, lastName, required);
				return message;
			}
			else
			{
				String message = "Booking could not be completed.";
				return message;
			}
        }
        else{
            return "Car with registration number: " + registrationNumber + " was not found.";
        }
	}
	
	public String completeBooking(String firstName, String lastName, DateTime dateOfBooking, double kilometers)
	{
		String result = "";
		
		// Search all cars for bookings on a particular date.
		for(int i = 0; i <cars.length; i++)
		{
			if (cars[i] != null)
			{
				if(cars[i].isCarBookedOnDate(dateOfBooking))
				{
					return cars[i].completeBooking(firstName, lastName, dateOfBooking, kilometers);
				}
				else
			{
								}
				if(!result.equals("Booking not found"))
				{
					return result;
			}
			}
		}
		return "Booking not found.";
	}
	
	public String completeBooking(String firstName, String lastName, String registrationNumber, double kilometers)
	{
		String carNotFound = "Car not found";
		Car car = null;
		// Search for car with registration number
		for(int i = 0; i <cars.length; i++)
		{
			if (cars[i] != null)
			{
				if (cars[i].getRegistrationNumber().equals(registrationNumber))
				{
					car = cars[i];
					break;
				}
			}
		}

		if (car == null)
		{
			return carNotFound;
		}
		if (car.getBookingByName(firstName, lastName) != -1)
		{
			return car.completeBooking(firstName, lastName, kilometers);
		}
		return "Error: Booking not found.";
	}
	
	public boolean getBookingByName(String firstName, String lastName, String registrationNumber)
	{
		String bookingNotFound = "Error: Booking not found";
		Car car = null;
		// Search for car with registration number
		for(int i = 0; i <cars.length; i++)
		{
			if (cars[i] != null)
			{
				if (cars[i].getRegistrationNumber().equals(registrationNumber))
				{
					car = cars[i];
					break;
				}
			}
		}
		
		if(car == null)
		{
			return false;
		}
		if(car.getBookingByName(firstName, lastName) == -1)
		{
			return false;
		}
		return true;
	}
	public String displaySpecificCar(String regNo)
	{
		for(int i = 0; i < cars.length; i++)
		{
			if(cars[i] != null)
			{
				if(cars[i].getRegistrationNumber().equals(regNo))
				{
					return cars[i].getDetails();
				}
			}
		}
		return "Error: The car could not be located.";
	}
	
	public String displaySpecificCar(String serviceType,DateTime date)
	{
		
		
		for(int i = 0; i < cars.length; i++)
		{
			if(car.notCurrentlyBookedOnDate(date)==true )
			{
				if(serviceType.contains("SD"))
				{
					return cars[i].getDetails();
				}
				if(serviceType.contains("SS"))
				{
					return SScars[i].getDetails();
				}
			}
		}
		return "Error: The car could not be located.";
	}
	
	public boolean seedData() throws InvalidID
	{
		for(int i = 0; i < cars.length; i++)
		{
			if(cars[i] != null)
			{
				return false;
			}
		}
		// 2 cars not booked
		Car honda = new Car("SIM194", "Honda", "Accord Euro", "Henry Cavill", 5);
		cars[itemCount] = honda;
		honda.book("Craig", "Cocker", new DateTime(1), 3);
		itemCount++;
		
		Car lexus = new Car("LEX666", "Lexus", "M1", "Angela Landsbury", 3);
		cars[itemCount] = lexus;
		lexus.book("Craig", "Cocker", new DateTime(1), 3);
		itemCount++;
		
		// 2 cars booked
		Car bmw = new Car("BMW256", "Mini", "Minor", "Barbara Streisand", 4);
		cars[itemCount] = bmw;
		itemCount++;
		bmw.book("Craig", "Cocker", new DateTime(1), 3);
		
		Car audi = new Car("AUD765", "Mazda", "RX7", "Matt Bomer", 6);
		cars[itemCount] = audi;
		itemCount++;
		audi.book("Rodney", "Cocker", new DateTime(1), 4);
		
		// 1 car booked five times (not available)
		Car toyota = new Car("TOY765", "Toyota", "Corola", "Tina Turner", 7);
		cars[itemCount] = toyota;
		itemCount++;
		toyota.book("Rodney", "Cocker", new DateTime(1), 3);
		toyota.book("Craig", "Cocker", new DateTime(2), 7);
		toyota.book("Alan", "Smith", new DateTime(3), 3);
		toyota.book("Carmel", "Brownbill", new DateTime(4), 7);
		toyota.book("Paul", "Scarlett", new DateTime(5), 7);
		toyota.book("Paul", "Scarlett", new DateTime(6), 7);
		toyota.book("Paul", "Scarlett", new DateTime(7), 7);
		

		Car rover = new Car("ROV465", "Honda", "Rover", "Jonathon Ryss Meyers", 7);
		cars[itemCount] = rover;
		itemCount++;
		rover.book("Rodney", "Cocker", new DateTime(1), 3);
	
		DateTime inTwoDays = new DateTime(2);
		rover.book("Rodney", "Cocker", inTwoDays, 3);
		rover.completeBooking("Rodney", "Cocker", inTwoDays,75);
		
		
		Car proton = new Car("PRO123", "Proton", "Saga", "William Ardor", 5);
		cars[itemCount] = proton;
		proton.book("Will", "Meg", new DateTime(1), 3);
		itemCount++;
		
		Car jeep = new Car("JEE666", "Jeep", "M1", "Angela Mei", 3);
		cars[itemCount] = jeep;
		jeep.book("Angel", "Lee", new DateTime(1), 4);
		itemCount++;
		

		Car toyota2 = new Car("TOY256", "Sace", "Vios", "Drow Ranger", 4);
		cars[itemCount] = toyota2;
		itemCount++;
		toyota2.book("Sace", "Vios", new DateTime(1), 4);
		
		Car volks = new Car("VOL345", "Bentley", "X72", "Legion Commander", 6);
		cars[itemCount] = volks;
		itemCount++;
		audi.book("Rodney", "Cocker", new DateTime(1), 4);
		
		Car hsv = new Car("HSV765", "Toyu", "Cola", "Tin Tan", 7);
		cars[itemCount] = hsv;
		itemCount++;
		toyota.book("Rodney", "Cocker", new DateTime(1), 3);
		toyota.book("Craig", "Cocker", new DateTime(2), 7);
		toyota.book("Alan", "Smith", new DateTime(3), 3);
		toyota.book("Carmel", "Brownbill", new DateTime(4), 7);
		toyota.book("Paul", "Scarlett", new DateTime(5), 7);
		toyota.book("Paul", "Scarlett", new DateTime(6), 7);
		toyota.book("Paul", "Scarlett", new DateTime(7), 7);
	
		Car leyland = new Car("Ley475", "Lala", "R45", "Jong Mayor", 7);
		cars[itemCount] = leyland;
		itemCount++;
		rover.book("Rodney", "Cocker", new DateTime(1), 3);
		DateTime inFiveDays = new DateTime(2);
		rover.book("Rodney", "Cocker", inTwoDays, 3);
		rover.completeBooking("Rodney", "Cocker", inTwoDays,75);
		return true;
	}

	public String displayAllCars(String carType,String sortOrder)
	{
		Car[] filteredAndSortedCars = filterAndSort(carType,sortOrder);
		String allCarDetails = getAllCarsDetails(filteredAndSortedCars);
		if(itemCount == 0)
		{
			return "No cars have been added to the system.";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Summary of all cars: ");
		sb.append("\n");
		sb.append(allCarDetails);
		
		return sb.toString();
	}
	
	private Car[] filterAndSort(String carType, String sortOrder)
	{
		Car[] filteredCars = getFilteredCars(carType);
		boolean sorted = false;
		// 1. PROCESS until no sorting is required
		while (!sorted)
		{
			// Exit loop only when sorted remains true
			sorted = true;

			// 2. FOR EACH object

			for (int i = 0; i < filteredCars.length - 1; i++)
			{
				// 3. OBTAIN sort element for current and next object
				String currentCarRegNo = cars[i].getRegistrationNumber();
				String nextCarRegNo = filteredCars[i + 1].getRegistrationNumber();

				// 4. DETERMINE if current and next objects are in sorted order
				// 5. DETERMINE if objects need to be swapped
				boolean sortedAscending = currentCarRegNo.compareTo(nextCarRegNo) < 0 && sortOrder.equals("A");
				boolean sortedDescending = currentCarRegNo.compareTo(nextCarRegNo) > 0 && sortOrder.equals("D");
				boolean areEqual = currentCarRegNo.compareTo(nextCarRegNo) == 0;
				boolean isSorted = sortedAscending || sortedDescending || areEqual;

				if (!isSorted)
				{
					// 6. SWAP objects
					Car temp = cars[i];
					cars[i] = cars[i + 1]; // swapping
					cars[i + 1] = temp;
					// Assumption was incorrect as a sort occurred.
					sorted = false;
				}
			}
		}
		return cars;
	}
	
	private Car[] getFilteredCars(String carType)
	{
		// 2. COUNT number of pets of the chosen pet type
		int carCount = howManyCars(carType);
		Car[] cars = new Car[carCount];
		int numFilteredPets = 0;
		// 3. FOR EACH pet in the main collection
		for (int i = 0; i < cars.length; i++)
		{
			// 4. CHECK pet type against chosen pet type
			boolean filteredCarFound = carType.equals("SD") && cars[i] instanceof Car
					|| carType.equals("SS") && cars[i] instanceof SilverServiceCar;

			// 5. IF petType is equal to chosen pet type
			if (filteredCarFound)
			{
				// 6. ASSIGN pet to new collection
				cars[numFilteredPets] = cars[i];
				numFilteredPets++;
			}
		}
		return cars;
	}
	
	private int howManyCars(String carType)
	{
		int carCount = 0;
		for (int i = 0; i < cars.length; i++)
		{
			boolean filteredCarFound = carType.equals("SD") && cars[i] instanceof Car
					|| carType.equals("C") && cars[i] instanceof SilverServiceCar;

			if (filteredCarFound)
			{
				carCount++;
			}
		}
		return carCount;
	}

	private String getAllCarsDetails(Car[] cars)
	{
		String allCarsDetails = "";
		String allBookingDetails = "";
		for (int i = 0; i < cars.length; i++)
		{
			if (cars[i] != null)
			{
				allCarsDetails += cars[i].getDetails();
				allBookingDetails += book[i].getDetails();
			}
		}
		return allCarsDetails;
	}


	public String displayBooking(String id, String seatId)
	{
		Car booking = getCarById(id);
		if(booking == null)
		{
			return "Booking not found";
		}
		return booking.getDetails();
	}
	
	public String isValidId(String id) throws InvalidID
	{
		return MiRidesUtilities.isRegNoValid(id);
	}
	
	public String isValidPassengerCapacity(int passengerNumber) throws IndexOutOfBoundsException
	{
		return MiRidesUtilities.isPassengerCapacityValid(passengerNumber);
	}
	
	public String isValidStandardFee(double standardFee) {
		return MiRidesUtilities.isStandardFeeValid(standardFee);
	}
	
	public String isValidServiceType(String serviceType) {
		return MiRidesUtilities.isServiceTypeValid(serviceType);
	}

	public boolean checkIfCarExists(String regNo) 
	{
		Car car = null;
		if (regNo.length() != 6)
		{
			
			return false;
		}
		car = getCarById(regNo);
		if (car == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	private Car getCarById(String regNo)
	{
		Car car = null;

		for (int i = 0; i < cars.length; i++)
		{
			if(cars[i] != null)
			{
				if (cars[i].getRegistrationNumber().equals(regNo))
				{
					car = cars[i];
					return car;
				}
			}
		}
		return car;
	}
}
