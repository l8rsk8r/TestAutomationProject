package utils;

import java.util.logging.Logger;

public class Calculator {
	
	protected static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	StringHelper stringHelper = new StringHelper();
	
	public double findMatchPercentage(float value1, float value2) {
		LOGGER.fine("finding match percent for : "+value1+" and "+value2);
		value1 = Math.abs(value1);
		value2 = Math.abs(value2);
		LOGGER.fine("converted values to absoulute: "+value1+" and "+value2);
		float biggerValue;
		float smallerValue;
		if (value1 > value2) {
			biggerValue = value1;
			smallerValue = value2;
		}else if (value1 < value2) {
			biggerValue = value2;
			smallerValue = value1;
		}else {
			LOGGER.fine("both values equal, returning: 100");
			return 100;
		}
		double result = smallerValue / biggerValue * 100;
		LOGGER.fine("before rounding percentage: "+result);
		result = Math.round(result * 100.0) / 100.0;
		LOGGER.fine("returning rounded percentage: "+result);
		return result;
	}
	
	public double findMatchPercentage(String value1, String value2) {
		LOGGER.fine("finding match percent for : "+value1+" and "+value2+" as strings");
		String[] removeChars = {"(", ")", ","};
		value1 = stringHelper.removeMultipleChars(value1, removeChars);
		value2 = stringHelper.removeMultipleChars(value2, removeChars);
		LOGGER.fine("removed special characters (, ), and , :"+value1+" and "+value2);
		float value1Float = stringHelper.stringToFloat(value1);
		float value2Float = stringHelper.stringToFloat(value2);
		LOGGER.fine("converted values from String to Float "+value1+" and "+value2);
		return findMatchPercentage(value1Float, value2Float);
	}
	
	public Float roundDecimal(Float value, int decimalPrecision) {
		String roundMultiplier = "1";
		for (int i = 0; i < decimalPrecision; i++) {
			roundMultiplier = roundMultiplier+"0";
		}
		int roundMultiplierI = Integer.parseInt(roundMultiplier);
		float roundMultiPlierF = (float) (Math.round(roundMultiplierI * 10.0) / 10.0);
		return  (float) (Math.round(value * roundMultiPlierF)/roundMultiPlierF);	
	}
	
	public static void main(String[] args) {
		Calculator calc = new Calculator();
		double result1 = calc.findMatchPercentage(-1.4f, -1.2f);
		System.out.println(result1);
		double result2 = calc.findMatchPercentage(0.7f, 1.2f);
		System.out.println(result2);
		double result3 = calc.findMatchPercentage(1.6f, 1.4f);
		System.out.println(result3);
		double result4 = calc.findMatchPercentage("(0.98)", "(1)");
		System.out.println(result4);
		Float result5 = calc.roundDecimal(8.0948494f, 1);
		System.out.println(result5);
		
	}

}
