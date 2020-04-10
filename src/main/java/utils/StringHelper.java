package utils;

import java.util.Random;
import java.util.logging.Logger;

import org.openqa.selenium.By;

public class StringHelper {

	protected static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/*
	 * @param lengthOfString = the length of the string to be generated
	 * @param stringType = ENUM >> alphabets, numbers, alphanumeric
	 */
	public String getRandomString(int lengthOfString, String stringType) { 
		LOGGER.fine("generating random string with type: "+stringType+" and length of: "+lengthOfString);
		String randomString = "";
		String characters = null;
		String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
					 		+ "abcdefghijklmnopqrstuvxyz";
		String num = "0123456789";
		if (stringType == "alphabets") {
			characters = alpha;
		} else if (stringType == "numbers") {
			characters = num;
		} else if (stringType == "alphanumeric") {
			characters = alpha+num;
		}     
        StringBuilder sb = new StringBuilder(lengthOfString); 
        for (int i = 0; i < lengthOfString; i++) { 
            int index 
                = (int)(characters.length() 
                        * Math.random()); 
            sb.append(characters 
                          .charAt(index)); 
        } 
        randomString = sb.toString();
        LOGGER.fine("returning random string: "+randomString);
        return randomString;
    } 
	
	public String cutString(String fullString, int stringSize) { 
		LOGGER.fine("cutting string: "+fullString+" to the size of: "+stringSize);
		String newString = null;
		newString = fullString.substring(0, stringSize);
		LOGGER.fine("returning new string: "+newString);
		return newString;
    } 
	
	public int randomNumber(int min, int max) {
		LOGGER.fine("generating random number where the min is: "+min+" and the max is: "+max);
		int rand = (int) ((Math.random() * ((max - min) + 1)) + min);
		LOGGER.fine("retrning random number: "+rand);
		return rand;
	}
	
	public int[] randomNumbers(int min, int max, int intLimit) {
		LOGGER.fine("generating random numbers where the min is: "+min+" and the max is: "+max+ " and limit is: "+intLimit);
		int[] ints = new Random().ints(min, max).distinct().limit(intLimit).toArray();
		LOGGER.fine("returning random numbers: "+ints);
		return ints;
	}
	
	public String extractXpathFromLocator(By locator) {
		LOGGER.fine("extracting xpath from locator: "+locator.toString());
		int index = locator.toString().indexOf("/");
		String xpath = locator.toString().substring(index);
		LOGGER.fine("returning xpath: "+xpath);
		return xpath;
	}
	
	public float stringToFloat(String value) {
		LOGGER.fine("converting a string to float for: "+value);
		String[] removeChars = {"(", ")", ","};
		boolean isNegativeNumber = false;
		if (value.startsWith("(")) {
			isNegativeNumber = true;
		}
		LOGGER.fine("isNegativeNumber flag is set to: "+isNegativeNumber);
		value = removeMultipleChars(value, removeChars);
		LOGGER.fine("removed special characters from value, new value: "+value);
		float result = Float.parseFloat(value);
		LOGGER.fine("converted the number to float: "+result);
		if (isNegativeNumber == true) {
			result = -result;
		}
		LOGGER.fine("returning float"+result);
		return result;
	}
	
	public Float stringToFloat(String value, int decimalPrecision) {
		String roundMultiplier = "1";
		for (int i = 0; i < decimalPrecision; i++) {
			roundMultiplier = roundMultiplier+"0";
		}
		int roundMultiplierI = Integer.parseInt(roundMultiplier);
		float roundMultiPlierF = (float) (Math.round(roundMultiplierI * 10.0) / 10.0);
		LOGGER.fine("converting a string to float for: "+value);
		String[] removeChars = {"(", ")", ","};
		boolean isNegativeNumber = false;
		if (value.startsWith("(")) {
			isNegativeNumber = true;
		}
		LOGGER.fine("isNegativeNumber flag is set to: "+isNegativeNumber);
		value = removeMultipleChars(value, removeChars);
		LOGGER.fine("removed special characters from value, new value: "+value);
		float result = Float.parseFloat(value);
		result = (float) (Math.round(result * roundMultiPlierF) / roundMultiPlierF);
		LOGGER.fine("converted the number to float: "+result);
		if (isNegativeNumber == true) {
			result = -result;
		}
		LOGGER.fine("returning float"+result);
		return result;
	}
	
	public String removeMultipleChars(String value, String... chars) {
		LOGGER.fine("removing special characters: "+chars+" from string: "+value);
		for (int i = 0; i < chars.length; i++) {
			if (value.contains(chars[i])) {
				value = value.replace(chars[i], "");
			}
		}
		LOGGER.fine("returning value after removing chars: "+value);
		return value;
	}
	
	public String removeLastCharacters(String str, int numOfCharsToRemove) {
	   String result = null;
	   if ((str != null) && (str.length() > 0)) {
	      result = str.substring(0, str.length() - numOfCharsToRemove);
	   }
	   return result;
	}
	
	public String splitCamelCaseString(String str) {
		String newString = "";
		int index = 0;
		for (char ch: str.toCharArray()) {
			if (index == 0) {
				String startWord = Character.toString(ch).toUpperCase();
				newString = newString+startWord;
			} else if (Character.isUpperCase(ch) || Character.isDigit(ch)) {
				String startWord = " "+Character.toString(ch);
				newString = newString+startWord;
			} else {
				newString = newString+Character.toString(ch);
			}
			index++;
		}
		return newString;
	}
	
	public static void main(String[] args) {
		StringHelper sh = new StringHelper();
		int[] randInts = sh.randomNumbers(0,6,6);
		for (int num : randInts)
		System.out.println(num);
		String[] chars = {"(", ")", ","};
		String result = sh.removeMultipleChars("(2323,434,9.88)", chars);
		System.out.println(result); 
		String negativeNum = "(8.4876)";
		float stf = sh.stringToFloat(negativeNum, 2);
		System.out.println(stf);
		String firstFour = sh.cutString("HHDHsdhfekhksdjkn", 4);
		System.out.println(firstFour);
		String str = "khurram,,,";
		String removed = sh.removeLastCharacters(str, 3);
		System.out.println(removed);
		String str2 = sh.splitCamelCaseString("my9Name9IsKhurram");
		System.out.println(str2);
	}
}
