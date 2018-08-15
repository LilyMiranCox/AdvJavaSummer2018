package edu.pdx.cs410J.cox.client;

import edu.pdx.cs410J.PhoneBillDumper;

import java.io.*;

/**
 * This class will accept the name of a file, then if it is valid, will print a human readable bill to the file. If a file
 * is '-' the pretty form will be written to the console, and if the file doesn't exist it will be created then written to.
 */

public class PrettyPrinter implements PhoneBillDumper <PhoneBill> {
    private String fileName = ""; // A string containing the name of the text file which will be accessed

    /**
     * This method accepts a PhoneBill, and creates a string with all of its information formatted so that
     * it is easily human readable for the customer.
     * @param bill A completed PhoneBill that will have its customer name and PhoneCall information copied into
     * a string, then written to the specified file, or written to the console if filename is '-'.
     */
    public void dump (PhoneBill bill) {
   /*     if(this.fileName.equals("-") == true) { // If the user wants to write the pretty bill to the console
            this.printOut(bill, "Bill for: "+bill.getCustomer());
            return;
        }

        // If the file is not a text file.
        if(this.fileName.charAt(this.fileName.length()-4) != '.' || this.fileName.charAt(this.fileName.length()-3) != 't' || this.fileName.charAt(this.fileName.length()-2) != 'x' || this.fileName.charAt(this.fileName.length()-1) != 't') {
            System.err.println("\nIncluded pretty file is not a text file.");
            System.exit(1);
        }
        File temp = new File(this.fileName);
        if(temp.exists() == false) { // If the text file does not exist, create it, and write the info to it
            try {
                temp.createNewFile();
                try {
                    Writer billAccess = new FileWriter(temp, false);
                    String toWrite = "         Bill for: " + bill.getCustomer() + System.getProperty("line.separator") + "_________________________________";
                    for(PhoneCall c: bill.getPhoneCalls()) {
                        toWrite += System.getProperty("line.separator") + System.getProperty("line.separator") + " Start time: " + c.getPrettyStartString() + System.getProperty("line.separator") + "   End time: " + c.getPrettyEndString() + System.getProperty("line.separator") + "   Duration: " + (c.getEndTime().getTime() - c.getStartTime().getTime())/60000 + " minutes" + System.getProperty("line.separator")+ "       From: " + c.getCaller() + System.getProperty("line.separator") + "         To: " + c.getCallee();
                    }
                    billAccess.write(toWrite);
                    billAccess.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                // If the text file exists, write the pretty bill to it.
                Writer billAccess = new FileWriter(temp, false);
                String toWrite = "         Bill for: " + bill.getCustomer() + System.getProperty("line.separator") + "_________________________________";
                for(PhoneCall c: bill.getPhoneCalls()) {
                    toWrite += System.getProperty("line.separator") +  System.getProperty("line.separator") + " Start time: " + c.getPrettyStartString() + System.getProperty("line.separator") + "   End time: " + c.getPrettyEndString()  + System.getProperty("line.separator") + "   Duration: " + (c.getEndTime().getTime() - c.getStartTime().getTime())/60000 + " minutes" + System.getProperty("line.separator")+ "       From: " + c.getCaller() + System.getProperty("line.separator") + "         To: " + c.getCallee();
                }
                billAccess.write(toWrite);
                billAccess.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    /**
     * This method accepts a String containing the name of a text file to be accessed.
     * @param name A string which contains the name of a text file that will be accessed.
     */
    public void setFileName (String name) {
        this.fileName = name;
    }

    /**
     * This method prints out the pretty form of the bill to the console.
     * @param bill An instance of PhoneBill that is filled with a bill's information that will be written to the console.
     */
    public Boolean printOut (PhoneBill bill, String title) {
        Boolean displayed = false;
        System.out.println("         "+title + "\n_________________________________");
        for(PhoneCall c: bill.getPhoneCalls()) {
            System.out.println("\n\n Start time: " + c.getPrettyStartString() + "\n   End time: " + c.getPrettyEndString() + "\n   Duration: " + (c.getEndTime().getTime() - c.getStartTime().getTime())/60000 + " minutes" + "\n       From: " + c.getCaller() + "\n         To: " + c.getCallee());
            displayed = true;
        }
        return displayed;
    }

    public String printOutString (PhoneBill bill, String title) {
        String prettyBill ="         "+title + "\n_________________________________";
        for(PhoneCall c: bill.getPhoneCalls()) {
       //     prettyBill += System.getProperty("line.separator")+ System.getProperty("line.separator")+" Start time: " + c.getPrettyStartString() + System.getProperty("line.separator")+ "   End time: " + c.getPrettyEndString() + System.getProperty("line.separator") + "   Duration: " + (c.getEndTime().getTime() - c.getStartTime().getTime())/60000 + " minutes" + System.getProperty("line.separator") + "       From: " + c.getCaller() + System.getProperty("line.separator") + "         To: " + c.getCallee();
            prettyBill += ("\n\n Start time: " + c.getPrettyStartString() + "\n   End time: " + c.getPrettyEndString() + "\n   Duration: " + (c.getEndTime().getTime() - c.getStartTime().getTime())/60000 + " minutes" + "\n       From: " + c.getCaller() + "\n         To: " + c.getCallee());
        }

        return prettyBill;
    }

 /*   public Boolean printOutToPrintWriter(PrintWriter pw, PhoneBill bill, String title) {
        Boolean displayed = false;
        pw.println("         "+ title);
        pw.println("_________________________________");
        for(PhoneCall c: bill.getPhoneCalls()) {
            pw.println();
            pw.println(" Start time: " + c.getPrettyStartString());
            pw.println("   End time: " + c.getPrettyEndString());
            pw.println("   Duration: " + (c.getEndTime().getTime() - c.getStartTime().getTime())/60000 + " minutes");
            pw.println("       From: " + c.getCaller());
            pw.println("         To: " + c.getCallee());
            displayed = true;
        }
        return displayed;
    }*/
}
