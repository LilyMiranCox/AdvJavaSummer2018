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

  @Override
  public PhoneBill getBill (String customer) {
    return this.bills.get(customer);
  }
}
