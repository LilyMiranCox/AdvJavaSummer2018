package edu.pdx.cs410J.cox.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.cox.client.PhoneBill;
import edu.pdx.cs410J.cox.client.PhoneCall;
import edu.pdx.cs410J.cox.client.PhoneBillService;

import java.util.HashMap;
import java.util.Map;

/**
 * The server-side implementation of the Phone Bill service
 */
public class PhoneBillServiceImpl extends RemoteServiceServlet implements PhoneBillService
{
  private Map<String, PhoneBill> bills = new HashMap<>();

  @Override
  public PhoneBill getPhoneBill() {
    PhoneBill phonebill = new PhoneBill();
    phonebill.addPhoneCall(new PhoneCall());
    return phonebill;
  }

  @Override
  public void throwUndeclaredException() {
    throw new IllegalStateException("Expected undeclared exception");
  }

  @Override
  public void throwDeclaredException() throws IllegalStateException {
    throw new IllegalStateException("Expected declared exception");
  }

  /**
   * Log unhandled exceptions to standard error
   *
   * @param unhandled
   *        The exception that wasn't handled
   */
  @Override
  protected void doUnexpectedFailure(Throwable unhandled) {
    unhandled.printStackTrace(System.err);
    super.doUnexpectedFailure(unhandled);
  }

  /**
   * This method will save a new PhoneCall to a PhoneBill.
   * @param customer A string containing the name of the customer.
   * @param call A new PhoneCall to add to an existing/newly created PhoneBill.
   * @return Returns true if the call was added successfully, and false if the call
   * already exists in the bill.
   */
  @Override
  public Boolean setPhoneBill(String customer, PhoneCall call) {
    PhoneBill bill = this.bills.get(customer);
    if(bill == null) {
      bill = new PhoneBill();
      bill.setCustomer(customer);
      this.bills.put(customer, bill);
    }

    if(bill.callExistsInBill(call) == true) {
      return false;
    }

    bill.addPhoneCall(call);
    return true;
  }

  /**
   * This method will retrieve the bill of a given customer
   * @param customer A string containing the name of a customer.
   * @return Returns the bill of the customer if they exist, and null if they do not.
   */
  @Override
  public PhoneBill getBill (String customer) {
    return this.bills.get(customer);
  }
}
