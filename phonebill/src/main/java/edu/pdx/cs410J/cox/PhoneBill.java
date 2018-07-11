package edu.pdx.cs410J.cox;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.util.ArrayList;
import java.util.Collection;

public class PhoneBill extends AbstractPhoneBill<PhoneCall>{
    private Collection<PhoneCall> calls = new ArrayList<>();
    private String customer = "notset";

    @Override
    public void addPhoneCall(PhoneCall call) {
        this.calls.add(call);
    }

    @Override
    public String getCustomer() {
        return this.customer;
    }

    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return this.calls;
    }

    public void setCustomer(String name) {
        this.customer = name;
    }
}
