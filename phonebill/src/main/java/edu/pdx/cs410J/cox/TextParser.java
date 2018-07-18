package edu.pdx.cs410J.cox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class deals with reading and parsing PhoneCall information from text files, and calling the correct methods to insert the parsed information into PhoneCalls, and each PhoneCall into a PhoneBill.
 */

public class TextParser {
    private Boolean formattedCorrectly = false;
    public PhoneBill getFromFile(String toRead) {
        /**
         * This method accepts a String containing the name of an existing text file, checks that it exists, reads and formats the customer's name, then reads phonecall information and calls setCall to add it to the PhoneBill
         * @param toRead A String that contains the name of an existing text file.
         * @returns A PhoneBill filled with all the information (customer name and all PhoneCalls) in the text file, assuming that the file was formatted correctly.
         */

        File billAccess = new File(toRead);
        Scanner billFile = null;
        try {
            billFile = new Scanner(billAccess);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PhoneBill bill = new PhoneBill();
        if(billFile.hasNextLine() == false) return new PhoneBill(); // If the file is empty
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

    public PhoneBill setCall (PhoneBill bill, String callInfo) {
        PhoneCall call = new PhoneCall();
        String currentInfo = "";

        int startIndex = 0;
        int endIndex = callInfo.indexOf(" ");
        if(endIndex < 0) {
            this.reportBadFormat();
        }
        currentInfo = callInfo.substring(startIndex, endIndex);

        Boolean caller = call.setCaller(currentInfo); // Test formatting of the phone # and set it

        startIndex = endIndex + 1;
        endIndex = callInfo.indexOf(" ", startIndex);
        if(endIndex < 0) {
            this.reportBadFormat();
        }
        currentInfo = callInfo.substring(startIndex, endIndex);
        Boolean callee = call.setCallee(currentInfo); // Test formatting of the phone # and set it

        startIndex = endIndex + 1;
        endIndex = callInfo.indexOf(" ", startIndex);
        if(endIndex < 0) {
            this.reportBadFormat();
        }
        currentInfo = callInfo.substring(startIndex, endIndex);

        startIndex = endIndex + 1;
        endIndex = callInfo.indexOf(" ", startIndex);
        if(endIndex < 0) {
            this.reportBadFormat();
        }
        String time = callInfo.substring(startIndex, endIndex);
        Boolean start = call.setStartTimeString(currentInfo, time); // Test formatting of the date and time and set it

        startIndex = endIndex + 1;
        endIndex = callInfo.indexOf(" ", startIndex);
        if(endIndex < 0) {
            this.reportBadFormat();
        }
        currentInfo = callInfo.substring(startIndex, endIndex);

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

        this.formattedCorrectly = true;
        return bill;
    }

    public Boolean passedFormatting () {
        return this.formattedCorrectly;
    }

    public void reportBadFormat () {
        System.err.println("Included text file is not formatted correctly");
        System.exit(1);
    }
}
