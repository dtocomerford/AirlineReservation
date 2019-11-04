package assessmentPart1;

import java.util.ArrayList;
import java.util.Scanner;

public class AirlineReservation {

 
	
	public static Scanner input = new Scanner(System.in);

	// Customer info variables
	public static String customerName;
	public static String originCity;
	public static String destinationCity;
	public static String customerFlightNumber;
	public static String seatSelection;
	public static String seatingSection;
	
	//Variable to hold the amount of available seats on the flight
	public static int availableSeats;
	
	//variable to track which coach class select
	public static int coachClassNumber;
	
	// Customer manual seat choice variable
	public static int userInput;
	
	//Customer menu choices
	public static int menuChoice;
	public static int coachChoice;
	public static String userChoice;
	
	
	// Flight number variables
	public static int numberOfFlights = 1;
	public static String flightLetters = "FL";
	public static String flightNumber = "";

	// Seat number used when populating the arrays
	public static int numberAssigner = 1;

	// Booleans
	public static int pass = 1;
	public static boolean automate;
	public static boolean firstClassFull = false;
	public static boolean businessClassFull = false;
	public static boolean economyClassFull = false;
	public static boolean flightHasLeft = false;
	
	// Seat arrays for each coach class
	public static String[][] firstClass = new String[9][2];
	public static String[][] business = new String[9][3];
	public static String[][] economy = new String[9][5];

	
	//Functions called in main are only done once, at the start of the program
	public static void main(String[] args) 
	{
		populateSeatNumbers(firstClass);
		populateSeatNumbers(business);
		populateSeatNumbers(economy);
		
		availableSeats += countAllAvailableSeats(firstClass);
		availableSeats += countAllAvailableSeats(business);
		availableSeats += countAllAvailableSeats(economy);

		start();
	}

	//The program is looped from here 
	public static void start() 
	{
		if(flightHasLeft == true)
		{
			flightHasLeft = false;
			createNewFlight();
		}
		
		
		//calls the menu function
		customerMenu();
		
		//System.out.println("Flight number " + numberOfFlights);
		//System.out.println("Total seats available: " + availableSeats);
	}

	
	//Populates the arrays with seat numbers, the function is passed an array (seatClass)
	public static void populateSeatNumbers(String[][] seatClass) 
	{
		//Nested for loop to access each cell of the array
		for (int column = 0; column < seatClass[0].length; column++) 
		{
			for (int row = 0; row < seatClass.length; row++) 
			{
				// Adding a 0 to the front of single digit seat numbers E.G 01, 02, 03...
				if (numberAssigner <= 9) 
				{
					String number = Integer.toString(numberAssigner);

					seatClass[row][column] = (new StringBuilder()).append("0").append(number).toString();
					numberAssigner++;
				} 
				else 
				{
					seatClass[row][column] = Integer.toString(numberAssigner);
					numberAssigner++;
				}
			}
		}
	}

	
	//Function to print the arrays to the console, function takes a 2-D array
	public static void printSeats(String[][] seats) 
	{
		//Some local variables 
		int multipleOfThree;
		int counter = 1;

		System.out.println();
		
		//Nested for loop
		for (int i = 0; i < seats[0].length; i++) 
		{
			for (int j = 0; j < seats.length; j++) 
			{
				//Calculations to get the remainder of the counter variable divided by 3
				multipleOfThree = counter % 3;
				counter++;

				//if theres no remainder then the console will print a space between the seat numbers
				//this creates the seating pattern of the flight E.G  01 02 03    04 05 06    07 08 09
				if (multipleOfThree == 0) 
				{
					System.out.print("[" + seats[j][i] + "]    ");
				} 
				else 
				{
					System.out.print("[" +seats[j][i] + "] ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	
	//Function which prints the menu to console and allows the user to interact with the program
	public static void customerMenu() 
	{
		
		//Gives the user the choice of selecting a seat or quitting
		System.out.println("Welcome!\nPress (1) to book a seat\nPress (2) to quit");
		menuChoice = input.nextInt();
		
		if(menuChoice == 2)
		{
			if(seatSelection == null)
			{
				System.out.println("Sorry you were unable to book a ticket, please try again later");
				System.exit(0);
			}
			else
			{
				generateBoardingPass();
				System.exit(0);
			}
		}else {}
		
		
		
		//Allows user to input personal information which will be used for the boarding pass
		System.out.println("Please enter your name: ");
		customerName = input.next();
		System.out.println("Please enter origin city: ");
		originCity = input.next();
		System.out.println("Please enter destination city: ");
		destinationCity = input.next();
		System.out.println();
		
		
		
		//Customer chooses if seat is selected automatically or manually
		System.out.println("Would you like to pick a seat or be assigned one?\n(P)ick\n(A)utomate");

		while (pass == 1) {
			String userInput = input.next().toUpperCase();
			if (userInput.contentEquals("P")) {
				automate = false;
				pass = 2;
			} else if (userInput.contains("A")) {
				automate = true;
				pass = 2;
			}

		}
		
		
		//Customer coach class choice list
		System.out.println("Please type 1 for First Class\n" + 
				"Please type 2 for Business\n" + 
				"Please type 3 for Economy");

		coachChoice = input.nextInt();
		
		//Switch statement used to call the manual seat selection function or the automated seat selection function
		if (automate == true) {
			switch (coachChoice) {
			case 1:
				coachClassNumber = 1;
				pickSeatAutomatically(firstClass);
				break;
			case 2:
				coachClassNumber = 2;
				pickSeatAutomatically(business);
				break;
			case 3:
				coachClassNumber = 3;
				pickSeatAutomatically(economy);
				break;
			}
		} else {
			switch (coachChoice) {
			case 1:
				coachClassNumber = 1;
				pickSeatManually(firstClass);
				break;
			case 2:
				coachClassNumber = 2;
				pickSeatManually(business);
				break;
			case 3:
				coachClassNumber = 3;
				pickSeatManually(economy);
				break;
			}
		}
	}

	
	//Function which creates a boarding pass and prints it to console
	public static void generateBoardingPass() 
	{
		System.out.println(coachClassNumber);
		
		//Create flight number 
		if (numberOfFlights > 28) 
		{
			numberOfFlights = 1;
		}

		if (numberOfFlights <= 9) 
		{
			//Append variable that stores flight number to add a 0 to it, if single digit E.G 01, 02, 03...
			flightNumber = Integer.toString(numberOfFlights);
			flightNumber = (new StringBuilder()).append("0").append(numberOfFlights).toString();
			customerFlightNumber = (new StringBuilder()).append(flightLetters).append(flightNumber).toString();
		} else {
			flightNumber = Integer.toString(numberOfFlights);
			customerFlightNumber = (new StringBuilder()).append(flightLetters).append(flightNumber).toString();
		}
		
		
		//Switch statement to set the seating section variable 
		switch(coachClassNumber)
		{
			case 1:
				seatingSection = "First Class";
				break;
			case 2:
				seatingSection = "Business";
				break;
			case 3:
				seatingSection = "Economy";
				break;			
		}
		
		//Print boarding pass
		System.out.println();
		System.out.println("|[   BOARDING PASS   ]|");
		System.out.println("Name\t: " + customerName);
		System.out.println("From\t: " + originCity);
		System.out.println("To\t: " + destinationCity);
		System.out.println("Flight\t: " + customerFlightNumber);
		System.out.println("Seat No\t: " + seatSelection);
		System.out.println("Section\t: " + seatingSection);
		System.out.println();
		
		printSeats(firstClass);
		printSeats(business);
		printSeats(economy);
	}
	
	
	//Function for automatically picking the customer seat
	public static void pickSeatAutomatically(String[][] seats) 
	{
		
		boolean seatFound = false;

		//Nested for loop to access all elements of the array 
		for (int i = 0; i < seats[0].length; i++) 
		{
			//if statement to break out of the loop if a seat has been found
			if (seatFound == true) 
			{
				break;
			}
			
			for (int j = 0; j < seats.length; j++) 
			{
				//If statement to detect free seat
				if (seats[j][i] != "XX") 
				{
					System.out.println("Seat " + seats[j][i] + " booked");
					seatSelection = seats[j][i];
					seats[j][i] = "XX";
					availableSeats--;
					seatFound = true;
					break;
				}

			}
			System.out.println();
		}

		printSeats(seats);
		
		
		
		//Ask customer if they want a seat in another class since their choice is full
		if(seatFound == false && coachClassNumber == 1 && flightHasLeft == false)
		{
			firstClassFull = true;
			boolean isFull = isPlaneFull();
			
			if(isFull == true)
			{
				return;
			}
			
			System.out.println("First class is full\nWould you like to try business? Y/N");
			userChoice = input.next().toUpperCase();
			
			if(userChoice.contentEquals("Y"))
			{
				coachClassNumber = 2;
				pickSeatAutomatically(business);
			}
			else
			{
				System.out.println("Sorry there is no available seat at the moment.\r\n" + 
						"Next flight leaves in 6 hours");
			}
		}
		
		if(seatFound == false && coachClassNumber == 2 && flightHasLeft == false)
		{
			businessClassFull = true;
			boolean isFull = isPlaneFull();
			if(isFull == true)
			{
				return;
			}
			System.out.println("Business class is full\nWould you like to try first class or economy? (F)irst class/(E)conomy");
			userChoice = input.next().toUpperCase();
			
			if(userChoice.contentEquals("F"))
			{
				coachClassNumber = 1;
				pickSeatAutomatically(firstClass);
			}
			else if(userChoice.contentEquals("E"))
			{
				coachClassNumber = 3;
				pickSeatAutomatically(economy);
			}
			else
			{
				System.out.println("Sorry there is no available seat at the moment.\r\n" + 
						"Next flight leaves in 6 hours");
			}
		}
		
		if(seatFound == false && coachClassNumber == 3 && flightHasLeft == false)
		{
			economyClassFull = true;
			boolean isFull = isPlaneFull();
			if(isFull == true)
			{
				return;
			}

			System.out.println("Economy class is full\nWould you like to try business? Y/N\"");
			
			userChoice = input.next().toUpperCase();
			
			if(userChoice.contentEquals("Y"))
			{
				coachClassNumber = 2;
				pickSeatAutomatically(business);
			}
		}
		
		//Sets seat selection variable to null if no seat was found
		if(seatFound == false)
		{
			seatSelection = null;
		}
		
		System.out.println();
		resetToLoopBack();
		start();
	}

	//Function for manually picking the customer seat
	public static void pickSeatManually(String[][] seats) 
	{
		
		boolean seatFound = false;
		String seatChoice;

		//Passes array of seats to the printSeats function to print them to console
		printSeats(seats);
		
		
		System.out.println("Enter seat number you wish to book: ");
		userInput = input.nextInt();
		
		//If statement which appends string of numbers of 9 or lower E.G 01, 02, 03...
		if (userInput <= 9) 
		{
			seatChoice = Integer.toString(userInput);
			seatChoice = (new StringBuilder()).append("0").append(seatChoice).toString();
		} 
		else 
		{
			seatChoice = Integer.toString(userInput);
		}

		for (int i = 0; i < seats[0].length; i++) 
		{
			//If seat found then break out of loop
			if (seatFound == true) 
			{
				break;
			}

			for (int j = 0; j < seats.length; j++) 
			{

				// Assign seat if free
				if (seatChoice.equals(seats[j][i]))
				{
					System.out.println("Seat " + seatChoice + " booked");
					
					//Set seat selection variable to customers choice as it was available
					seatSelection = seats[j][i];
					
					//Then set that cell of the array to XX to show that it is taken
					seats[j][i] = "XX";
					
					availableSeats--;
					printSeats(seats);
					seatFound = true;
					if (seatFound == true) {
						break;
					}
				}
			}
		}

		
		//Ask customer if they want a seat in another class since their choice is full
		if(seatFound == false && coachClassNumber == 1 && flightHasLeft == false)
		{
			firstClassFull = true;
			boolean isFull = isPlaneFull();
					
			if(isFull == true)
			{
				return;
			}
					
			System.out.println("First class is full\nWould you like to try business? Y/N");
			userChoice = input.next().toUpperCase();
					
			if(userChoice.contentEquals("Y"))
			{
				coachClassNumber = 2;
				pickSeatAutomatically(business);
			}
			else
			{
				System.out.println("Sorry there is no available seat at the moment.\r\n" + 
						"Next flight leaves in 6 hours");
			}
		}
		
		if(seatFound == false && coachClassNumber == 2 && flightHasLeft == false)
		{
			businessClassFull = true;
			boolean isFull = isPlaneFull();
			if(isFull == true)
			{
				return;
			}
			System.out.println("Business class is full\nWould you like to try first class or economy? (F)irst class/(E)conomy");
			userChoice = input.next().toUpperCase();
			
			if(userChoice.contentEquals("F"))
			{
				coachClassNumber = 1;
				pickSeatAutomatically(firstClass);
			}
			else if(userChoice.contentEquals("E"))
			{
				coachClassNumber = 3;
				pickSeatAutomatically(economy);
			}
			else
			{
				System.out.println("Sorry there is no available seat at the moment.\r\n" + 
						"Next flight leaves in 6 hours");
			}
		}
		
		
		if(seatFound == false && coachClassNumber == 3 && flightHasLeft == false)
		{
			economyClassFull = true;
			boolean isFull = isPlaneFull();
			if(isFull == true)
			{
				return;
			}

			System.out.println("Economy class is full\nWould you like to try business? Y/N\"");
			
			userChoice = input.next().toUpperCase();
			
			if(userChoice.contentEquals("Y"))
			{
				coachClassNumber = 2;
				pickSeatAutomatically(business);
			}
		}
	
		
		if (seatFound == false) 
		{
			seatSelection = null;
			System.out.println("Seat " + seatChoice + " taken");
		}

		
		resetToLoopBack();
		start();

	}

	
	//Resets variables so the program can be run again
	public static void resetToLoopBack() 
	{
		pass = 1;
		userInput = 0;
		
	}
	
	
	//Counts all the seats still available on the flight
	public static int countAllAvailableSeats(String[][] seats)
	{
		ArrayList<String> freeSeats = new ArrayList<String>();
		int count = 0;
		
		
		for (int i = 0; i < seats[0].length; i++) 
		{	
			for (int j = 0; j < seats.length; j++) 
			{
				if (seats[j][i] != "XX") 
				{
					freeSeats.add(seats[j][i]);
				}
			}
		}
		
		for(int i = 0; i < freeSeats.size(); i++)
		{
			count++;
		}
		
		return count;
	}
	
	
	//Function which re-populates the arrays, increases the flight number by 1 and resets some variables 
	public static void createNewFlight()
	{
		numberAssigner = 1;
		
		//Increases the flight number
		numberOfFlights++;
		
		//If statement to stop the flight number going above 28 flights
		if (numberOfFlights > 28) 
		{
			numberOfFlights = 1;
		}
		populateSeatNumbers(firstClass);
		populateSeatNumbers(business);
		populateSeatNumbers(economy);
		
		firstClassFull = false;
		businessClassFull = false;
		economyClassFull = false;
		
	}
	
	//A function which returns a boolean to check if the flight is full
	public static boolean isPlaneFull()
	{
		System.out.println("First " + firstClassFull);
		System.out.println("Business " + businessClassFull);
		System.out.println("Economy " + economyClassFull);
		
		if(firstClassFull == true && businessClassFull == true && economyClassFull == true)
		{
			flightHasLeft = true;
			
			System.out.println("Sorry there is no available seat at the moment.\r\n" + 
					"Next flight leaves in 6 hours");
			
			//createNewFlight();
			System.out.println();
			
			return true;
		}
		else 
		{
			return false;
		}
		
	}
	
}
