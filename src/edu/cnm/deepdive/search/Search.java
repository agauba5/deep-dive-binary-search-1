package edu.cnm.deepdive.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class Search {

  private static final String RESOURCE_BUNDLE_NAME = "usage";
  private static final String USAGE_MESSAGE_KEY = "searchMessage";
  private static final String PARSE_ERROR_MESSAGE_KEY = "parseErrorMessage";
  private static final String VALUE_ERROR_MESSAGE_KEY = "needleErrorMessage";
  private static final String READ_ERROR_MESSAGE_KEY = "haystackErrorMessage";
  private static final String FOUND_MESSAGE_KEY = "foundMessage";
  private static final String NOT_FOUND_MESSAGE_KEY = "notFoundMessage";
  
  public static void main(String[] args) {
    try {
      ResourceBundle resources = getBundle(RESOURCE_BUNDLE_NAME);
      int targetVal = getSearchValue(args, resources);
      Integer[] dataArray = readValues(resources);
      int foundPosition = findValue(targetVal, dataArray);
      if (foundPosition >= 0) {
        System.out.printf(resources.getString(FOUND_MESSAGE_KEY), 
                          targetVal, foundPosition);
      } else {
        System.out.printf(resources.getString(NOT_FOUND_MESSAGE_KEY), 
                          targetVal, ~foundPosition);        
      }
    } catch (Exception ex) {
      // Do nothing.
    }
  }

  private static int getSearchValue(String[] args, ResourceBundle resources) 
      throws IllegalArgumentException, NumberFormatException, ArrayIndexOutOfBoundsException {
    try {
      int value = Integer.parseInt(args[0]);
      if (value < 0) {
        throw new IllegalArgumentException();
      }
      return value;
    } catch (NumberFormatException ex) {
      System.out.printf(resources.getString(PARSE_ERROR_MESSAGE_KEY));
      System.out.printf(resources.getString(USAGE_MESSAGE_KEY), 
          Search.class.getName());
      throw ex;
    } catch (IllegalArgumentException ex) {
      System.out.printf(resources.getString(VALUE_ERROR_MESSAGE_KEY));
      System.out.printf(resources.getString(USAGE_MESSAGE_KEY), 
          Search.class.getName());
      throw ex;
    } catch (ArrayIndexOutOfBoundsException ex) {
      System.out.printf(resources.getString(USAGE_MESSAGE_KEY), 
          Search.class.getName());
      throw ex;
    }
  }
  
  private static ResourceBundle getBundle(String bundleName) {
    return ResourceBundle.getBundle(bundleName);
  }

  private static Integer[] readValues(ResourceBundle resources) 
      throws NumberFormatException, IOException {      
    try (
        InputStreamReader reader = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(reader);
    ) {
      List<Integer> data = new LinkedList<>();
      for (String line = buffer.readLine(); line != null; line = buffer.readLine()) {
        data.add(Integer.valueOf(line));
      }
      return data.toArray(new Integer[data.size()]);
    } catch (NumberFormatException | IOException ex) {
      System.out.printf(resources.getString(READ_ERROR_MESSAGE_KEY));
      throw ex;
    }
  }
  
  private static int findValue(int targetVal, Integer[] dataArray) {
    int index = dataArray.length/2 - 1;
    int change = index;
    while (dataArray[index] != targetVal){
      if(change != 0) {
        if(dataArray[index] > targetVal) {
          change >>= 1;
          index -= change;
        }else if(dataArray[index] < targetVal){
          change >>= 1;
          index += change;
        }else {
          return index;
        }
      }else {
        index = ~index;
      }    
    }
    return index;
  }
}









