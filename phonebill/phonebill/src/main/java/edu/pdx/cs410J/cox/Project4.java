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
                "\nProject 4 implemented by Lily Cox \n" +
                "\nThis program creates phone bills from phone call information." +
                "\nAll phone call information is entered in the form: customer callerNumber calleeNumber startTime endTime" +
                "\nCall information in the command line may be preceded by the options: -print, -README, or -search followed by just a customer name, start time, and end time." +
                "\nThe options -host and -port are required to use the program"+
                "\nEach piece of data is then checked to make sure it adheres to the expected formatting." +
                "\nIf any information is formatted incorrectly, the program will report the error." +
                "\nIf it passes, however, and the -README option is not used, the information will be added to the instance of the" +
                "\nPhoneCall class, and that instance added to the specified customer's bill." +
                "\nIf only a customer name is provided, all of the biils for that customer will be pretty printed to the command line." +
                "\nFrom the url, if you provide just a customer name, that customer's bill will be pretty printed." +
                "\nIf a customer name, start time, and end time are provided, all calls from the customer's bill that fall" +
                "\nwithin that time period will be pretty printed." +
                "\nif the customer name, caller number, callee number, start time, and end time are provided, a new call" +
                "\nwill be created for the customer's bill. If the customer does not already exist, it will be created.";

        String hostName = null;
        String portString = null;

        int numOptionArgs = 14;
        int numOptionsUsed = 0;
        Boolean printCall = false;
        String searchName = null;
        Date searchStart = new Date();
        Date searchEnd = new Date();

        if (args.length == 0) { // Checks that command line arguments have been provided
            System.err.println("Missing command line arguments");
            System.exit(1);
        }

        for (int i = 0; i < numOptionArgs && i < args.length; ++i) {
            if (args[i].equals("-README")) {
                printReadme(README);
            } else if (args[i].equals("-host")) {
                hostName = args[i + 1];
                numOptionsUsed += 2;
            } else if (args[i].equals("-port")) {
                portString = args[i + 1];
                numOptionsUsed += 2;
            } else if (args[i].equals("-print")) {
                if (printCall == true) {
                    System.err.println("-print option used multiple times.");
                    System.exit(1);
                }
                printCall = true;
                ++numOptionsUsed;
            } else if (args[i].equals("-search")) {
                searchName = args[i + 1];

                if(args.length < numOptionsUsed + 8) {
                    System.err.println("'-search' requires a customer name, start time, and end time.");
                    System.exit(1);
                }

                searchStart = call.stringToDate(args[i + 2], args[i + 3], args[i + 4]);
                searchEnd = call.stringToDate(args[i + 5], args[i + 6], args[i + 7]);

                if (searchStart.after(searchEnd)) {
                    System.err.println("The '-search' start time is after the end time.");
                    System.exit(1);
                }
                numOptionsUsed += 8;
            }
        }

        if (hostName == null) {
            usage(MISSING_ARGS);

        } else if (portString == null) {
            usage("Missing port");
        }

        int port;
        try {
            port = Integer.parseInt(portString);

        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

        if (args.length > (numOptionsUsed + 9)) { // If too many phone call arguments were included
            System.err.println("Too many phone call arguments.");
            System.exit(1);
        }

        if (args.length != (numOptionsUsed + 9) && args.length != (numOptionsUsed + 1) && searchName == null) { // Since there are seven total pieces of data expected for the PhoneCall, there must be that many arguments (if no options are used)
            System.err.println("Does not contain all required phone call arguments.");
            System.exit(1);
        }

        PhoneBillRestClient client = new PhoneBillRestClient(hostName, port);

        // If something was searched or only the customer name is included - get the bill from the server
        if (searchName != null || args.length == numOptionsUsed + 1) {
            try {
                String allCalls;
                if(searchName != null) {
                    allCalls = client.getAllPhoneCallEntries(searchName);
                }
                else {
                    bill.setCustomer(args[numOptionsUsed]);
                    allCalls = client.getAllPhoneCallEntries(args[numOptionsUsed]);
                }

                String [] lines = allCalls.split("\n"); // Get each line of the pretty printed bill
                for(int i = 3; i < lines.length; i+=6) { // For each call, parse it and put it into a new phonecall
                    PhoneCall newCall = new PhoneCall();

                    String startTime = lines[i].replace(" Start time: ", "");
                    String[] startPieces = startTime.split("\\s+");
                    newCall.setStartTimeString(startPieces[0], startPieces[1], startPieces[2]);

                    String endTime = lines[i+1].replace("   End time: ","");
                    String[] endPieces = endTime.split("\\s+");
                    newCall.setEndTimeString(endPieces[0], endPieces[1], endPieces[2]);

                    String callerNum = lines[i+3].replace("       From: ","");
                    newCall.setCaller(callerNum);

                    String calleeNum = lines[i+4].replace("         To: ","");
                    newCall.setCallee(calleeNum);
                    bill.addPhoneCall(newCall);
                }

                if(searchName == null) { // If nothing was searched, then pretty print all of the bills
                    PrettyPrinter newPrettyPrint = new PrettyPrinter();
                    Boolean displayed = newPrettyPrint.printOut(bill, "Search results: ");
                    if (displayed == false) {
                        System.err.println("That customer does not exist");
                        System.exit(1);
                    }
                }
                else { // Get the calls that fall within the start and end times.
                    PhoneBill searchedCalls = bill.searchCalls(searchName, searchStart, searchEnd);

                    PrettyPrinter newPrettyPrint = new PrettyPrinter();
                    Boolean displayed = newPrettyPrint.printOut(searchedCalls, "Search results: ");
                    if (displayed == false) {
                        System.err.println("No calls fall within the included time period.");
                    }
                }
            }
            catch (IOException ex)  {
                error("While contacting server: " + ex);
                return;
            }

        }

        if(args.length != numOptionsUsed+1 && searchName == null) { // If a new call is to be added
            // Set the PhoneCall information, and add it to the PhoneBill
            bill.setCustomer(args[numOptionsUsed]); // Set the name of the customer in the bill
            Boolean caller = call.setCaller(args[numOptionsUsed + 1]); // Test formatting of the phone # and set it
            Boolean callee = call.setCallee(args[numOptionsUsed + 2]); // Test formatting of the phone # and set it
            Boolean start = call.setStartTimeString(args[numOptionsUsed + 3], args[numOptionsUsed + 4], args[numOptionsUsed + 5]); // Test formatting of the date and time and set it
            Boolean end = call.setEndTimeString(args[numOptionsUsed + 6], args[numOptionsUsed + 7], args[numOptionsUsed + 8]); // Test formatting of the date and time and set it

            if ((caller && callee && start && end)) { // If it passes all formatting tests, and all variables are set
                bill.addPhoneCall(call); // Add the added completed phonecall to the phone bill
                 if (printCall == true) {
                     System.out.println("Bill for: " + bill.getCustomer());
                     for (PhoneCall c : bill.getPhoneCalls()) {
                         printPhonecall(c);
                     }
                 }

                 try {
                     client.addPhoneCallEntry(bill.getCustomer(), call);
                 } catch (IOException ex) {
                     error("While contacting server: " + ex);
                     return;
                 }
            }
         }
        System.exit(0);
    }

    /**
     * Makes sure that the give response has the expected HTTP status code
     *
     * @param code     The expected status code
     * @param response The response from the server
     */
    private static void checkResponseCode(int code, HttpRequestHelper.Response response) {
        if (response.getCode() != code) {
            error(String.format("Expected HTTP code %d, got code %d.\n\n%s", code,
                    response.getCode(), response.getContent()));
        }
    }

    private static void error(String message) {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

    /**
     * Prints usage information for this program and exits
     *
     * @param message An error message to print
     */
    private static void usage(String message) {
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
     *
     * @param README A String containing a README statement
     */
    public static void printReadme(String README) {
        System.out.println(README);
        System.exit(0);
    }

    /**
     * This method receives a PhoneCall, and prints out its contents to the console
     *
     * @param call An instance of PhoneCall that will have it's information printed to the console
     */
    public static void printPhonecall(PhoneCall call) {

        System.out.println(call.toString());
    }
}