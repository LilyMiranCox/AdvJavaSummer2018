package edu.pdx.cs410J.cox;

import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Integration test that tests the REST calls made by {@link PhoneBillRestClient}
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PhoneBillRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private PhoneBillRestClient newPhoneBillRestClient() {
    int port = Integer.parseInt(PORT);
    return new PhoneBillRestClient(HOSTNAME, port);
  }

  @Test
  public void test0RemoveAllDictionaryEntries() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    client.removeAllDictionaryEntries();
  }

  @Test
  public void test1EmptyServerContainsNoDictionaryEntries() throws IOException {
/*    PhoneBillRestClient client = newPhoneBillRestClient();
    Map<String, String> dictionary = client.getAllDictionaryEntries();
    assertThat(dictionary.size(), equalTo(0));*/
  }

 /* @Test
  public void test2DefineOneWord() throws IOException {
    PhoneBillRestClient client = newPhoneBillRestClient();
    String testCustomer = "Lily";
    String testCaller = "503-688-0563";
    String testCallee = "503-684-9232";
    PhoneCall call = new PhoneCall();
    call.setCaller(testCaller);
    call.setCallee(testCallee);
    call.setStartTimeString("1/3/2018", "8:00", "PM");
    call.setEndTimeString("1/3/2018", "8:00", "PM");
    System.out.println("2 WOW");
    client.addPhoneCallEntry(testCustomer, call);
    System.out.println("3 WOW");

    String definition = client.getAllPhoneCallEntries(testCustomer);
    System.out.println("4 WOW");
    assertThat(definition, equalTo("Lily 503-688-0563 503-684-9232 1/3/2018 8:00 PM 1/3/2018 8:02 PM"));
    System.out.println("5 WOW");
  }*/

  @Test
  public void test4MissingRequiredParameterReturnsPreconditionFailed() throws IOException {
  /*  PhoneBillRestClient client = newPhoneBillRestClient();
    HttpRequestHelper.Response response = client.postToMyURL();
    assertThat(response.getContent(), containsString(Messages.missingRequiredParameter("customer")));
    assertThat(response.getCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));*/
  }

}
