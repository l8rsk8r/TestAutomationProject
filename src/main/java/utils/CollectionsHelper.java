package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;


public class CollectionsHelper {

	protected static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	StringHelper stringHelper = new StringHelper();
	
	public ArrayList<String> removeAllFromArrayList(ArrayList<String> removeFromArrayList, ArrayList<String> removeArrayList) {
		LOGGER.fine("removing "+removeArrayList+" from "+removeFromArrayList);
		ArrayList<String> finalArrayList = new ArrayList<String>();
		for (String val: removeFromArrayList) {
			finalArrayList.add(val);
		}
		finalArrayList.removeAll(removeArrayList);
		LOGGER.fine("returning after removing values from main arraylist");
		LOGGER.fine("returning: "+finalArrayList);
		return finalArrayList;
	}
	
	public ArrayList<String> findCommonFromArrayLists(ArrayList<String> retainToArrayList, ArrayList<String> retainFromArrayList) {
		LOGGER.fine("finding common between "+retainToArrayList+" and "+retainFromArrayList);
		ArrayList<String> finalArrayList = new ArrayList<String>();
		for (String val: retainToArrayList) {
			finalArrayList.add(val);
		}
		finalArrayList.retainAll(retainFromArrayList);
		LOGGER.fine("returning common between 2 array lists");
		LOGGER.fine("returning: "+finalArrayList);
		return finalArrayList;
	}
		
	public HashMap<String, String> changeDecimalPrecisionOfHashMapValues(HashMap<String, String> map, int decimalPrecision) {
		for (String key: map.keySet()) {
			String value = map.get(key);
			try {
				Float valueF = stringHelper.stringToFloat(value, decimalPrecision);
				value = Float.toString(valueF);
				map.put(key, value);
			} catch (Exception e) {
			}
		}
		return map;
	}
	
	public static void main(String[] args) {
		CollectionsHelper ch = new CollectionsHelper();
		ArrayList<String> al1 = new ArrayList<String>(Arrays.asList("a", "b", "c", "d", "e"));
		ArrayList<String> al2 = new ArrayList<String>(Arrays.asList("a", "f"));
		ArrayList<String> al3 = new ArrayList<String>(Arrays.asList("b", "g"));

		ArrayList<String> sub1 = ch.removeAllFromArrayList(al1, al1);
		System.out.println(sub1.size());
		ArrayList<String> sub2 = ch.removeAllFromArrayList(al1, al3);
		System.out.println(sub2);
		
		ArrayList<String> ret1 = ch.findCommonFromArrayLists(al1, al2);
		System.out.println(ret1);
		ArrayList<String> ret2 = ch.findCommonFromArrayLists(al1, al3);
		System.out.println(ret2);
	}

}
