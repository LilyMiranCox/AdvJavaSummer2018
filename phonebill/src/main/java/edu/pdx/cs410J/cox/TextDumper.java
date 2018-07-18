package edu.pdx.cs410J.cox;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class TextDumper {
    public void saveToFile (String setTo, PhoneBill bill) {
        File temp = new File(setTo);
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
}
