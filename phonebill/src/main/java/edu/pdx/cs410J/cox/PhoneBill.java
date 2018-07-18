package edu.pdx.cs410J.cox;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents a phone bill. It is descended from the AbstractPhoneBill class.
 * It implements AbstractPhoneBill's abstract classes :
 *      addPhoneCall, getCustomer, and getPhoneCalls.
 * The following classes are specific to PhoneBill (not inherited):
 *      setCustomer.
 * Variables:
 *      calls: Contains all of the calls made from this account.
 *      customer: Contains the name of the customer.
 */

public class PhoneBill extends AbstractPhoneBill<PhoneCall>{
    private Collection<PhoneCall> calls = new ArrayList<>();
    private String customer = "notset";

    @Override
    public void addPhoneCall(PhoneCall call) {
        /**
         * This Method accepts a PhoneCall object, which it adds to the existing collection of PhoneCalls.
         * @param A PhoneCall object which will be added to the collection variable, calls.
         * */
        this.calls.add(call);
    }

    @Override
    public String getCustomer() {
        /**
         * This method returns string containing the name of the customer whose PhoneBill it is.
         * @return A string which contains the name of the customer who pays the bill.
         */
        return this.customer;
    }

    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        /**
         * This method returns all of the calls that have taken place for this customer.
         * @return A collection of PhoneCall instances which hold the information regarding the calls that have taken place.
         */
        return this.calls;
    }

    public void setCustomer(String name) {
        /**
         * This method accepts a string containing the name of the customer whose PhoneBill it is.
         * @param A string containing the name of the customer who pays the bill.*/
        this.customer = name;
    }
}
