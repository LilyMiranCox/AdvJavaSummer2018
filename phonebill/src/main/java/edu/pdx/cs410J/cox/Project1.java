package edu.pdx.cs410J.cox;

import edu.pdx.cs410J.AbstractPhoneBill;
import java.util.Arrays;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {



  public static void main(String[] args) {
    PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    PhoneBill bill = new PhoneBill();

    final String README = "\n\n -- README --\n" +
            "\nProject 1 implemented by Lily Cox \n" +
            "\nThis program creates phone bills from phone call information." +
            "\nAll phone call information is entered from the command line in the form: customer callerNumber calleeNumber startTime endTime" +
            "\nThis information may be preceded by the options: -print or -README" +
            "\nEach piece of data is then checked to make sure it adheres to the expected formatting." +
            "\nIf any information is formatted incorrectly, the program will report the error." +
            "\nIf it passes, however, and the -README option is not used, the information will be added to the instance of the" +
            "\nPhoneCall class, and that instance added to the specified customer's bill.\n";

    int printOption = 0;

    if(args.length == 0) { // Make sure that arguments have been provided
      System.err.println("Missing command line arguments");
      System.exit(1);
    }

    if(args[0].equals("-README") || args[1].equals("-README")) { // If the -README option is selected
      System.out.println(README);
      System.exit(0);
    }

    if(args[0].equals("-print") || args[1].equals("-print")) { // If the -print option is selected
      printOption = 1;
    }

    bill.setCustomer(args[printOption]); // Set the name of the customer in the bill
    Boolean caller = call.setCaller(args[printOption + 1]); // Test formatting of the phone # and set it
    Boolean callee = call.setCallee(args[printOption + 2]); // Test formatting of the phone # and set it
    Boolean start = call.setStartTimeString(args[printOption + 3], args[printOption + 4]); // Test formatting of the date and time and set it
    Boolean end = call.setEndTimeString(args[printOption + 5], args[printOption + 6]); // Test formatting of the date and time and set it
    bill.addPhoneCall(call); // Add the added completed phonecall to the phone bill

    if(caller && callee && start && end && printOption == 1) { // If it passes all formatting tests, and all variables are set, print the phone call information
      System.out.println(call.toString());
    }
  }
}