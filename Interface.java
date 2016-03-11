import java.util.Scanner;
import java.text.DateFormat;

public class Interface
{
	public static void main(String [] args)
	{
		boolean  exitProgram = false;
		String   userInput = "";
		Scanner  userInputScanner = new Scanner(System.in);
		Calendar calendar;

		//if user enters a command argument
		if (args.length == 1)
		{
			//if user enters a valid .ics file
			if (!isIcsFile(args[0]))
			{
				System.out.println("The entered file is not a valid .ics file!");
				return;
			}

			System.out.println("importing .ics file into calendar object...");
			calendar = new Calendar(args[0]);
		}
		//if user enters more than 1 argument
		else if (args.length > 1)
		{
			System.out.println("Too many arguments!");
			return;
		}
		//no command arguments
		else
		{
			System.out.println("No import specified, creating a new .ics file...");
			calendar = new Calendar();
		}

		//get user input
		while(!exitProgram)
		{
			//take user input and converts to lower case
			System.out.print("Command: ");
       		userInput = userInputScanner.nextLine();
       		userInput = userInput.toLowerCase();

       		//list all commands
       		if(userInput.equals("commands"))
       		{
       			printAllCommands();
       		}
       		//add event to calendar
       		else if(userInput.equals("add"))
       		{
       			calendar.addEvent(addEventInterface());
       		}
       		//adds a sample event - currently just for speed of testing/debugging
       		else if(userInput.equals("addsample"))
       		{
       			calendar.addEvent(new Vevent("htms3l9k1rnnadhbfg1oqc46d8@google.com", "20160222T030130Z", "", "20150322T173000Z", "20150322T180000Z", "default event", new Geo("37.386013;-122.082932"), "PRIVATE"));
       		}
       		//print all events currently in the calendar
       		else if(userInput.equals("printallevents"))
       		{
       			System.out.println();
       			System.out.println("===================================");
       			calendar.printAllEvents();
       			System.out.println("===================================");
       			System.out.println();
       		}
       		//exit application
       		else if(userInput.equals("exit") || userInput.equals("quit"))
       		{
       			System.out.println("Exporting data to " + calendar.getFileName());
       			calendar.exportIcs();
       			exitProgram = true;
       		}
       		//If their is no known command for user input
       		else
       		{
       			System.out.println("I am not sure what you mean, perhaps type \"Commands\"?");
       		}
		}
	}

	//TODO
	//needs to be more user friendly, but it works
	private static Vevent addEventInterface()
	{
		Vevent  vevent = new Vevent();
		Scanner userInputScanner = new Scanner(System.in);
		String  temp =  "";
		boolean addGeo   = true;
		boolean addCLASS = true;

	//set event UID(Unique Identifier): get domain name or a domain literal IP address of the host on which      the identifier was created on the right-hand side of an "@", and      on the left-hand side, put a combination of the current calendar      date and time of day 

        
		do
		{
			//temp = getDomainName+ “@“+ getDTSTAMP;

		}
        while(!vevent.validUID(temp));
		vevent.setUID(temp);

        //set event DTSTAMP(Date-Time Stamp):In the case of an iCalendar object that doesn't specify a "METHOD"  property, this property is equivalent to the "LAST-MODIFIED"      property.
		do
		{
			//temp    = getUTCtimeFormat;
		}
		while(!vevent.validDTSTAMP(temp));
		vevent.setDTSTAMP(temp);

        //User sets event ORGANIZER
		do
		{
			System.out.println("\nEnter a valid ORGANIZER (or type \"cancel\" to cancel adding an event):\n");
			temp = userInputScanner.nextLine();
       		temp = temp.toLowerCase();
            if (temp.equals("cancel"))
            {
                return null;
            }
		}
		while(!vevent.validORGANIZER(temp));
		vevent.setORGANIZER(temp);

        //User sets event DTSTART
		do
		{
			System.out.println("\nEnter a valid DTSTART (or type \"cancel\" to cancel adding an event):\n");
			temp = userInputScanner.nextLine();
       		temp = temp.toLowerCase();
            if (temp.equals("cancel"))
            {
                return null;
            }
		}
		while(!vevent.validDTSTART(temp));
		vevent.setDTSTART(temp);

        //User sets event DTEND
		do
		{
			System.out.println("\nEnter a valid DTEND (or type \"cancel\" to cancel adding an event):\n");
			temp = userInputScanner.nextLine();
       		temp = temp.toLowerCase();
            if (temp.equals("cancel"))
            {
                return null;
            }
		}
		while(!vevent.validDTEND(temp));
		vevent.setDTEND(temp);

        //User sets event SUMMARY
		do
		{
			System.out.println("\nEnter a valid SUMMARY (or type \"cancel\" to cancel adding an event):\n");
			temp = userInputScanner.nextLine();
       		temp = temp.toLowerCase();
            if (temp.equals("cancel"))
            {
                return null;
            }
		}
		while(!vevent.validSUMMARY(temp));
		vevent.setSUMMARY(temp);

		//User sets event GEO (optional)
		do
		{
			System.out.println("\nEnter a valid GEO");
			System.out.println("or type \"cancel\" to cancel adding an event");
			System.out.println("or type \"pass\" to skip adding a GEO parameter to this event");
			System.out.println("A valid GEO consists of two decimal values seperated by a semi-colon");
			System.out.println("Example: 37.386013;-122.08293\n");
			temp = userInputScanner.nextLine();
			temp = temp.toLowerCase();

			//if the user no longer wants to add an event
			if (temp.equals("cancel"))
			{
				return null;
			}

			//if the user doesn't want to add a geo to the event
			if (temp.equals("pass"))
			{
				addGeo = false;
				break;
			}
		}
		while(!vevent.validGEO(temp));

		if(addGeo)
		{
			vevent.setGEO(temp);
		}

		//User sets event CLASS (optional)
		do
		{
			System.out.println("\nEnter a valid CLASS");
			System.out.println("or type \"cancel\" to cancel adding an event");
			System.out.println("or type \"pass\" to skip adding a CLASS parameter to this event");
			System.out.println("A valid CLASS is either PRIVATE, PUBLIC, or CLASSIFIED\n");
			temp = userInputScanner.nextLine();

			//if the user no longer wants to add an event
			if (temp.equals("cancel"))
			{
				return null;
			}

			//if the user doesn't want to add a CLASS to the event
			if (temp.equals("pass"))
			{
				addCLASS = false;
				break;
			}
		}
		while(!vevent.validCLASS(temp));

		//if the user hasn't passed on adding a class, set the class vairable in the vevent
		if(addCLASS)
		{
			vevent.setCLASS(temp);
		}

		return vevent;
	}

	/*
		Returns all possible commands that a user could enter
	*/
	private static void printAllCommands()
	{
		System.out.println();
		System.out.println("=============COMMANDS==============");
		System.out.println("commands       - prints all known commands");
		System.out.println("exit           - exits the program with exporting");
		System.out.println("add            - add an event to current .ics file");
		System.out.println("printallevents - prints every event currently in the working calendar");
		System.out.println("===================================");
		System.out.println();
	}

	/*
		Returns true if input is .ics file, false if otherwise
		modified from http://stackoverflow.com/questions/3571223/how-do-i-get-the-file-extension-of-a-file-in-java
	*/
	private static boolean isIcsFile(String fileName)
	{
		String extension = "";
		int i = fileName.lastIndexOf('.');
		boolean myReturn;

		if (i > 0)
		{
		    extension = fileName.substring(i+1);
		}

		extension = extension.toLowerCase();

		if (extension.equals("ics"))
		{
			myReturn = true;
		}
		else
		{
			myReturn = false;
		}

		return myReturn;
	}
}
