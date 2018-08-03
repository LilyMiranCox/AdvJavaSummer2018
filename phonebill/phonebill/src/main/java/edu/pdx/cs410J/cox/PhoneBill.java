package edu.pdx.cs410J.cox;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.*;

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

    /**
     * This Method accepts a PhoneCall object, which it adds to the existing collection of PhoneCalls.
     * @param call A PhoneCall object which will be added to the collection variable, calls.
     */
    @Override
    public void addPhoneCall(PhoneCall call) {
        if(this.calls.isEmpty() == true) { // If there are no calls in the PhoneBill yet
            this.calls.add(call);
            List callList = (List) this.calls;
            Collections.sort(callList);
            this.calls = callList;
        }
        else { // If there are already calls in the PhoneBill
            for (PhoneCall c : this.calls) { // Compare the potential new call to see if it already exists in the collection
                if (c.compareTo(call) == 0) { // If it already exists, don't add it
                    System.err.println("Call at  "+ c.getStartTimeString() + "  by  "+ c.getCaller()+"  is a duplicate and will not be added.");
                    return;
                }
            }
            // New call doesn't already exist in the bill, so add it
            this.calls.add(call);
            List callList = (List) this.calls;
            Collections.sort(callList);
            this.calls = callList;
            return;
        }
    }

    /**
     * This method returns string containing the name of the customer whose PhoneBill it is.
     * @return A string which contains the name of the customer who pays the bill.
     */
    @Override
    public String getCustomer() {
        return this.customer;
    }

    /**
     * This method returns all of the calls that have taken place for this customer.
     * @return A collection of PhoneCall instances which hold the information regarding the calls that have taken place.
     */
    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return this.calls;
    }

    /**
     * This method accepts a string containing the name of the customer whose PhoneBill it is.
     * @param name A string containing the name of the customer who pays the bill.
     */
    public void setCustomer(String name) {

        this.customer = name;
    }

    /**
     * This method will search through all of the PhoneCalls and create a subset bill containing all calls that
     * fall between the included start and end times.
     * @param customer A string containing the name of the expected bill's customer.
     * @param start A date containing the start time limit.
     * @param end A date containing the end time limit.
     * @return A bill containing the subset of calls within the start and end times.
     */
    public PhoneBill searchCalls (String customer, Date start, Date end) {
        PhoneBill searchSubset = new PhoneBill();

        searchSubset.setCustomer(customer);

        for (PhoneCall c : this.calls) { // Compare the potential new call to see if it already exists in the collection
            if((c.getStartTime().equals(start) || c.getStartTime().after(start)) && (c.getEndTime().equals(end) || c.getEndTime().before(end))){
                searchSubset.addPhoneCall(c);
            }
        }

        return searchSubset;
    }
}
