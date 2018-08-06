package edu.pdx.cs410J.cox;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>PhoneBill</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class PhoneBillServlet extends HttpServlet
{
  //  static final String WORD_PARAMETER = "word";
  //  static final String DEFINITION_PARAMETER = "definition";
    static final String CUSTOMER_PARAM = "customer";
    static final String CALLER_PARAM = "callerNumber";
    static final String CALLEE_PARAM = "calleeNumber";
    static final String START_PARAM = "startTime";
    static final String END_PARAM = "endTime";

    private final Map<String, String> dictionary = new HashMap<>();
    private Map<String, PhoneBill> bills = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the call of the
     * customer specified in the "customer" HTTP parameter to the HTTP response.  If the
     * "customer" parameter is not specified, all of the entries in the Phonebill
     * are written to the HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String customer = getParameter(CUSTOMER_PARAM, request );
        String startTime = getParameter(START_PARAM, request);
        String endTime = getParameter(END_PARAM, request);
        String caller = getParameter(CALLER_PARAM, request);
        String callee = getParameter(CALLEE_PARAM, request);

        if (customer == null) {
            PrintWriter pw = response.getWriter();
            pw.println("Missing customer in the url.");
            pw.flush();
            return;
        }

        PhoneBill bill = this.bills.get(customer);
        PrintWriter pw = response.getWriter();
        PrettyPrinter prettyPrint = new PrettyPrinter();

        if (bill != null) {
            if(startTime != null && endTime != null && customer !=null) {
                String[] startPieces = startTime.split("\\s+");
                String[] endPieces = endTime.split("\\s+");

                if(caller != null && callee != null) { // If the user is adding a new phonecall to an existing customer
                    PhoneCall call = new PhoneCall();
                    Boolean callerSet = call.setCaller(caller);
                    Boolean calleeSet = call.setCallee(callee);
                    Boolean startSet = call.setStartTimeString(startPieces[0], startPieces[1], startPieces[2]);
                    Boolean endSet = call.setEndTimeString(endPieces[0], endPieces[1], endPieces[2]);

                    if ((callerSet && calleeSet && startSet && endSet)) {
                        bill.addPhoneCall(call);
                        pw.println(customer + "'s call has been added to their bill");
                    }
                    else {
                        pw.println("The entered phone call data is formatted incorrectly.");
                    }
                }
                else { // If the user only included a customer name, start, and end times, search for and display calls within that time period
                    if(caller == null && callee != null) {
                        pw.println("Missing caller in the url.");
                    }
                    else if(callee == null && caller != null) {
                        pw.println("Missing callee in the url.");
                    }
                    else {

                        Date searchStart = PhoneCall.stringToDate(startPieces[0], startPieces[1], startPieces[2]);
                        Date searchEnd = PhoneCall.stringToDate(endPieces[0], endPieces[1], endPieces[2]);

                        if (searchStart.after(searchEnd)) { // If the startTime falls after the endTime
                            pw.println();
                            pw.println("The start time is after the end time.");
                        }
                        PhoneBill searchedCalls = bill.searchCalls(customer, searchStart, searchEnd);
                        Boolean displayed = prettyPrint.printOutToPrintWriter(pw, searchedCalls, "Search results for: " + bill.getCustomer());

                        if (displayed == false) { // If no calls fall within the included time period
                            pw.println();
                            pw.println("No calls fall within the included time period.");
                        }
                    }
                }
            }
            else { // If the user only provides the customer's name, pretty print the customer's bill

                if(customer == null) {
                    pw.println("Missing customer in the url.");
                }
                else {
                    if(startTime == null && endTime != null) {
                        pw.println("Missing startTime in the url.");
                    }
                    else if(endTime == null && startTime != null) {
                        pw.println("Missing endTime in the url.");
                    }
                    else
                        prettyPrint.printOutToPrintWriter(pw, bill, "Bill for: " + bill.getCustomer());
                }
            }

            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            // If the user is creating a phone call for a NEW bill customer
            if(customer != null && startTime != null && endTime != null && caller != null && callee != null) {
                String[] startPieces = startTime.split("\\s+");
                String[] endPieces = endTime.split("\\s+");

                bill = new PhoneBill();
                bill.setCustomer(customer);
                this.bills.put(customer, bill);

                PhoneCall call = new PhoneCall();
                Boolean callerSet = call.setCaller(caller);
                Boolean calleeSet = call.setCallee(callee);
                Boolean startSet = call.setStartTimeString(startPieces[0], startPieces[1], startPieces[2]);
                Boolean endSet = call.setEndTimeString(endPieces[0], endPieces[1], endPieces[2]);

                if ((callerSet && calleeSet && startSet && endSet)) {
                    bill.addPhoneCall(call);
                    pw.println(customer + "'s call has been added to their bill");
                }
                else {
                    pw.println("The entered phone call data is formatted incorrectly.");
                }

                response.setStatus(HttpServletResponse.SC_OK);
            }
            else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    /**
     * Handles an HTTP POST request by storing the Phonecall for the
     * "customer", "callerNumber", "calleeNumber", "startTime", and "endTime" request parameters.  It writes the Phonecall
     * entry to the HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String customer = getParameter(CUSTOMER_PARAM, request );
        if (customer == null) {
            PrintWriter pw = response.getWriter();
            pw.println("Missing customer.");
            pw.flush();
            return;
        }

      String caller = getParameter(CALLER_PARAM, request);
      String callee = getParameter(CALLEE_PARAM, request);
      String startTime = getParameter(START_PARAM, request);
      String [] startPieces = startTime.split("\\s+");
      String endTime = getParameter(END_PARAM, request);
      String [] endPieces = endTime.split("\\s+");

      PhoneBill bill = this.bills.get(customer);
      if(bill == null) {

          bill = new PhoneBill();
          bill.setCustomer(customer);
          this.bills.put(customer, bill);
      }

      PhoneCall call = new PhoneCall();
      call.setCaller(caller);
      call.setCallee(callee);
      call.setStartTimeString(startPieces[0], startPieces[1], startPieces[2]);
      call.setEndTimeString(endPieces[0], endPieces[1], endPieces[2]);

      bill.addPhoneCall(call);
      response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        this.dictionary.clear();

    /*    PrintWriter pw = response.getWriter();
        pw.println(Messages.allDictionaryEntriesDeleted());
        pw.flush();*/

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
        throws IOException
    {
   /*     String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);*/
    }

    /**
     * Writes the definition of the given word to the HTTP response.
     *
     * The text of the message is formatted with
     * {}
     */
    private void writeDefinition(String word, HttpServletResponse response ) throws IOException
    {
     /*   String definition = this.dictionary.get(word);

        PrintWriter pw = response.getWriter();
        pw.println(Messages.formatDictionaryEntry(word, definition));

        pw.flush();*/

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Writes all of the dictionary entries to the HTTP response.
     *
     * The text of the message is formatted with
     * {}
     */
    private void writeAllDictionaryEntries(HttpServletResponse response ) throws IOException
    {
   /*     PrintWriter pw = response.getWriter();
        Messages.formatDictionaryEntries(pw, dictionary);

        pw.flush();
*/
        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */

    private String getParameter(String name, HttpServletRequest request) {
      String value = request.getParameter(name);
      if (value == null || "".equals(value)) {
        return null;

      } else {
        return value;
      }
    }

    @VisibleForTesting
    String getDefinition(String word) {
        return this.dictionary.get(word);
    }

}
