package edu.pdx.cs410J.cox;

import edu.pdx.cs410J.AbstractPhoneBill;
import java.util.Arrays;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  static final String README = "Wow, this is such a good README. So good.";

  public static void main(String[] args) {
    PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath

    if(args.length == 0) {
      System.err.println("Missing command line arguments");
      System.exit(1);
    }

    if( containsOption(args, "-README")) {
      printReadme();
      System.exit(0);
    }

    if(containsOption(args, "-print")) {
      System.out.println(call.toString());
    }

    for (String arg : args) {
      System.out.println(arg);
    }

  }

  private static void printReadme() {
    System.out.println(README);
  }

  private static boolean containsOption(String[] args, String option) {
    return Arrays.stream(args).anyMatch(s -> s.equals(option));
  }

}