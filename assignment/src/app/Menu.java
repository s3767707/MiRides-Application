package app;


	
import java.util.Scanner;

import cars.Car;
import cars.SilverServiceCar;
import exception.InvalidBooking;
import exception.InvalidID;
import utilities.DateTime;

	
	public class Menu {
		
		private Scanner console = new Scanner(System.in);
		private MiRidesApplication application = new MiRidesApplication();
		private DateTime date;
		private String[]refreshments;
		// Allows me to turn validation on/off for testing business logic in the
		// classes.
		private boolean testingWithValidation = true;
		private Car car;
		private SilverServiceCar ss;

		/*
		 * Runs the menu in a loop until the user decides to exit the system.
		 */
		public void run() throws InvalidID
		{
			final int MENU_ITEM_LENGTH = 2;
			String input;
			String choice = "";
			do
			{
				printMenu();

				input = console.nextLine().toUpperCase();

				if (input.length() != MENU_ITEM_LENGTH)
				{
					System.out.println("Error - selection must be two characters!");
				} else
				{
					System.out.println();

					switch (input)
					{
					case "CC":
							createCar();
						break;
					case "BC":
							book();
						break;
					case "CB":
						completeBooking();
						break;
					case "DA":
                        System.out.print("\nEnter Type (SD/SS) : ");
                        String carType=console.nextLine();
                        System.out.println("\nEnter sort order (A/D) :");
                        String sortOrder=console.nextLine();
						System.out.println(application.displayAllCars(carType, sortOrder));
						
						break;
					case "SS":
						System.out.print("Enter Registration Number: ");
						System.out.println(application.displaySpecificCar(console.nextLine()));
						break;
					case "SA":
						System.out.print("Enter Type (SD/SS) :");
						System.out.print("Enter date : ");
						System.out.println("format DD/MM/YYYY)");
						System.out.println(application.displaySpecificCar(console.nextLine(), date));
						String dateEntered = console.nextLine();
						int day = Integer.parseInt(dateEntered.substring(0, 2));
						int month = Integer.parseInt(dateEntered.substring(3, 5));
						int year = Integer.parseInt(dateEntered.substring(6));
					    date = new DateTime(day, month, year);
					case "SD":
						application.seedData();
						break;
					case "EX":
						choice = "EX";
						System.out.println("Exiting Program ... Goodbye!");
						break;
					default:
						System.out.println("Error, invalid option selected!");
						System.out.println("Please try Again...");
					}
				}

			} while (choice != "EX");
		}

		/*
		 * Creates cars for use in the system available or booking.
		 */
		private void createCar() throws InvalidID
		{
	
			String id = "", make, model, driverName,type,serviceType;
			String[] refreshments=null;
			int numPassengers = 0;
			double bookingFee= 0;
        
			System.out.print("Enter registration number: ");
			id = promptUserForRegNo();
			if (id.length() != 0)
			{
				// Get details required for creating a car.
				System.out.print("Enter Make: ");
				make = console.nextLine();

				System.out.print("Enter Model: ");
				model = console.nextLine();

				System.out.print("Enter Driver Name: ");
				driverName = console.nextLine();

				System.out.print("Enter number of passengers: ");
				numPassengers = promptForPassengerNumbers();

				System.out.print("Enter Service Type (SD/SS) :");
				type= promptForServiceType();
				
				switch(type) {
				case "SS":
					System.out.print("\nEnter Standard Fee :");
					bookingFee= promptForStandardFee();
					System.out.print("\nEnter List of Refreshments :");
					
					for(int i=0;i<refreshments.length;i++)
					{
						refreshments[i]=console.nextLine();
						
					}
					try {
					    ss.validRefreshmentsNumber(refreshments);
					}catch(InvalidID e) {
						System.out.println(e);
					}
					try{
						ss.validNoDuplicates(refreshments);
					}catch(InvalidID e) {
						System.out.println(e);
					}
					boolean result = application.checkIfCarExists(id);

					if (!result)
					{
						String carRegistrationNumber = application.createCar(id, make, model, driverName, numPassengers, bookingFee, refreshments);
						System.out.println(carRegistrationNumber);
					} else
					{
						System.out.println("Error - Already exists in the system");
					}
				case "SD":
					boolean result2 = application.checkIfCarExists(id);

					if (!result2)
					{
						String carRegistrationNumber = application.createCar(id, make, model, driverName, numPassengers);
						System.out.println(carRegistrationNumber);
					} else
					{
						System.out.println("Error - Already exists in the system");
					}
				default:
					System.out.println("Type can only be either SD or SS.");
				}
				
				
				}
				
				}
			
				
				

		/*
		 * Book a car by finding available cars for a specified date.
		 */
		private boolean book() 
		{
			System.out.println("Enter date car required: ");
			System.out.println("format DD/MM/YYYY)");
			String dateEntered = console.nextLine();
			int day = Integer.parseInt(dateEntered.substring(0, 2));
			int month = Integer.parseInt(dateEntered.substring(3, 5));
			int year = Integer.parseInt(dateEntered.substring(6));
			DateTime dateRequired = new DateTime(day, month, year);
			
			try{
				car.validBookingPastDate(dateRequired);
				
			}catch(InvalidBooking e){
				System.out.println(e);
				return true;
			}
			try{
				car.validBookingLessThan7Days(dateRequired); 
			}catch(InvalidBooking e){
				System.out.println(e);
				return true;
			}
			try{
				car.validBookingBookOnDate(dateRequired); 
			}catch(InvalidBooking e){
				System.out.println(e);
				return true;
			}
			
			String[] availableCars = application.book(dateRequired);
			for (int i = 0; i < availableCars.length; i++)
			{
				System.out.println(availableCars[i]);
			}
			if (availableCars.length != 0)
			{
				System.out.println("Please enter a number from the list:");
				int itemSelected = Integer.parseInt(console.nextLine());
				
				String regNo = availableCars[itemSelected - 1];
				regNo = regNo.substring(regNo.length() - 6);
				System.out.println("Please enter your first name:");
				String firstName = console.nextLine();
				System.out.println("Please enter your last name:");
				String lastName = console.nextLine();
				System.out.println("Please enter the number of passengers:");
				int numPassengers = Integer.parseInt(console.nextLine());
				String result = application.book(firstName, lastName, dateRequired, numPassengers, regNo);

				System.out.println(result);
			} else
			{
				System.out.println("There are no available cars on this date.");
			}
			return true;
		}
		
		/*
		 * Complete bookings found by either registration number or booking date.
		 */
		private void completeBooking()
		{
			System.out.print("Enter Registration or Booking Date:");
			String response = console.nextLine();
			
			String result;
			// User entered a booking date
			if (response.contains("/"))
			{
				System.out.print("Enter First Name:");
				String firstName = console.nextLine();
				System.out.print("Enter Last Name:");
				String lastName = console.nextLine();
				System.out.print("Enter kilometers:");
				double kilometers = Double.parseDouble(console.nextLine());
				int day = Integer.parseInt(response.substring(0, 2));
				int month = Integer.parseInt(response.substring(3, 5));
				int year = Integer.parseInt(response.substring(6));
				DateTime dateOfBooking = new DateTime(day, month, year);
				result = application.completeBooking(firstName, lastName, dateOfBooking, kilometers);
				System.out.println(result);
			} else
			{
				
				System.out.print("Enter First Name:");
				String firstName = console.nextLine();
				System.out.print("Enter Last Name:");
				String lastName = console.nextLine();
				if(application.getBookingByName(firstName, lastName, response))
				{
					System.out.print("Enter kilometers:");
					double kilometers = Double.parseDouble(console.nextLine());
					result = application.completeBooking(firstName, lastName, response, kilometers);
					System.out.println(result);
				}
				else
				{
					System.out.println("Error: Booking not found.");
				}
			}
			
		}
		
		private int promptForPassengerNumbers()
		{
			int numPassengers = 0;
			boolean validPassengerNumbers = false;
			// By pass user input validation.
			if (!testingWithValidation)
			{
				return Integer.parseInt(console.nextLine());
			} 
			else
			{
				while (!validPassengerNumbers)
				{
					numPassengers = Integer.parseInt(console.nextLine());

					String validId = application.isValidPassengerCapacity(numPassengers);
					if (validId.contains("Error:"))
					{
						System.out.println(validId);
						System.out.println("Enter passenger capacity: ");
						System.out.println("(or hit ENTER to exit)");
					} else
					{
						validPassengerNumbers = true;
					}
				}
				return numPassengers;
			}
		}
		/*
		 * Prompt user for registration number and validate it is in the correct form.
		 * Boolean value for indicating test mode allows by passing validation to test
		 * program without user input validation.
		 */
		
	
		private String promptForServiceType()
		{
			String serviceType = null;
			boolean validServiceType = false;
			// By pass user input validation.
			if (!testingWithValidation)
			{
				return console.nextLine().toUpperCase();
			} 
			else
			{
				while (!validServiceType)
				{
					serviceType = console.nextLine().toUpperCase();

					String validStringServiceType = application.isValidServiceType(serviceType);
					if (validStringServiceType.contains("Error:"))
					{
						System.out.println(validServiceType);
						System.out.println("Enter Service Type: ");
						System.out.println("(or hit ENTER to exit)");
					} else
					{
						validServiceType = true;
					}
				}
				return serviceType;
			}
		}
		private double promptForStandardFee()
		{
			double standardFee = 0;
			boolean validStandardFee = false;
			// By pass user input validation.
			if (!testingWithValidation)
			{
				return Double.parseDouble(console.nextLine());
			} 
			else
			{
				while (!validStandardFee)
				{
					standardFee = Integer.parseInt(console.nextLine());

					String validFee = application.isValidStandardFee(standardFee);
					if (validFee.contains("Error:"))
					{
						System.out.println(validFee);
						System.out.println("Enter standard fee: ");
						System.out.println("(or hit ENTER to exit)");
					} else
					{
						validStandardFee = true;
					}
				}
				return standardFee;
			}
		}
		private String promptUserForRegNo() throws InvalidID
		{
			String regNo = "";
			boolean validRegistrationNumber = false;
			// By pass user input validation.
			if (!testingWithValidation)
			{
				return console.nextLine();
			} 
			else
			{
				while (!validRegistrationNumber)
				{
					regNo = console.nextLine();
					boolean exists = application.checkIfCarExists(regNo);
					if(exists)
					{
						// Empty string means the menu will not try to process
						// the registration number
						System.out.println("Error: Reg Number already exists");
						return "";
					}
					if (regNo.length() == 0)
					{
						break;
					}

					String validId = application.isValidId(regNo);
					if (validId.contains("Error:"))
					{
						System.out.println(validId);
						System.out.println("Enter registration number: ");
						System.out.println("(or hit ENTER to exit)");
					} else
					{
						validRegistrationNumber = true;
					}
				}
				return regNo;
			}
		}

		/*
		 * Prints the menu.
		 */
		private void printMenu()
		{
			System.out.printf("\n********** MiRide System Menu **********\n\n");

			System.out.printf("%-30s %s\n", "Create Car", "CC");
			System.out.printf("%-30s %s\n", "Book Car", "BC");
			System.out.printf("%-30s %s\n", "Complete Booking", "CB");
			System.out.printf("%-30s %s\n", "Display ALL Cars", "DA");
			System.out.printf("%-30s %s\n", "Search Specific Car", "SS");
			System.out.printf("%-30s %s\n", "Search Available Cars", "SA");
			System.out.printf("%-30s %s\n", "Seed Data", "SD");
			System.out.printf("%-30s %s\n", "Exit Program", "EX");
			System.out.println("\nEnter your selection: ");
			System.out.println("(Hit enter to cancel any operation)");
		}
		
		
	}


