package edu.pdx.cs410J.cox.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The client-side interface to the phone bill service
 */
public interface PhoneBillServiceAsync {

  /**
   * Return the current date/time on the server
   */
  void getPhoneBill(AsyncCallback<PhoneBill> async);

  /**
   * Always throws an exception so that we can see how to handle uncaught
   * exceptions in GWT.
   */
  void throwUndeclaredException(AsyncCallback<Void> async);

  /**
   * Always throws a declared exception so that we can see GWT handles it.
   */
  void throwDeclaredException(AsyncCallback<Void> async);

  /**
   * This method will add a call to the customer's bill.
   * @param customer A string containing the name of a customer.
   * @param call A PhoneCall to be added to the customer's bill.
   * @param async To be used in the callback in PhoneBillGwt
   */
  void setPhoneBill(String customer, PhoneCall call, AsyncCallback<Boolean> async);

  /**
   * This method retrieves the phonebill of a customer.
   * @param customer A string containing the name of a customer.
   * @param async To be useed in the callback in PhoneBillGwt
   */
  void getBill (String customer, AsyncCallback<PhoneBill> async);

}
