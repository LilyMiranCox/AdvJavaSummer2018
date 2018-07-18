package edu.pdx.cs410J.cox;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class deals with reading and parsing PhoneCall information from text files, and calling the correct methods
 * to insert the parsed information into PhoneCalls, and each PhoneCall into a PhoneBill.
 */

public class TextParser implements PhoneBillParser <PhoneBill> {
    private Boolean formattedCorrectly = false;
    public String fileName = "";

    /**
     * This method accepts a String containing the name of an existing text file, checks that it exists, reads and
     * formats the customer's name, then reads phonecall information and calls setCall to add it to the PhoneBill
     * @return A PhoneBill filled with all the information (customer name and all PhoneCalls) in the text file,
     * assuming that the file was formatted correctly.
     */
    public PhoneBill parse () {

        if(this.fileName.charAt(this.fileName.length()-4) != '.' || this.fileName.charAt(this.fileName.length()-3) != 't' || this.fileName.charAt(this.fileName.length()-2) != 'x' || this.fileName.charAt(this.fileName.length()-1) != 't') {
            System.err.println("Included file is not a text file.");
            System.exit(1);
        }

        File billAccess = new File(this.fileName);
        if(billAccess.exists() == false) { // If the attempted file doesn't exist, try to create it
            try {
                billAccess.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Scanner billFile = null;
        try {
            billFile = new Scanner(billAccess); // A Scanner object to read in lines from the file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PhoneBill bill = new PhoneBill();
        if(billFile.hasNextLine() == false) { // If the file is empty
            System.err.println("Selected file is empty.");
            System.exit(1);
        }

        String custName = billFile.nextLine();

        if(custName.length()<5 || custName.charAt(0) != '!' || custName.charAt(1) != '!' || custName.charAt(custName.length()-1) != '!' || custName.charAt(custName.length()-2) != '!') {
            this.reportBadFormat();
        }
        custName = custName.replace("!","");
        bill.setCustomer(custName);

        while(billFile.hasNextLine() == true) { // For each line of PhoneCall data, call setCall to parse it and add it to the instance of PhoneBill
            setCall(bill, billFile.nextLine());
        }

        return bill;
    }

    /**
     * This method receives a String containing the name of the file to be accessed
     * @param name A string containing the name of the file to be accessed, which is used to set the variable fileName.
     */
    public void setFileName (String name) {
        this.fileName = name;
    }

    /**
     * This method receives a string containing PhoneCall data that it parses and adds to a PhoneBill if it is formatted correctly.
     * @param bill An instance of PhoneBill to have a PhoneCall added to it.
     * @param callInfo A String containing PhoneCall information, that will be parsed and it's information added to a PhoneCall.
     * @return The received Phonebill instance, with the new PhoneCall data added to it.
     */
    public PhoneBill setCall (PhoneBill bill, String callInfo) {
        PhoneCall call = new PhoneCall();
        String currentInfo = "";

        // Get the caller number
        int startIndex = 0;
        int endIndex = callInfo.indexOf(" ");
        if(endIndex < 0) {
            this.reportBadFormat();
        }
        currentInfo = callInfo.substring(startIndex, endIndex);
        Boolean caller = call.setCaller(currentInfo); // Test formatting of the phone # and set it

        // Get the callee number
        startIndex = endIndex + 1;
        endIndex = callInfo.indexOf(" ", startIndex);
        if(endIndex < 0) {
            this.reportBadFormat();
        }
        currentInfo = callInfo.substring(startIndex, endIndex);
        Boolean callee = call.setCallee(currentInfo); // Test formatting of the phone # and set it

        // Get the start date
        startIndex = endIndex + 1;
        endIndex = callInfo.indexOf(" ", startIndex);
        if(endIndex < 0) {
            this.reportBadFormat();
        }
        currentInfo = callInfo.substring(startIndex, endIndex);

        // Get the start time
        startIndex = endIndex + 1;
        endIndex = callInfo.indexOf(" ", startIndex);
        if(endIndex < 0) {
            this.reportBadFormat();
        }
        String time = callInfo.substring(startIndex, endIndex);
        Boolean start = call.setStartTimeString(currentInfo, time); // Test formatting of the date and time and set it

        // Get the end date
        startIndex = endIndex + 1;
        endIndex = callInfo.indexOf(" ", startIndex);
        if(endIndex < 0) {
            this.reportBadFormat();
        }
        currentInfo = callInfo.substring(startIndex, endIndex);

        // Get the end time
        startIndex = endIndex + 1;
        endIndex = callInfo.length();
        if(endIndex < 0) {
            this.reportBadFormat();
        }
        time = callInfo.substring(startIndex, endIndex);

        Boolean end = call.setEndTimeString(currentInfo, time); // Test formatting of the date and time and set it
        bill.addPhoneCall(call); // Add the added completed phonecall to the phone bill

        if(!caller || !callee || !start || !end) { // If the formatting for all are correct
            this.reportBadFormat();
        }

        this.formattedCorrectly = true; // Indicate that it has passed all formatting tests
        return bill;
    }

    /**
     * This method returns a Boolean indicating whether or not the text file was formatted correctly.
     * @return A Boolean indicacting whether or not the text file was formatted correctly.
     */
    public Boolean passedFormatting () {
        return this.formattedCorrectly;
    }

    /**
     * This method prints an error indicating that the user named a file that is not formatted correctly, then exits.
     */
    public void reportBadFormat () {
        System.err.println("Included text file is not formatted correctly");
        System.exit(1);
    }
}
