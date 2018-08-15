package edu.pdx.cs410J.cox.client;

import edu.pdx.cs410J.AbstractPhoneCall;

//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.Date;
import com.google.gwt.i18n.shared.DateTimeFormat;

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

public class PhoneCall extends AbstractPhoneCall implements Comparable <PhoneCall>{
  private String caller = "";
  private String callee = "not implemented";
  private Date callStart = new Date();
  private Date callEnd = new Date();

  public PhoneCall () {

  }

  /**
   * This method overrrides Comparable's method, compare to. It compares the times and caller numbers, and returns 0
   * if they are the same, or 1 or -1 to indicate inequal.
   * @param secondCall A call that is compared to this instances of PhoneCall.
   * @return 0 if they are equal, 1 if the first is greater, or -1 if the second is greater.
   */
  @Override
  public int compareTo(PhoneCall secondCall) {
    if(this.getStartTime().after(secondCall.getStartTime())) {
      return 1;
    }
    else if(this.getStartTime().before(secondCall.getStartTime())) {
      return -1;
    }
    else  {
      int compared = this.caller.compareTo(secondCall.getCaller());
      if(compared > 0) {
        return 1;
      }
      else if(compared < 0) {
        return -1;
      }
      return 0;
    }
  }

  /**
   * This method returns the start time of the call.
   * @return A Date containing the start time of the call.
   */
  @Override
  public Date getStartTime () {
    return callStart;
  }

  /**
   * This method returns the end time of the call
   * @return A Date containing the end time fo the call.
   */
  @Override
  public Date getEndTime () {
    return callEnd;
  }

  /**
   * This method returns the phone number of the person who initiated the call.
   * @return A string containing a phone number
   * */
  @Override
  public String getCaller() {
    return caller;
  }

  /**
   * This method returns the phone number of the person who received the call.
   * @return A string containing a phone number
   */
  @Override
  public String getCallee() {
    return callee;
  }

  /**
   * This method returns the start date/time of the call.
   * @return A string containing a date and time separated by a space
   * */
  @Override
  public String getStartTimeString() {
    DateTimeFormat newFormat = DateTimeFormat.getFormat("M/d/yyyy h:mm aaa");
    return newFormat.format(this.callStart);
  }

  /**
   * This method returns a string containing a more human readable version of the start time.
   * @return A string containing a more human readable version of the start time.
   */
  public String getPrettyStartString() {
    DateTimeFormat newFormat = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm aaa");
    return newFormat.format(this.callStart);
  }

  /**
   * This method returns the end date/time of the call.
   * @return A string containing a date and time separated by a space.
   */
  @Override
  public String getEndTimeString() {
    DateTimeFormat newFormat = DateTimeFormat.getFormat("M/d/yyyy h:mm aaa");
    return newFormat.format(this.callEnd);
  }

  /**
   * This method returns a more human readable version of the end time.
   * @return A string containing a more human readable version of the end time.
   */
  public String getPrettyEndString() {
    DateTimeFormat newFormat = DateTimeFormat.getFormat("MM/dd/yyyy hh:mm aaa");
    return newFormat.format(this.callEnd);
  }

  /**
   * This method receives a String containing a phone number, calls verifyPhoneNumberFormat to check
   * that it is formatted correctly, then returns true if it is formatted correctly, or false otherwise.
   * @param phoneNum A string containing a phone number
   * @return A boolean value representing whether or not the phone number was formatted correctly, and whether caller was set.
   */
  public Boolean setCaller(String phoneNum) {
    boolean phoneNumberCorrect = verifyPhoneNumberFormat(phoneNum);
    if(phoneNumberCorrect == true) {
      this.caller = phoneNum;
    }
    return phoneNumberCorrect;
  }

  /**
   * This method receives a String containing a phone number, calls verifyPhoneNumberFormat to check
   * that it is formatted correctly, then returns true if it is formatted correctly, or false otherwise.
   * @param phoneNum A string containing a phone number
   * @return A boolean value representing whether or not the phone number was formatted correctly, and whether caller was set.
   */
  public Boolean setCallee(String phoneNum) {
    boolean phoneNumberCorrect = verifyPhoneNumberFormat(phoneNum);
    if(phoneNumberCorrect == true) {
      this.callee = phoneNum;
    }
    return phoneNumberCorrect;
  }

  /**
   * This method receives a string containing a date, and a string containing a time. It verifies that both are formatteed
   * correctly, then sets startTime to be the date followed by a space and the time.
   * @param date A string containing a date
   * @param time A string containing a time
   * @param period A string containing either 'am' or 'pm'
   * @return A boolean representing whether or not the date and time were formatted correctly, and starTime was set.
   * */
  public Boolean setStartTimeString(String date, String time, String period) {
    boolean dateCorrect = verifyDateFormat(date);
    boolean timeCorrect = verifyTimeFormat(time, period);
    if(dateCorrect == true && timeCorrect == true) {
      DateTimeFormat newFormat = DateTimeFormat.getFormat("M/d/yyyy h:mm aaa");
      callStart = newFormat.parse(date + " " + time + " " + period);
      callStart = newFormat.parse(date + " " + time + " " + period);
      newFormat.format(this.callStart);
      return true;
    }
    return false;
  }

  /**
   * This method receives a string containing a date, and a string containing a time. It verifies that both are formatteed
   * correctly, then sets endTime to be the date followed by a space and the time.
   * @param date A string containing a date
   * @param time A string containing a time
   * @param period A string containing either 'am' or 'pm'
   * @return A boolean representing whether or not the date and time were formatted correctly, and endTime was set.
   * */
  public Boolean setEndTimeString(String date, String time, String period) {
    boolean dateCorrect = verifyDateFormat(date);
    boolean timeCorrect = verifyTimeFormat(time, period);
    if(dateCorrect == true && timeCorrect == true) {
      DateTimeFormat newFormat = DateTimeFormat.getFormat("M/d/yyyy h:mm aaa"); // Set the correct pattern
      this.callEnd = newFormat.parse(date + " " + time + " " + period);
      if(this.callEnd.before(this.callStart) == true) {
 //       System.err.println("Call end time is before call start time.");
//        System.exit(1);
      }
      newFormat.format(this.callEnd);

      return true;
    }
    return false;
  }

  /**
   * This method verifies that the received date adheres to the formatting rules. If it doesn't have one or two digits
   * in the month, separated by '/', one or two digits in the day, separated by '/', and 4 digits in the year, it does
   * not pass the formatting, and will return false. Otherwise, it is formatted correctly and will return true.
   * @param date A string containing a date
   * @return True if the date is formatted correctly, false if it is not.
   * */
  public boolean verifyDateFormat(String date) {
    String trimmedDate = date;
    int startIndex = 0;
    int endIndex = 0;

  if(date == null) {
      return false;
  }

    endIndex = date.indexOf("/");
    if(endIndex < 0) {
     // System.err.println("Missing at least one '/'.");
      return false;
    }
    trimmedDate = date.substring(startIndex, endIndex);

    // One or two characters (0-9) allowed for month
    if(verifyAllDigits(trimmedDate) != 1 && verifyAllDigits(trimmedDate) != 2) {
    //  System.err.println("Month must contain 1 or 2 digits.");
      return false;
    }

    startIndex = endIndex + 1;
    endIndex = date.indexOf("/", startIndex);
    if(endIndex < 0) {
    //  System.err.println("Missing at least one '/'.");
      return false;
    }
    trimmedDate = date.substring(startIndex, endIndex);

    // One or two characters (0-9) allowed for day
    if(verifyAllDigits(trimmedDate) != 1 && verifyAllDigits(trimmedDate) != 2) {
    //  System.err.println("Day must contain 1 or 2 digits.");
      return false;
    }

    startIndex = endIndex + 1;
    endIndex = date.length();
    if(endIndex < 0) {
    //  System.err.println("Missing a space between the date and time.");
      return false;
    }
    trimmedDate = date.substring(startIndex, endIndex);

    // Four characters (0-9) required for year
    if(verifyAllDigits(trimmedDate) != 4) {
    //  System.err.println("Year must contain only 4 digits.");
      return false;
    }
    return true;
  }

  /**
   * This method verifies that the received time adheres to the correct formatting.It must have one or two digits, followed
   * by ':", followed by two digits. If it not pass the formatting, and will return false. Otherwise, it is formatted correctly and will return true.
   * @param time A string containing a time
   * @return True if the time is formatted correctly, false if it is not.
   * */
  public boolean verifyTimeFormat(String time, String period) {
    String trimmedDate = time;
    int startIndex = 0;
    int endIndex = 0;

    if(time == null || period == null) {
        return false;
    }

    endIndex = time.indexOf(":");
    if(endIndex < 0) {
    //  System.err.println("Missing ':' between hours and minutes.");
      return false;
    }
    trimmedDate = time.substring(startIndex, endIndex);

    // One or two characters (0-9) allowed for hour
    if(verifyAllDigits(trimmedDate) != 1 && verifyAllDigits(trimmedDate) != 2) {
    //  System.err.println("Hour must contain only 1 or 2 digits.");
      return false;
    }
    // One semicolon
    startIndex = endIndex + 1;
    endIndex = time.length();
    trimmedDate = time.substring(startIndex, endIndex);

    // Two characters (0-9) required for minutes
    if(verifyAllDigits(trimmedDate) != 2) {
    //  System.err.println("Minutes must contain only 1 or 2 digits.");
      return false;
    }

    // am or pm included
    if(period.toLowerCase().equals("am") == false && period.toLowerCase().equals("pm") == false) {
    //  System.err.println("Must include either 'am' or 'pm'.");
      return false;
    }

    return true;
  }

  /**
   * This method verifies that the received phone number adheres to the correct formatting.It must have three digits, followed
   * by '-", followed by three digits, followed by '-', followed by 4 digits. If it not pass the formatting, and will return false. Otherwise, it is formatted correctly and will return true.
   * @param phone A string containing a phone number
   * @return True if the phone number is formatted correctly, false if it is not.
   * */

  public boolean verifyPhoneNumberFormat (String phone) {
    String trimmedPhone = phone;
    int startIndex = 0;
    int endIndex = 0;

      if(phone == null) {
          return false;
      }

    endIndex = phone.indexOf("-");
    if(endIndex < 0) {
    //  System.err.println("Missing at least one '-'.");
      return false;
    }
    trimmedPhone = phone.substring(startIndex, endIndex);

    // Three characters (0-9)
    if(verifyAllDigits(trimmedPhone) != 3) {
    //  System.err.println("Phone number is not formatted like ###-###-####.");
      return false;
    }

    startIndex = endIndex + 1;
    endIndex = phone.indexOf("-", startIndex);
    if(endIndex < 0) {
    //  System.err.println("Missing at least one '-'.");
      return false;
    }
    trimmedPhone = phone.substring(startIndex, endIndex);

    // Three characters (0-9)
    if(verifyAllDigits(trimmedPhone) != 3) {
    //  System.err.println("Phone number is not formatted like ###-###-####.");
      return false;
    }

    startIndex = endIndex + 1;
    endIndex = phone.length();
    if(endIndex < 0) {
    //  System.err.println("Missing at least one '-'.");
      return false;
    }
    trimmedPhone = phone.substring(startIndex, endIndex);

    // Four characters (0-9)
    if(verifyAllDigits(trimmedPhone) != 4) {
    //  System.err.println("Phone number is not formatted like ###-###-####.");
      return false;
    }
    return true;
  }

  /**
   * This method tests to make sure that all the characters in a string are digits (0-9)
   * @param test A string that will be tested
   * @return The number of characters, or 0 if a non digit character is found.
   */
  public int verifyAllDigits (String test) {
    int numDigits = 0;
    for(int i = 0; i < test.length(); ++i) {
      if(!Character.isDigit(test.charAt(i))) {
        return 0;
      }
      ++numDigits;
    }
    return numDigits;
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
      DateTimeFormat newFormat = DateTimeFormat.getFormat("M/d/yyyy h:mm aaa");
      newDate = newFormat.parse(date + " " + time + " " + period);
      newFormat.format(newDate);
      return newDate;
    }
   // System.err.println("The entered -search times are not formatted correctly.");
  //  System.exit(1);
    return null;
  }
}
