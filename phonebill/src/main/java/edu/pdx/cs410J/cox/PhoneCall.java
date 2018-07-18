package edu.pdx.cs410J.cox;

import edu.pdx.cs410J.AbstractPhoneCall;

/**
 * This class represents a phone call. It is descended from the AbstractPhoneCall class.
 * It implements AbstractPhoneCall's abstract base classes:
 *    getCaller, getCallee, getStartTimeString, and getEndTimeString.
 * The following methods are specific to PhoneCall:
 *    setCaller, setCallee, setStartTimeString, setEndTimeString, verifyDateFormat, verifyTimeFormat, verifyPhoneNumberFormat,
 *    verifyAllDigits.
 * Variables:
 *      caller: A string containing the phone number of the person who initiated the call.
 *      callee: A string containing the phone number of the person who received the call.
 *      startTime: A string containing the date and time the call was received, separated by a space.
 *      endTime: A string containing the date and time the call was terminated, separated by a space.
 */

public class PhoneCall extends AbstractPhoneCall {
  private String caller = "";
  private String callee = "not implemented";
  private String startTime = "";
  private String endTime = "";

  @Override
  public String getCaller() {
    /**
     * This method returns the phone number of the person who initiated the call.
     * @return A string containing a phone number
     * */
    return caller;
  }

  @Override
  public String getCallee() {
    /**
     * This method returns the phone number of the person who received the call.
     * @return A string containing a phone number
     */
    return callee;
  }

  @Override
  public String getStartTimeString() {
    /**
     * This method returns the start date/time of the call.
     * @return A string containing a date and time separated by a space
     * */
    return this.startTime;
  }

  @Override
  public String getEndTimeString() {
    /**
     * This method returns the end date/time of the call.
     * @return A string containing a date and time separated by a space.
     */
    return this.endTime;
  }

  public Boolean setCaller(String phoneNum) {
    /**
     * This method receives a String containing a phone number, calls verifyPhoneNumberFormat to check
     * that it is formatted correctly, then returns true if it is formatted correctly, or false otherwise.
     * @param A string containing a phone number
     * @return A boolean value representing whether or not the phone number was formatted correctly, and whether caller was set.
     */
    boolean phoneNumberCorrect = verifyPhoneNumberFormat(phoneNum);
    if(phoneNumberCorrect == true) {
      this.caller = phoneNum;
    }
    return phoneNumberCorrect;
  }

  public Boolean setCallee(String phoneNum) {
    /**
     * This method receives a String containing a phone number, calls verifyPhoneNumberFormat to check
     * that it is formatted correctly, then returns true if it is formatted correctly, or false otherwise.
     * @param A string containing a phone number
     * @return A boolean value representing whether or not the phone number was formatted correctly, and whether caller was set.
     */
    boolean phoneNumberCorrect = verifyPhoneNumberFormat(phoneNum);
    if(phoneNumberCorrect == true) {
      this.callee = phoneNum;
    }
    return phoneNumberCorrect;
  }

  public Boolean setStartTimeString(String date, String time) {
    /**
     * This method receives a string containing a date, and a string containing a time. It verifies that both are formatteed
     * correctly, then sets startTime to be the date followed by a space and the time.
     * @param A string containing a date, and a string containing a time
     * @return A boolean representing whether or not the date and time were formatted correctly, and starTime was set.
     * */
    boolean dateCorrect = verifyDateFormat(date);
    boolean timeCorrect = verifyTimeFormat(time);
    if(dateCorrect == true && timeCorrect == true) {
      this.startTime = date + " " + time;
      return true;
    }
    return false;
  }

  public Boolean setEndTimeString(String date, String time) {
    /**
     * This method receives a string containing a date, and a string containing a time. It verifies that both are formatteed
     * correctly, then sets endTime to be the date followed by a space and the time.
      * @param A string containing a date, and a string containing a time
      * @return A boolean representing whether or not the date and time were formatted correctly, and endTime was set.
      * */
    boolean dateCorrect = verifyDateFormat(date);
    boolean timeCorrect = verifyTimeFormat(time);
    if(dateCorrect == true && timeCorrect == true) {
      this.endTime = date + " " + time;
      return true;
    }
    return false;
  }

  public boolean verifyDateFormat(String date) {
    /**
     * This method verifies that the received date adheres to the formatting rules. If it doesn't have one or two digits
     * in the month, separated by '/', one or two digits in the day, separated by '/', and 4 digits in the year, it does
     * not pass the formatting, and will return false. Otherwise, it is formatted correctly and will return true.
     * @param A string containing a date
     * @return True if the date is formatted correctly, false if it is not.
     * */
    String trimmedDate = date;
    int startIndex = 0;
    int endIndex = 0;

    endIndex = date.indexOf("/");
    if(endIndex < 0) {
      System.err.println("Missing at least one '/'.");
      return false;
    }
    trimmedDate = date.substring(startIndex, endIndex);

    // One or two characters (0-9) allowed for month
    if(verifyAllDigits(trimmedDate) != 1 && verifyAllDigits(trimmedDate) != 2) {
      System.err.println("Month must contain 1 or 2 digits.");
      return false;
    }

    startIndex = endIndex + 1;
    endIndex = date.indexOf("/", startIndex);
    if(endIndex < 0) {
      System.err.println("Missing at least one '/'.");
      return false;
    }
    trimmedDate = date.substring(startIndex, endIndex);

    // One or two characters (0-9) allowed for day
    if(verifyAllDigits(trimmedDate) != 1 && verifyAllDigits(trimmedDate) != 2) {
      System.err.println("Day must contain 1 or 2 digits.");
      return false;
    }

    startIndex = endIndex + 1;
    endIndex = date.length();
    if(endIndex < 0) {
      System.err.println("Missing a space between the date and time.");
      return false;
    }
    trimmedDate = date.substring(startIndex, endIndex);

    // Four characters (0-9) required for year
    if(verifyAllDigits(trimmedDate) != 4) {
      System.err.println("Year must contain 4 digits.");
      return false;
    }
    return true;
  }

  public boolean verifyTimeFormat(String time) {
    /**
     * This method verifies that the received time adheres to the correct formatting.It must have one or two digits, followed
     * by ':", followed by two digits. If it not pass the formatting, and will return false. Otherwise, it is formatted correctly and will return true.
     * @param A string containing a time
     * @return True if the time is formatted correctly, false if it is not.
     * */
    String trimmedDate = time;
    int startIndex = 0;
    int endIndex = 0;

    endIndex = time.indexOf(":");
    if(endIndex < 0) {
      System.err.println("Missing ':' between hours and minutes.");
      return false;
    }
    trimmedDate = time.substring(startIndex, endIndex);

    // One or two characters (0-9) allowed for hour
    if(verifyAllDigits(trimmedDate) != 1 && verifyAllDigits(trimmedDate) != 2) {
      System.err.println("Hour must contain 1 or 2 digits.");
      return false;
    }
    // One semicolon
    startIndex = endIndex + 1;
    endIndex = time.length();
    trimmedDate = time.substring(startIndex, endIndex);

    // Two characters (0-9) required for minutes
    if(verifyAllDigits(trimmedDate) != 2) {
      System.err.println("Minutes must contain 1 or 2 digits.");
      return false;
    }
    return true;
  }

  public boolean verifyPhoneNumberFormat (String phone) {
    /**
     * This method verifies that the received phone number adheres to the correct formatting.It must have three digits, followed
     * by '-", followed by three digits, followed by '-', followed by 4 digits. If it not pass the formatting, and will return false. Otherwise, it is formatted correctly and will return true.
     * @param A string containing a phone number
     * @return True if the phone number is formatted correctly, false if it is not.
     * */
    String trimmedPhone = phone;
    int startIndex = 0;
    int endIndex = 0;

    endIndex = phone.indexOf("-");
    if(endIndex < 0) {
      System.err.println("Missing at least one '-'.");
      return false;
    }
    trimmedPhone = phone.substring(startIndex, endIndex);

    // Three characters (0-9)
    if(verifyAllDigits(trimmedPhone) != 3) {
      System.err.println("Phone number is not formatted like ###-###-####.");
      return false;
    }

    startIndex = endIndex + 1;
    endIndex = phone.indexOf("-", startIndex);
    if(endIndex < 0) {
      System.err.println("Missing at least one '-'.");
      return false;
    }
    trimmedPhone = phone.substring(startIndex, endIndex);

    // Three characters (0-9)
    if(verifyAllDigits(trimmedPhone) != 3) {
      System.err.println("Phone number is not formatted like ###-###-####.");
      return false;
    }

    startIndex = endIndex + 1;
    endIndex = phone.length();
    if(endIndex < 0) {
      System.err.println("Missing at least one '-'.");
      return false;
    }
    trimmedPhone = phone.substring(startIndex, endIndex);

    // Four characters (0-9)
    if(verifyAllDigits(trimmedPhone) != 4) {
      System.err.println("Phone number is not formatted like ###-###-####.");
      return false;
    }
    return true;
  }

  public int verifyAllDigits (String test) {
    /**
     * This method tests to make sure that all the characters in a string are digits (0-9)
     * @param A string that will be tested
     * @return The number of characters, or 0 if a non digit character is found.
     */
    int numDigits = 0;
    for(int i = 0; i < test.length(); ++i) {
      if(!Character.isDigit(test.charAt(i))) {
        return 0;
      }
      ++numDigits;
    }
    return numDigits;
  }
}
