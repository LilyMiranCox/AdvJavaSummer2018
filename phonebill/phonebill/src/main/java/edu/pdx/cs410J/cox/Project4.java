package edu.pdx.cs410J.cox;

import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Phone Bill server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String... args) {
        PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
        PhoneBill bill = new PhoneBill();

        final String README = "\n\n -- README --\n" +
                "\nProject 3 implemented by Lily Cox \n" +
                "\nThis program creates phone bills from phone call information." +
                "\nAll phone call information is entered in the form: customer callerNumber calleeNumber startTime endTime" +
                "\nInformation can be brought in from a file included in the command line arguments and from the command line itself" +
                "\nCall information in the command line may be preceded by the options: -print, -README, or -textFile or -pretty followed by the name of the file." +
                "\nEach piece of data is then checked to make sure it adheres to the expected formatting." +
                "\nIf any information is formatted incorrectly, the program will report the error." +
                "\nIf it passes, however, and the -README option is not used, the information will be added to the instance of the" +
                "\nPhoneCall class, and that instance added to the specified customer's bill." +
                "\nIf a file is used but the names in the file and command line do not match, an error will be reported." +
                "\nIf -pretty is used, and a text file is specified, an easy to read bill will be written to that file." +
                "\nIf '-' us used instead of a file, the easy to read bill will be written to the console instead.";

        String hostName = null;
        String portString = null;
        String word = null;
        String definition = null;

        int numOptionArgs = 14;
        int numOptionsUsed = 0;
        Boolean printCall = false;
        String searchName = null;
        Date searchStart = new Date();
        Date searchEnd = new Date();

        if(args.length == 0) { // Checks that command line arguments have been provided
            System.err.println("Missing command line arguments");
            System.exit(1);
        }

        for(int i = 0; i < numOptionArgs && i < args.length; ++i) {
            if (args[i].equals("-README")) {
                printReadme(README);
            }
            else if (args[i].equals("-host")) {
                hostName = args[i+1];
                numOptionsUsed+=2;
            }
            else if (args[i].equals("-port")) {
                portString = args[i+1];
                numOptionsUsed+=2;
            }
            else if (args[i].equals("-print")) {
                if(printCall == true) {
                    System.err.println("-print option used multiple times.");
                    System.exit(1);
                }
                printCall = true;
                ++numOptionsUsed;
            }
            else if (args[i].equals("-search")) {
                searchName = args[i+1];
                searchStart = stringToDate(args[i+2], args[i+3], args[i+4]);
                searchEnd = stringToDate(args[i+5], args[i+6], args[i+7]);
                numOptionsUsed+=8;
            }
        }

        if(args.length > (numOptionsUsed + 9)) { // If too many phone call arguments were included
            System.err.println("Too many phone call arguments.");
            System.exit(1);
        }

        if(args.length != (numOptionsUsed + 9)) { // Since there are seven total pieces of data expected for the PhoneCall, there must be that many arguments (if no options are used)
            System.err.println("Does not contain all required phone call arguments.");
            System.exit(1);
        }

  //      if(bill.getCustomer().equals(args[numOptionsUsed]) == false && useFile == true && bill.getCustomer().equals("notset") == false ) { // If the name in the file doesn't match the name of the name of the PhoneCall to add from the command line
    //        System.err.println("Received customer name does not match customer name in the text file.");
      //      System.exit(1);
        //}

        // Set the PhoneCall information, and add it to the PhoneBill
        bill.setCustomer(args[numOptionsUsed]); // Set the name of the customer in the bill
        Boolean caller = call.setCaller(args[numOptionsUsed + 1]); // Test formatting of the phone # and set it
        Boolean callee = call.setCallee(args[numOptionsUsed + 2]); // Test formatting of the phone # and set it
        Boolean start = call.setStartTimeString(args[numOptionsUsed + 3], args[numOptionsUsed + 4], args[numOptionsUsed + 5]); // Test formatting of the date and time and set it
        Boolean end = call.setEndTimeString(args[numOptionsUsed + 6], args[numOptionsUsed + 7], args[numOptionsUsed + 8]); // Test formatting of the date and time and set it
        bill.addPhoneCall(call); // Add the added completed phonecall to the phone bill

     /*   for (String arg : args) {
            if (hostName == null) {
                hostName = arg;

            } else if ( portString == null) {
                portString = arg;

            } else if (word == null) {
                word = arg;

            } else if (definition == null) {
                definition = arg;

            } else {
                usage("Extraneous command line argument: " + arg);
            }
        }*/

        if (hostName == null) {
            usage( MISSING_ARGS );

        } else if ( portString == null) {
            usage( "Missing port" );
        }

        int port;
        try {
            port = Integer.parseInt( portString );
            
        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        String message = null;
  //      try {
            if((caller && callee && start && end)) { // If it passes all formatting tests, and all variables are set
                if(searchName != null) {
                    PhoneBill searchedCalls = bill.searchCalls(searchName, searchStart, searchEnd);

                    PrettyPrinter newPrettyPrint = new PrettyPrinter();
                    newPrettyPrint.printOut(searchedCalls);
                }
                else if (printCall == true) {
                    System.out.println("Bill for: " + bill.getCustomer());
                    for (PhoneCall c : bill.getPhoneCalls()) {
                        printPhonecall(c);
                    }
                }
            }


          /*  if (word == null) {
                // Print all word/definition pairs
                Map<String, String> dictionary = client.getAllDictionaryEntries();
                StringWriter sw = new StringWriter();
                Messages.formatDictionaryEntries(new PrintWriter(sw, true), dictionary);
                message = sw.toString();

            } else if (definition == null) {
                // Print all dictionary entries
                message = Messages.formatDictionaryEntry(word, client.getDefinition(word));

            } else {
                // Post the word/definition pair
                client.addDictionaryEntry(word, definition);
                message = Messages.definedWordAs(word, definition);
            }*/

 //       } catch ( IOException ex ) {
   //         error("While contacting server: " + ex);
     //       return;
       // }

 //       System.out.println(message);

        System.exit(0);
    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     * @param code The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode( int code, HttpRequestHelper.Response response )
    {
        if (response.getCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                                response.getCode(), response.getContent()));
        }
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java Project4 host port [word] [definition]");
        err.println("  host         Host of web server");
        err.println("  port         Port of web server");
        err.println("  word         Word in dictionary");
        err.println("  definition   Definition of word");
        err.println();
        err.println("This simple program posts words and their definitions");
        err.println("to the server.");
        err.println("If no definition is specified, then the word's definition");
        err.println("is printed.");
        err.println("If no word is specified, all dictionary entries are printed");
        err.println();

        System.exit(1);
    }

    /**
     * This method accepts a String containing a README statement, prints the statement to the console, then exits.
     * @param README A String containing a README statement
     */
    public static void printReadme (String README) {
        System.out.println(README);
        System.exit(0);
    }

    /**
     * This method receives a PhoneCall, and prints out its contents to the console
     * @param call An instance of PhoneCall that will have it's information printed to the console
     */
    public static void printPhonecall (PhoneCall call) {

        System.out.println(call.toString());
    }

    /**
     * This method receives a string containing a date, and a string containing a time. It verifies that both are formatteed
     * correctly, then sets startTime to be the date followed by a space and the time.
     * @param date A string containing a date
     * @param time A string containing a time
     * @param period A string containing either 'am' or 'pm'
     * @return A boolean representing whether or not the date and time were formatted correctly, and starTime was set.
     * */
    public static Date stringToDate (String date, String time, String period) {
        PhoneCall tempCall = new PhoneCall();
        boolean dateCorrect = tempCall.verifyDateFormat(date);
        boolean timeCorrect = tempCall.verifyTimeFormat(time, period);
        Date newDate = new Date();
        if(dateCorrect == true && timeCorrect == true) {
            DateFormat newFormat = new SimpleDateFormat("M/d/yyyy h:mm aaa");
            try {
                newDate = newFormat.parse(date + " " + time + " " + period);
                DateFormat.getDateInstance(DateFormat.SHORT).format(newDate);
                DateFormat.getTimeInstance(DateFormat.SHORT).format(newDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return newDate;
        }
        System.err.println("The entered -search times are not formatted correctly.");
        System.exit(1);
        return newDate;
    }
}