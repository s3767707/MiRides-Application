package io;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import cars.Car;
import cars.SilverServiceCar;
import exception.CorruptedFileException;
import exception.InvalidID;

public class Persistence 
{
	
	public void saveDogs(Car[] cars) throws IOException
	{
	
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("data.txt")));
		
		for(Car car: cars)
		{
			if(car != null)
			{
				String string = car.toString();
				pw.println(string);
			}
		}
		pw.close();
	}
	
	
	public Car[] readData(String fileName) throws IOException, CorruptedFileException, InvalidID
	{
	
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = null;
		
		
		Car[] records = new Car[16];
		
		int i = 0;
		while ((line = br.readLine()) != null)
		{
			
			StringTokenizer inReader = new StringTokenizer(line, ":");
			
			
			checkFileIntegrity(inReader);
			
			
			Car cars  = createCar(inReader);
			
			
			records[i] = cars;
			i++;
		}
		br.close();
		return records;
	}
	

	private void checkFileIntegrity(StringTokenizer inReader) throws IOException, CorruptedFileException
	{
		if (inReader.countTokens() != 2)
		{
			throw new CorruptedFileException("File has been corrupted");
		}
	}
	
	
	private Car createCar(StringTokenizer inReader) throws InvalidID
	{
		String regNo = inReader.nextToken();
		String make = inReader.nextToken();
		String model = inReader.nextToken();
		String driverName = inReader.nextToken();
		int passengerCapacity = Integer.parseInt(inReader.nextToken());
		double bookingFee = Double.parseDouble(inReader.nextToken());
		String[] refreshments = null;
		for(int i=0;i<refreshments.length;i++)
		{
			refreshments[i]=inReader.nextToken();;
			
		}
		Car car = new Car( regNo,  make,  model,  driverName,  passengerCapacity);
		car.setRegNo(regNo);
		car.setPassengerCapacity(passengerCapacity); 
		Car car2 = new SilverServiceCar( regNo,  make,  model,  driverName,  passengerCapacity,
				 bookingFee,  refreshments);
		return car;
	}
}
