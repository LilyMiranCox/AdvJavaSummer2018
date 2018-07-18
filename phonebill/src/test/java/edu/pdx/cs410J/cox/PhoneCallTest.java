package edu.pdx.cs410J.cox;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link PhoneCall} class.
 */
public class PhoneCallTest {

 /* @Test(expected = UnsupportedOperationException.class)
  public void getStartTimeStringNeedsToBeImplemented() {
    PhoneCall call = new PhoneCall();
    call.getStartTimeString();
  }*/

  @Test
  public void initiallyAllPhoneCallsHaveTheSameCallee() {
    PhoneCall call = new PhoneCall();
    assertThat(call.getCallee(), containsString("not implemented"));
  }

  @Test
  public void forProject1ItIsOkayIfGetStartTimeReturnsNull() {
    PhoneCall call = new PhoneCall();
    assertThat(call.getStartTime(), is(nullValue()));
  }

  @Test
  public void timeFormatCorrect () {
    PhoneCall call = new PhoneCall();
    call.verifyTimeFormat("19:30");
    call.verifyTimeFormat("1:03");
  }

  @Test
  public void dateFormatCorrect () {
    PhoneCall call = new PhoneCall();
    call.verifyDateFormat("1/15/2018");
    call.verifyDateFormat("01/2/2018");
  }

 /* @Test
  public void timeFormatMonthIncorrect () {
    PhoneCall call = new PhoneCall();
    call.verifyTimeFormat("001/15/2018 19:39");
  }

  @Test
  public void timeFormatDayIncorrect () {
    PhoneCall call = new PhoneCall();
    call.verifyTimeFormat("01/015/2018 19:39");
  }

  @Test
  public void timeFormatYearIncorrect () {
    PhoneCall call = new PhoneCall();
    call.verifyTimeFormat("01/15/218 19:39");
  }

  @Test
  public void timeFormatHourIncorrect () {
    PhoneCall call = new PhoneCall();
    call.verifyTimeFormat("01/15/2018 109:39");
  }

  @Test
  public void timeFormatMinuteIncorrect () {
    PhoneCall call = new PhoneCall();
    call.verifyTimeFormat("01/15/2018 19:3");
  }

  @Test
  public void timeFormatLetterIncluded () {
    PhoneCall call = new PhoneCall();
    call.verifyTimeFormat("0b/15/2018 19:3");
  }*/
 @Test
  public void phoneFormatCorrect () {
   PhoneCall call = new PhoneCall();
   call.verifyPhoneNumberFormat("503-684-9232");
 }

  @Test
  public void phoneFormatIncorrect () {
    PhoneCall call = new PhoneCall();
    //call.verifyPhoneNumberFormat("503-684-9p32");
    //call.verifyPhoneNumberFormat("503684-9232");
    //call.verifyPhoneNumberFormat("503-684-92-32");
  }
}
