package edu.pdx.cs410J.cox;

import edu.pdx.cs410J.PhoneBillDumper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * This class deals with writing a completed PhoneBill to a text file
 */

public class TextDumper implements PhoneBillDumper <PhoneBill>{
    private String fileName = ""; // A string containing the name of the text file which will be accessed

    /**
     * This method accepts a PhoneBill, and creates a string with all of its information formatted so that
     * TextParser can read it in correctly.
     * @param bill A completed PhoneBill that will have its customer name and PhoneCall information copied into
     * a string, then written to the specified file
     */
    public void dump (PhoneBill bill) {
        File temp = new File(this.fileName);
        if(temp.exists() == false) {
            System.exit(1);
        }
        try {
            Writer billAccess = new FileWriter(temp, false);
            String toWrite = "!!" + bill.getCustomer() + "!!";
            for(PhoneCall c: bill.getPhoneCalls()) {
                toWrite += System.getProperty("line.separator") + c.getCaller() + " " + c.getCallee() + " " + c.getStartTimeString() + " " + c.getEndTimeString();
            }
            billAccess.write(toWrite);
            billAccess.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method accepts a String containing the name of a text file to be accessed.
     * @param name A string which contains the name of a text file that will be accessed.
     */
    public void setFileName (String name) {
        this.fileName = name;
    }
}
