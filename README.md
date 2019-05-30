# s3767707-Assignment-2

## MiRides Application

### INTRODUCTION
------------

Thank you for downloading the MiRides Application. This download contains code designed for car booking services.

MiRides application does not troubleshoot content from GitHub. The sample code is as an example of code that has worked for previous implementations and was created by Anson Go Guang Ping. Please send any GitHub-related comments and feedback to s3767707@student.rmit.edu.au.

#### REQUIREMENTS
------------

JDK 1.6 

CONTENTS
--------

This sample zip contains:

    /readme.txt - this file
    /src - example code to execute the MiRides Application
    
PRE-REQUISITES
--------------

The following are pre-requisites to successfully run the sample code:

1. Java SE Runtimr Environment 8
-   Link ( https://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html )
    
    
RUNNING THE EXAMPLE
-------------------
 
1. Download the repository file as a zip file.
2. Install Java Eclipse JDK
3. Open File -> Import -> File System -> Browse 
4. Select the file you download and click on the file.
5. The file should contain 6 subfiles :
-   app
-   cars
-   exceptions
-   io
-   main
-   utilities
6. Click Finish.
7. Compile and run the code.

DESCRIPTION
------------
 
The project should start by displaying menu to prompt user input. Users are able to carry out functions such as register a car, book a car or search for specific cars. This is a brief code showing part of the project :

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
         
  
===============================================================================
Hope you enjoys MiRides Application. Cheers !


