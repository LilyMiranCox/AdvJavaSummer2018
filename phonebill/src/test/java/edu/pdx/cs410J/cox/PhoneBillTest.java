package edu.pdx.cs410J.cox;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class PhoneBillTest {
    @Test
    public void customerSetCorrectly () {
        PhoneBill bill = new PhoneBill();
        assertThat(bill.getCustomer(), is("notset"));
    }

    @Test
    public void customerSetIncorrectly () {
        PhoneBill bill = new PhoneBill();
        assertThat(bill.getCustomer(), containsString(""));
    }
}
