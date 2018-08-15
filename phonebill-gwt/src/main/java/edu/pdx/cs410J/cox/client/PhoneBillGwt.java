package edu.pdx.cs410J.cox.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.MenuBar;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A basic GWT class that makes sure that we can send an Phone Bill back from the server
 */
public class PhoneBillGwt implements EntryPoint {
  private final Alerter alerter;
  private final PhoneBillServiceAsync phoneBillService;
  private final Logger logger;
  private String selectedOption = "add";
  

  @VisibleForTesting
  Button showPhoneBillButton;

  @VisibleForTesting
  Button showUndeclaredExceptionButton;

  @VisibleForTesting
  Button showDeclaredExceptionButton;

  @VisibleForTesting
  Button showClientSideExceptionButton;

  @VisibleForTesting
  MenuBar helpMenu;

  @VisibleForTesting
   FlexTable applicationTable, optionButtonsTable;

  @VisibleForTesting
  Button addCallButton, searchBillButton, prettyPrintBillButton;

  @VisibleForTesting
  FlexTable inputsTable;

  @VisibleForTesting
  Label customerLabel, callerLabel, calleeLabel, startLabel, endLabel;

  @VisibleForTesting
  TextBox customerNameInput, callerNumberInput, calleeNumberInput, addStartTimeInput, addEndTimeInput;

  @VisibleForTesting
  FlexTable submitOrClearButtonsTable;

  @VisibleForTesting
  Button submitRequestButton, clearAllInputsButton;

  @VisibleForTesting
  TextArea billResultsTextarea;


  public PhoneBillGwt() {
    this(new Alerter() {
      @Override
      public void alert(String message) {
        Window.alert(message);
      }
    });
  }

  @VisibleForTesting
  PhoneBillGwt(Alerter alerter) {
    this.alerter = alerter;
    this.phoneBillService = GWT.create(PhoneBillService.class);
    this.logger = Logger.getLogger("phoneBill");
    Logger.getLogger("").setLevel(Level.INFO);  // Quiet down the default logging
  }

  private void alertOnException(Throwable throwable) {
    Throwable unwrapped = unwrapUmbrellaException(throwable);
    StringBuilder sb = new StringBuilder();
    sb.append(unwrapped.toString());
    sb.append('\n');

    for (StackTraceElement element : unwrapped.getStackTrace()) {
      sb.append("  at ");
      sb.append(element.toString());
      sb.append('\n');
    }

    this.alerter.alert(sb.toString());
  }

  private Throwable unwrapUmbrellaException(Throwable throwable) {
    if (throwable instanceof UmbrellaException) {
      UmbrellaException umbrella = (UmbrellaException) throwable;
      if (umbrella.getCauses().size() == 1) {
        return unwrapUmbrellaException(umbrella.getCauses().iterator().next());
      }

    }

    return throwable;
  }

  private void addWidgets(VerticalPanel panel) {
    final String readme = "-- README --\n" +
            "Project 5 implemented by Lily Cox \n" +
            "This program creates phone bills from phone call information. " +
            "There are three main options for interacting with the phonebill application: 'Add a Call', 'Search Bill', and 'Print Bill'. " +
            "When one of these options is selected, the relevant textboxes will be made visible. The selected option's button will become disabled (grayed out and"+
            "non clickable) to indicate that that is the option that is currently selected. 'Add a Call' is selected by default. "+
            "Each piece of data entered into the text boxes is checked to make sure it adheres to the expected formatting. " +
            "If any information is formatted incorrectly, an alert will pop up stating which textboxes are formatted incorrectly. " +
            "No calls will be added, or searches will be conducted if any of the information is formatted incorrectly. "+
            "If all of the entered data was formatted correctly, the information will be added to an instance of the" +
            "PhoneCall class, and that instance added to the specified customer's bill. " +
            "If a new call is being added, and the included customer does not exist, a bill will be created for them. " +
            "If the customer provided to 'Search Bill' or 'Print Bill' does not exist on the server, an alert message will be displayed to the user. " +
            "Search and Print results will be pretty printed to the textarea beneath the 'SUBMIT' button. "+
            "To send the 'Add a Call', 'Search Bill', or 'Print Bill' requests to the server, fill out all provided text boxes, and click the 'SUBMIT' button below the text boxes. " +
            "Clicking 'Empty Textboxes' will delete all information you have put in the textboxes. ";

    showPhoneBillButton = new Button("Show Phone Bill");
    showPhoneBillButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        showPhoneBill();
      }
    });

    showUndeclaredExceptionButton = new Button("Show undeclared exception");
    showUndeclaredExceptionButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        showUndeclaredException();
      }
    });

    showDeclaredExceptionButton = new Button("Show declared exception");
    showDeclaredExceptionButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        showDeclaredException();
      }
    });

    showClientSideExceptionButton= new Button("Show client-side exception");
    showClientSideExceptionButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        throwClientSideException();
      }
    });

    addCallButton= new Button("Add a Call");
    addCallButton.addClickHandler( new ClickHandler () {
      @Override
      public void onClick(ClickEvent clickEvent) {selectedOption="add";setForAdd();}
    });
    addCallButton.setEnabled(false);

    searchBillButton= new Button("Search Bill");
    searchBillButton.addClickHandler( new ClickHandler () {
      @Override
      public void onClick(ClickEvent clickEvent) {selectedOption="search";setForSearch();}
    });

    prettyPrintBillButton= new Button("Print Bill");
    prettyPrintBillButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {selectedOption="print";setForPrint();}
    });

    submitRequestButton = new Button ("SUBMIT");
    submitRequestButton.addClickHandler(new ClickHandler () {
        @Override
        public void onClick(ClickEvent clickEvent) {
            switch (selectedOption) {
                case "add":
                    addCall();
                    break;
                case "search":
                    searchBill();
                    break;
                case "print":
                    printBill();
                    break;
            }
        }
    });

    clearAllInputsButton = new Button ("Empty Textboxes");
    clearAllInputsButton.addClickHandler(new ClickHandler () {
      @Override
      public void onClick(ClickEvent clickEvent) {
       customerNameInput.setValue("");
       callerNumberInput.setValue("");
       calleeNumberInput.setValue("");
       addStartTimeInput.setValue("");
       addEndTimeInput.setValue("");
      }
    });

    helpMenu = new MenuBar();
    Command cmd = new Command () {
      public void execute () {
        alerter.alert(readme);
      }
    };
    helpMenu.addItem("Readme", cmd);
    helpMenu.getElement().getStyle().setProperty("position", "absolute");
    helpMenu.getElement().getStyle().setProperty("left", "10px");
    helpMenu.getElement().getStyle().setProperty("top", "10px");

    // applicationTable - holds all buttons and labels for the phonebill application
    applicationTable = new FlexTable();
    optionButtonsTable = new FlexTable();

    // optionButtonsTable - holds all options buttons - within applicationTable
    optionButtonsTable.setWidget(0,0, addCallButton);
    optionButtonsTable.setWidget(0, 1, searchBillButton);
    optionButtonsTable.setWidget(0, 2, prettyPrintBillButton);

    // inputsTable - holds all inputs and inputs label - within applicationTable
    customerLabel = new Label("Customer Name: ");
    callerLabel = new Label("Caller Number: ");
    calleeLabel = new Label("Callee Number: ");
    startLabel = new Label("Start Time: ");
    endLabel = new Label("End Time: ");

    customerNameInput = new TextBox();
    callerNumberInput = new TextBox();
    calleeNumberInput = new TextBox();
    addStartTimeInput = new TextBox();
    addEndTimeInput = new TextBox();

    inputsTable = new FlexTable();
    inputsTable.setWidget(0, 0, customerLabel);
    inputsTable.setWidget(0, 1, customerNameInput);
    inputsTable.setWidget(1,0, callerLabel);
    inputsTable.setWidget(1,1, callerNumberInput);
    inputsTable.setWidget(2,0,calleeLabel);
    inputsTable.setWidget(2,1,calleeNumberInput);
    inputsTable.setWidget(3,0,startLabel);
    inputsTable.setWidget(3,1,addStartTimeInput);
    inputsTable.setWidget(4,0,endLabel);
    inputsTable.setWidget(4,1,addEndTimeInput);

    // submitOrClearButtonsTable - holds the 'SUBMIT' and clear buttons - within applicationTable
    submitOrClearButtonsTable = new FlexTable();
    submitOrClearButtonsTable.setWidget(0, 0, submitRequestButton);
    submitOrClearButtonsTable.setWidget(0,1,clearAllInputsButton);

    applicationTable.setWidget(2, 0, optionButtonsTable);
    applicationTable.setWidget(4, 0, inputsTable);
    applicationTable.setWidget(6, 0, submitOrClearButtonsTable);
    applicationTable.getElement().getStyle().setProperty("position", "absolute");
    applicationTable.getElement().getStyle().setProperty("left", "10px");
    applicationTable.getElement().getStyle().setProperty("top", "40px");

    // billResultsTextarea - textbox to put print and search results - in applicationTable
    billResultsTextarea = new TextArea();
    billResultsTextarea.setWidth("270px");
    billResultsTextarea.setHeight("250px");
    billResultsTextarea.setVisible(false);
    billResultsTextarea.setReadOnly(true);

    applicationTable.setWidget(8,0,billResultsTextarea);

    panel.add(helpMenu);
    panel.add(applicationTable);
  }

  private void throwClientSideException() {
    logger.info("About to throw a client-side exception");
    throw new IllegalStateException("Expected exception on the client side");
  }

  private void showUndeclaredException() {
    logger.info("Calling throwUndeclaredException");
    phoneBillService.throwUndeclaredException(new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(Void aVoid) {
        alerter.alert("This shouldn't happen");
      }
    });
  }

  private void showDeclaredException() {
    logger.info("Calling throwDeclaredException");
    phoneBillService.throwDeclaredException(new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(Void aVoid) {
        alerter.alert("This shouldn't happen");
      }
    });
  }

  private void showPhoneBill() {
    logger.info("Calling getPhoneBill");
    phoneBillService.getPhoneBill(new AsyncCallback<PhoneBill>() {

      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(PhoneBill phoneBill) {
        StringBuilder sb = new StringBuilder(phoneBill.toString());
        Collection<PhoneCall> calls = phoneBill.getPhoneCalls();
        for (PhoneCall call : calls) {
          sb.append(call);
          sb.append("\n");
        }
        alerter.alert(sb.toString());
      }
    });
  }
  
  @Override
  public void onModuleLoad() {
    setUpUncaughtExceptionHandler();

    // The UncaughtExceptionHandler won't catch exceptions during module load
    // So, you have to set up the UI after module load...
    Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
      @Override
      public void execute() {
        setupUI();
      }
    });
  }


  private void setupUI() {
    RootPanel rootPanel = RootPanel.get();
    VerticalPanel panel = new VerticalPanel();
    rootPanel.add(panel);

    addWidgets(panel);
  }

  private void setUpUncaughtExceptionHandler() {
    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(Throwable throwable) {
        alertOnException(throwable);
      }
    });
  }

  @VisibleForTesting
  interface Alerter {
    void alert(String message);
  }

  /**
   * This method shows all inputs and  input lables, disables the add button, enables the other buttons,
   * and hides the textarea.
   */
  public void setForAdd () {
    addCallButton.setEnabled(false);
    searchBillButton.setEnabled(true);
    prettyPrintBillButton.setEnabled(true);

    customerNameInput.setVisible(true);
    customerLabel.setVisible(true);

    callerNumberInput.setVisible(true);
    callerLabel.setVisible(true);

    calleeNumberInput.setVisible(true);
    calleeLabel.setVisible(true);

    addStartTimeInput.setVisible(true);
    startLabel.setVisible(true);

    addEndTimeInput.setVisible(true);
    endLabel.setVisible(true);

    billResultsTextarea.setVisible(false);
    billResultsTextarea.setValue("");
  }

  /**
   * This method hides the caller and callee inputs and input labels, disables the search button, enables the
   * other buttons, and hides the textarea.
   */
  public void setForSearch () {
    addCallButton.setEnabled(true);
    searchBillButton.setEnabled(false);
    prettyPrintBillButton.setEnabled(true);

    customerNameInput.setVisible(true);
    customerLabel.setVisible(true);

    callerNumberInput.setVisible(false);
    callerLabel.setVisible(false);

    calleeNumberInput.setVisible(false);
    calleeLabel.setVisible(false);

    addStartTimeInput.setVisible(true);
    startLabel.setVisible(true);

    addEndTimeInput.setVisible(true);
    endLabel.setVisible(true);

    billResultsTextarea.setVisible(false);
    billResultsTextarea.setValue("");
  }

  /**
   * This method hides all inputs and input labels except for customer, disables the print button, enables the other
   * buttons, and hides the textarea.
   */
  public void setForPrint () {
    addCallButton.setEnabled(true);
    searchBillButton.setEnabled(true);
    prettyPrintBillButton.setEnabled(false);

    customerNameInput.setVisible(true);
    customerLabel.setVisible(true);

    callerNumberInput.setVisible(false);
    callerLabel.setVisible(false);

    calleeNumberInput.setVisible(false);
    calleeLabel.setVisible(false);

    addStartTimeInput.setVisible(false);
    startLabel.setVisible(false);

    addEndTimeInput.setVisible(false);
    endLabel.setVisible(false);

    billResultsTextarea.setVisible(false);
    billResultsTextarea.setValue("");
  }

  /**
   * Get all of the inputed information into a new PhoneCall (if it is formatted correctly), then send it to
   * the server to save. Alerts the user if it is added correctly, or if the call already exists.
   */
  public void addCall () {
      logger.info("Calling setPhoneBill");

      PhoneCall newCall = textBoxesToPhoneCall();
      if(newCall == null) {
        return;
      }
      String customer = customerNameInput.getValue().trim();

      if(newCall.getEndTime().before(newCall.getStartTime())) {
        alerter.alert("The end time falls before the start time. Call will not be added.");
        return;
      }

      phoneBillService.setPhoneBill(customer, newCall, new AsyncCallback<Boolean>() {
          @Override
          public void onFailure(Throwable ex) {
            alertOnException(ex);
          }

          @Override
          public void onSuccess(Boolean callAddSuccess) {
            if(callAddSuccess == true) {
              alerter.alert("Added the call to " + customer + "'s bill.");
            }
            else {
              alerter.alert("This call already exists in the bill, and has not been added.");
            }
          }
        }
      );
  }

  /**
   * This method will get the bill if an existing customer is requested, search the bill for all calls
   * within the included time period, and pretty print it's contents to a textarea.
   */
  public void searchBill () {
    Boolean validSearch = validateSearchCall();
    if(validSearch == false) {
      return;
    }

    String customer = customerNameInput.getValue().trim();
    String start = addStartTimeInput.getValue().trim();
    String[] startPieces = start.split("\\s+");

    String end = addEndTimeInput.getValue().trim();
    String[] endPieces = end.split("\\s+");

    logger.info("Calling getPhoneBill");
    phoneBillService.getBill(customer, new AsyncCallback<PhoneBill>() {

      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(PhoneBill phoneBill) {
        if(phoneBill != null) {
          PhoneBill subBill = phoneBill.searchCalls(customer,PhoneCall.stringToDate(startPieces[0], startPieces[1], startPieces[2]), PhoneCall.stringToDate(endPieces[0], endPieces[1], endPieces[2]));

          if(subBill.getPhoneCalls().size() == 0) {
            billResultsTextarea.setVisible(true);
            billResultsTextarea.setValue("No calls fall within those times.");
            return;
          }

          String prettyPrint = subBill.prettyPrintCalls(subBill, "Search Results: ");
          billResultsTextarea.setVisible(true);
          billResultsTextarea.setValue(prettyPrint);
        }
        else {
          alerter.alert("That customer doesn't exist.");
        }
      }
    });
  }

  /**
   * This method will get the bill if an existing customer is requested, and pretty print it's contents
   * to a textarea.
   */
  public void printBill () {
    logger.info("Calling getPhoneBill to print");
    Boolean customerValid = validatePrintCall();
    if(customerValid == false) {
      return;
    }

    String customer = customerNameInput.getValue().trim();

    phoneBillService.getBill(customer, new AsyncCallback<PhoneBill>() {

      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(PhoneBill bill) {
        if(bill == null) {
          alerter.alert("That customer doesn't exist");
          return;
        }

        String prettyBill = bill.prettyPrintCalls(bill, "Bill for: "+bill.getCustomer());
        billResultsTextarea.setVisible(true);
        billResultsTextarea.setValue(prettyBill);
      }
    });

  }

  /**
   * This method takes the values out of the user inputs, checks that they are all formatted correctly, and adds
   * them to a new instance of PhoneCall. It will send an alert message containing which inputs are malformed.
   * @return A filled PhoneCall if all inputs are formatted correctly, or null if any of them are malformed.
   */
  public PhoneCall textBoxesToPhoneCall () {
    Boolean validCall = validateNewCall();
    if(validCall == false) {
      return null;
    }
    PhoneCall call = new PhoneCall();

    String caller = callerNumberInput.getValue().trim();
    Boolean callerCorrect = call.setCaller(caller);

    String callee = calleeNumberInput.getValue().trim();
    Boolean calleeCorrect = call.setCallee(callee);

    String start = addStartTimeInput.getValue().trim();
    String[] startPieces = start.split("\\s+");
    Boolean startCorrect = call.setStartTimeString(startPieces[0], startPieces[1], startPieces[2]);

    String end = addEndTimeInput.getValue().trim();
    String[] endPieces = end.split("\\s+");
    Boolean endCorrect = call.setEndTimeString(endPieces[0], endPieces[1], endPieces[2]);

    String errorMessage = "";

    if(callerCorrect == false) {
      errorMessage += "Caller Number is not formatted correctly.\n";
    }
    if(calleeCorrect == false) {
      errorMessage += "Callee Number is not formatted correctly.\n";
    }
    if(startCorrect == false) {
      errorMessage += "Start Time is not formatted correctly.\n";
    }
    if(endCorrect == false) {
      errorMessage += "End Time is not formatted correctly.\n";
    }

    if(errorMessage.equals("") == false) {
      alerter.alert(errorMessage);
      return null;
    }

    return call;
  }

  /**
   * This method will check whether the customer, caller, callee, start time, and end time text boxes are filled.
   * @return Returns true if all five inputs are occupied, false if any of them are empty.
   */
  public Boolean validateNewCall () {
    String invalidMessage = "";

    if(customerNameInput.getValue().equals("")) {
      invalidMessage += "Customer is empty.\n";
    }

    if(callerNumberInput.getValue().equals("")) {
      invalidMessage += "Caller number is empty.\n";
    }

    if(calleeNumberInput.getValue().equals("")) {
      invalidMessage += "Callee number is empty.\n";
    }

    if(addStartTimeInput.getValue().equals("")) {
      invalidMessage += "Start time is empty.\n";
    }

    if(addEndTimeInput.getValue().equals("")) {
      invalidMessage += "End time is empty.\n";
    }

    if(invalidMessage.equals("") == false) {
      alerter.alert(invalidMessage);
      return false;
    }

    return true;
  }

  /**
   * This method will check whether the customer, start time, and end time textboxes are filled.
   * @return Returns true if all three inputs are occupied, false if any of them are empty.
   */
  public Boolean validateSearchCall () {
    String invalidMessage = "";

    String customer = customerNameInput.getValue();
    if(customer.equals("")) {
      invalidMessage += "Customer is empty.\n";
    }

    String start = addStartTimeInput.getValue();
    if(start.equals("")) {
      invalidMessage += "Start time is empty.\n";
    }

    String end = addEndTimeInput.getValue();
    if(end.equals("")) {
      invalidMessage += "End time is empty.\n";
    }

    if(invalidMessage.equals("") == false) {
      alerter.alert(invalidMessage);
      return false;
    }

    return true;
  }

  /**
   * This method will check whether the customer textbox is filled.
   * @return Returns true if a customer input is occupied, false if it is empty.
   */
  public Boolean validatePrintCall () {
    String invalidMessage = "";

    String customer = customerNameInput.getValue();
    if(customer.equals("")) {
      invalidMessage += "Customer is empty.\n";
      alerter.alert(invalidMessage);
      return false;
    }

    return true;
  }
}
