package edu.cnm.deepdive.search;

import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

public class Generator {

  private static final String RESOURCE_BUNDLE_NAME = "usage";
  private static final String USAGE_MESSAGE_KEY = "usageMessage";
  private static final String PARSE_ERROR_MESSAGE_KEY = "parseErrorMessage";
  private static final String VALUE_ERROR_MESSAGE_KEY = "valueErrorMessage";
  
  private static int size;
  private static int limit;
  
  public static void main(String[] args) {
    parseCommandLine(args, getBundle(RESOURCE_BUNDLE_NAME));
    if (size > 1) {
      emitValues();
    }
  }

  private static ResourceBundle getBundle(String bundleName) {
    return ResourceBundle.getBundle(bundleName);
  }
  
  private static void parseCommandLine(String[] args, ResourceBundle resources) {
    try {
      size = Integer.parseInt(args[0]);
      limit = Integer.parseInt(args[1]);
      if (size <=0 || limit <= 0) {
        throw new IllegalArgumentException();
      }
    } catch (NumberFormatException ex) {
      System.out.printf(resources.getString(PARSE_ERROR_MESSAGE_KEY));
      System.out.printf(resources.getString(USAGE_MESSAGE_KEY), 
          Generator.class.getName());
      size = -1;      
    } catch (IllegalArgumentException ex) {
      System.out.printf(resources.getString(VALUE_ERROR_MESSAGE_KEY));
      System.out.printf(resources.getString(USAGE_MESSAGE_KEY), 
          Generator.class.getName());
      size = -1;      
    } catch (ArrayIndexOutOfBoundsException ex) {
      System.out.printf(resources.getString(USAGE_MESSAGE_KEY), 
          Generator.class.getName());
      size = -1;
    }
  }

  
  private static void emitValues() {
    Random rng = new Random();
    int[] data = new int[size];
    for (int i = 0; i < data.length; i++) {
      data[i] = rng.nextInt(limit);
    }
    Arrays.sort(data);
    for (int value : data) {
      System.out.println(value);
    }
  }
  
}














