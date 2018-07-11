package edu.pdx.cs410J.cox;

import edu.pdx.cs410J.AbstractPhoneCall;

public class PhoneCall extends AbstractPhoneCall {
  private String caller = "";
  private String callee = "not implemented";
  private String startTime = "";
  private String endTime = "";

  @Override
  public String getCaller() {
    //throw new UnsupportedOperationException("This method is not implemented yet");
    return caller;
  }

  @Override
  public String getCallee() {
  //  return "This method is not implemented yet";
    return callee;
  }

  @Override
  public String getStartTimeString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getEndTimeString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

  public void setCaller(String name) {
    this.caller = name;
  }

  public void setCallee(String name) {
    this.callee = name;
  }

  public void setStartTimeString(String initial) {
    this.startTime = initial;
  }

  public void setEndTimeStirng(String ending) {
    this.endTime = ending;
  }

  public void verifyTimeFormat(String time) {
    time = time.trim(); // Remove excess spaces before and after the date/time

    String trimmedDate = time;
    int startIndex = 0;
    int endIndex = 0;

    endIndex = time.indexOf("/");
    if(endIndex < 0) {
      throw new UnsupportedOperationException("Missing at least one '/'.");
    }
    trimmedDate = time.substring(startIndex, endIndex);

    // One or two characters (0-9) allowed for month
    if(verifyAllDigits(trimmedDate) != 1 && verifyAllDigits(trimmedDate) != 2) {
      throw new UnsupportedOperationException("Month must contain 1 or 2 digits.");
    }

    startIndex = endIndex + 1;
    endIndex = time.indexOf("/", startIndex);
    if(endIndex < 0) {
      throw new UnsupportedOperationException("Missing at least one '/'.");
    }
    trimmedDate = time.substring(startIndex, endIndex);

    // One or two characters (0-9) allowed for day
    if(verifyAllDigits(trimmedDate) != 1 && verifyAllDigits(trimmedDate) != 2) {
      throw new UnsupportedOperationException("Day must contain 1 or 2 digits.");
    }

    startIndex = endIndex + 1;
    endIndex = time.indexOf(" ", startIndex);
    if(endIndex < 0) {
      throw new UnsupportedOperationException("Missing a space between the date and time.");
    }
    trimmedDate = time.substring(startIndex, endIndex);

    // Four characters (0-9) required for year
    if(verifyAllDigits(trimmedDate) != 4) {
      throw new UnsupportedOperationException("Year must contain 4 digits.");
    }
    // Space (allow for more?)
    startIndex = endIndex + 1;
    endIndex = time.indexOf(":", startIndex);
    if(endIndex < 0) {
      throw new UnsupportedOperationException("Missing ':' between hours and minutes.");
    }
    trimmedDate = time.substring(startIndex, endIndex);

    // One or two characters (0-9) allowed for hour
    if(verifyAllDigits(trimmedDate) != 1 && verifyAllDigits(trimmedDate) != 2) {
      throw new UnsupportedOperationException("Hour must contain 1 or 2 digits.");
    }
    // One semicolon
    startIndex = endIndex + 1;
    endIndex = time.length();
    trimmedDate = time.substring(startIndex, endIndex);

    // Two characters (0-9) required for minutes
    if(verifyAllDigits(trimmedDate) != 2) {
      throw new UnsupportedOperationException("Minutes must contain 1 or 2 digits.");
    }
  }

  public void verifyPhoneNumberFormat (String phone) {
    phone = phone.trim(); // Remove excess spaces before and after the date/time

    String trimmedPhone = phone;
    int startIndex = 0;
    int endIndex = 0;

    endIndex = phone.indexOf("-");
    if(endIndex < 0) {
      throw new UnsupportedOperationException("Missing at least one '-'.");
    }
    trimmedPhone = phone.substring(startIndex, endIndex);

    // Three characters (0-9)
    if(verifyAllDigits(trimmedPhone) != 3) {
      throw new UnsupportedOperationException("Phone number is not formatted like ###-###-####.");
    }

    startIndex = endIndex + 1;
    endIndex = phone.indexOf("-", startIndex);
    if(endIndex < 0) {
      throw new UnsupportedOperationException("Missing at least one '-'.");
    }
    trimmedPhone = phone.substring(startIndex, endIndex);

    // Three characters (0-9)
    if(verifyAllDigits(trimmedPhone) != 3) {
      throw new UnsupportedOperationException("Phone number is not formatted like ###-###-####.");
    }

    startIndex = endIndex + 1;
    endIndex = phone.length();
    if(endIndex < 0) {
      throw new UnsupportedOperationException("Missing at least one '-'.");
    }
    trimmedPhone = phone.substring(startIndex, endIndex);

    // Four characters (0-9)
    if(verifyAllDigits(trimmedPhone) != 4) {
      throw new UnsupportedOperationException("Phone number is not formatted like ###-###-####.");
    }
  }

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
}
