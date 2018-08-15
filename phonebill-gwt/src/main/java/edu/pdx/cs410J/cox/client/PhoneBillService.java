package edu.pdx.cs410J.cox.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * A GWT remote service that returns a dummy Phone Bill
 */
@RemoteServiceRelativePath("phoneBill")
public interface PhoneBillService extends RemoteService {

  /**
   * Returns the a dummy Phone Bill
   */
  public PhoneBill getPhoneBill();


  /**
   * Always throws an undeclared exception so that we can see GWT handles it.
   */
  void throwUndeclaredException();

  /**
   * Always throws a declared exception so that we can see GWT handles it.
   */
  void throwDeclaredException() throws IllegalStateException;

  /**
   * This method accepts the name of a customer, and a new call to add.
   * @param customer A string containing the name of a customer.
   * @param call A PhoneCall to add to the customer's bill.
   * @return Returns true if the call was added successfully, and false if it is a duplicate.
   */
  Boolean setPhoneBill(String customer, PhoneCall call);

  /**
   * This method returns the bill of the received customer.
   * @param customer  A string containing the name of a customer to retrieve a bill for.
   * @return The PhoneBill of the customer, or null if the customer does not exist.
   */
  public PhoneBill getBill (String customer);
}
