package edu.pdx.cs410J.cox;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.io.File;
import java.util.Arrays;

/**
 * The main class for the CS410J Phone Bill Project 2
 */

public class Project2 {

    /**
     * Facilitates the programs processes. Receives an array of command line arguments, searches for the -print, -README,
     * and -textFile options, then sends the remaining arguments to check formatting in be set in the PhoneCall class.
     * If the user uses -textFile and includes a file name, the initial PhoneBill will be loaded with it's contents (assiming
     * the file is not blank or not formatted incorrectly). At the end of the program, the PhoneBill will be written to the
     * named file, including any new PhoneCalls that have been added to it.
     */
    public static void main(String[] args) {
        PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
        PhoneBill bill = new PhoneBill();

        final String README = "\n\n -- README --\n" +
                "\nProject 2 implemented by Lily Cox \n" +
                "\nThis program creates phone bills from phone call information." +
                "\nAll phone call information is entered in the form: customer callerNumber calleeNumber startTime endTime" +
                "\nInformation can be brought in from a file included in the command line arguments and from the command line itself" +
                "\nCall information in the command line may be preceded by the options: -print, -README, or -textFile followed by the name of the file." +
                "\nEach piece of data is then checked to make sure it adheres to the expected formatting." +
                "\nIf any information is formatted incorrectly, the program will report the error." +
                "\nIf it passes, however, and the -README option is not used, the information will be added to the instance of the" +
                "\nPhoneCall class, and that instance added to the specified customer's bill." +
                "\nIf a file is used but the names in the file and command line do not match, an error will be reported\n";

        int numOptionArgs = 4; // The max number of argument positions that the available options could take up
        int numOptionsUsed = 0; // The number of argument positions actually used by user options
        Boolean useFile = false; // If the user includes the '-textFile' option with a valid file name
        String billFileName = ""; // File access for the user included file name
        Boolean printCall = false; // If the user includes the '-print' option
        TextParser readFile = new TextParser();

        if(args.length == 0) { // Checks that command line arguments have been provided
            System.err.println("Missing command line arguments");
            System.exit(1);
        }

        for(int i = 0; i < numOptionArgs; ++i) { // Loop through to find how many options are used so we know how many positions to offset the phone call information
            if(args[i].equals("-README")) { // If user wants readme, even if there are other options, only print readme and exit
                printReadme(README);
            }
            else if(args[i].equals("-print")) { // If user wants to print the phonecalls in the bill, make the Boolean indicating this choice, true
                if(printCall == true) {
                    System.err.println("-print option used multiple times.");
                    System.exit(1);
                }
                printCall = true;
                ++numOptionsUsed;
            }
            else if(args[i].equals("-textFile")) { // If user wants to read/write to an existing text file, check that the file exists, make the Boolean indicating this choice, true
                if(useFile == true) {
                    System.err.println("-textFile option used multiple times.");
                    System.exit(1);
                }
                File billAccess = new File(args[i+1]); // Try to access the requested file
                billFileName = args[i+1];
                useFile = true;
                numOptionsUsed +=2; // It counts for two array positions since one is '-textFile' and the other is the name of the file
            }
        }

        if(args.length > (numOptionsUsed + 7)) { // If too many phone call arguments were included
            System.err.println("Too many phone call arguments.");
            System.exit(1);
        }

        if(args.length != (numOptionsUsed + 7)) { // Since there are seven total pieces of data expected for the PhoneCall, there must be that many arguments (if no options are used)
            System.err.println("Does not contain all required phone call arguments.");
            System.exit(1);
        }

        if(useFile == true) { // If the user wants to read/write from a file, read the information, parse it, and insert it into an instance of PhoneBill, which is returned
            readFile.setFileName(billFileName);
            bill = readFile.parse();
        }

        if(bill.getCustomer().equals(args[numOptionsUsed]) == false && useFile == true && bill.getCustomer().equals("notset") == false ) { // If the name in the file doesn't match the name of the name of the PhoneCall to add from the command line
            System.err.println("Received customer name does not match customer name in the text file.");
            System.exit(1);
        }

        // Set the PhoneCall information, and add it to the PhoneBill
        bill.setCustomer(args[numOptionsUsed]); // Set the name of the customer in the bill
        Boolean caller = call.setCaller(args[numOptionsUsed + 1]); // Test formatting of the phone # and set it
        Boolean callee = call.setCallee(args[numOptionsUsed + 2]); // Test formatting of the phone # and set it
        Boolean start = call.setStartTimeString(args[numOptionsUsed + 3], args[numOptionsUsed + 4]); // Test formatting of the date and time and set it
        Boolean end = call.setEndTimeString(args[numOptionsUsed + 5], args[numOptionsUsed + 6]); // Test formatting of the date and time and set it
        bill.addPhoneCall(call); // Add the added completed phonecall to the phone bill

        if((caller && callee && start && end)) { // If it passes all formatting tests, and all variables are set
            if((useFile == true && readFile.passedFormatting() == true) || useFile == false) {
                if (printCall == true) {
                    System.out.println("Bill for: " + bill.getCustomer());
                    for (PhoneCall c : bill.getPhoneCalls()) {
                        printPhonecall(c);
                    }
                }

                if (useFile == true) { // If the user wanted to read/write from a file, write all information in the PhoneBill to the file, including added PhoneCall
                    TextDumper saveFile = new TextDumper();
                    saveFile.setFileName(billFileName);
                    saveFile.dump(bill);
                }
            }
        }
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
}
