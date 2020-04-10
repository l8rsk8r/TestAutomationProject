package comparators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class ComparatorBase {
	
	protected static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Compares two arrays which have delimited rows, like a CSV file. This method does not ignore the header
	 * This method finds discrepancies between the two arrays and returns an array with the failures
	 * @param array1 First array to be compared
	 * @param array2 Second array to be compared 
	 * @param delimiter How is each row delimited
	 * @param keyColumnIndex The index of the unique key which would be considered for comparison after splitting the row
	 * @return ArrayList of all the mismatches with both rows delimited by | or by , if the original delimiter is |
	 */
	private ArrayList<String> simpleDelimitedArrayListsComparator(ArrayList<String> array1, ArrayList<String> array2, String delimiter, int keyColumnIndex) {
		LOGGER.info("Start: comoparing 2 delimited arrayLists");
		ArrayList<String> mismatches = new ArrayList<String>();
		LOGGER.fine("setting delimiter for return");
		String returnDelimiter = "\\|";
		if (delimiter.equals(returnDelimiter)) {
			returnDelimiter = ",";
		} else {
			returnDelimiter = "|";
		}
		LOGGER.fine("iterating over array1");
		for (String array1Row: array1) {
			String[] array1RowSplit = array1Row.split(delimiter);
			String array1Key = array1RowSplit[keyColumnIndex];
			LOGGER.fine("key for array1: "+array1Key);
			for (String array2Row: array2) {
				String[] array2RowSplit = array2Row.split(delimiter);
				String array2Key = array2RowSplit[keyColumnIndex];
				LOGGER.fine("key for array2: "+array2Key);
				if(array1Key.equals(array2Key)) {
					LOGGER.fine("found matching keys for array1 and array 2: "+array1Key);
					if (!array1Row.equals(array2Row)) {
						LOGGER.info("found mismatch between 2 arrays, adding the mismatch to arraylist");
						LOGGER.info("Mismatch array 1 row: "+array1Row);
						LOGGER.info("Mismatch array 2 row: "+array2Row);
						mismatches.add(array1Row+returnDelimiter+array2Row);
						break;
					} 
				}
			}
		}
		LOGGER.info("End: returning mismatches between the 2 arraylists");
		return mismatches;
	}
	
	/**
	 * Compares two arrays which have delimited rows, like a CSV file
	 * This method finds discrepancies between the two arrays and returns an array with the failures
	 * @param array1 First array to be compared
	 * @param array2 Second array to be compared 
	 * @param delimiter How is each row delimited
	 * @param ignoreHeader This determines if the header needs to be ignored for validation
	 * Will compare the headers if set to false and will not compare the headers if set to true
	 * @param keyColumnIndex The index of the unique key which would be considered for comparison after splitting the row
	 * @return ArrayList of all the mismatches with both rows delimited by |
	 */
	public ArrayList<String> simpleDelimitedArrayListsComparator(ArrayList<String> array1, ArrayList<String> array2, String delimiter, boolean ignoreHeaderArray1, boolean ignoreHeaderArray2, int keyColumnIndex) {
		if (ignoreHeaderArray1 == true) {
			LOGGER.info("removing header from array 1 for validation");
			array1.remove(0);
		}
		if (ignoreHeaderArray2 == true) {
			LOGGER.info("removing header from array 2 for validation");
			array2.remove(0);
		}
		return simpleDelimitedArrayListsComparator(array1, array2, delimiter, keyColumnIndex);
	}
	
	/**
	 * Compares two arrays which have delimited rows, like a CSV file
	 * This method finds discrepancies between the two arrays and returns an array with the failures
	 * @param array1 First array to be compared
	 * @param array2 Second array to be compared 
	 * @param delimiter How is each row delimited
	 * @param ignoreHeader This determines if the header needs to be ignored for validation
	 * Will compare the headers if set to false and will not compare the headers if set to true
	 * @param keyColumnName The name of the unique key which would be considered for comparison after splitting the row
	 * @return ArrayList of all the mismatches with both rows delimited by |
	 */
	public ArrayList<String> simpleDelimitedArrayListsComparator(ArrayList<String> array1, ArrayList<String> array2, String delimiter, boolean ignoreHeaderArray1, boolean ignoreHeaderArray2, String keyColumnName) {
		String header = array1.get(0);
		String[] split = header.split(delimiter);
		int i;
		for (i = 0; i < split.length; i++) {
			String indexValue = split[i];
			if (indexValue.equals(keyColumnName)) {
				break;
			}
		}
		if (ignoreHeaderArray1 == true) {
			LOGGER.info("removing header from array 1 for validation");
			array1.remove(0);
		}
		if (ignoreHeaderArray2 == true) {
			LOGGER.info("removing header from array 2 for validation");
			array2.remove(0);
		}
		return simpleDelimitedArrayListsComparator(array1, array2, delimiter, i);
	}
	
	public HashMap<String, String> getMismatchesFromHashMaps(HashMap<String, String> map1, HashMap<String, String> map2, String separator) {
		HashMap<String, String> mismatches = new HashMap<String, String>();
		for (String map1Key: map1.keySet()) {
			String map1Value = map1.get(map1Key);
			for (String map2Key: map2.keySet()) {
				String map2Value = map2.get(map2Key);
				if (map1Key.equalsIgnoreCase(map2Key)) {
					if (!map1Value.equalsIgnoreCase(map2Value)) {
						mismatches.put(map1Key, map1Value+separator+map2Value);
						break;
					}
				}
			}
		}
		return mismatches;
	}
	
	
	public static void main(String[] args) {
		ComparatorBase comparatorBase = new ComparatorBase();
		ArrayList<String> array1 = new ArrayList<String>();
		array1.add("name,ge,address,phone");
		array1.add("khurram,35,111 K St,100100100");
		array1.add("foo,20,111 F St,200200200");
		array1.add("bar,30,111 B St,300300300");
		ArrayList<String> array2 = new ArrayList<String>();
		array2.add("name,age,address,phone");
		array2.add("khurram,35,111 K St,10010000");
		array2.add("foo,20,111 F St,200200200");
		array2.add("bar,30,111 B St,300300300");
		ArrayList<String> mismatches = comparatorBase.simpleDelimitedArrayListsComparator(array1, array2, ",", true, true, "name");
		for (String mismatch: mismatches) {
			System.out.println(mismatch);
		}
		
		array1 = new ArrayList<String>();
		array1.add("name|ge|address|phone");
		array1.add("khurram|35|111 K St|100100100");
		array1.add("foo|20|111 F St|200200200");
		array1.add("bar|30|111 B St|300300300");
		array2 = new ArrayList<String>();
		array2.add("name|age|address|phone");
		array2.add("khurram|35|111 K St|10010000");
		array2.add("foo|20|111 F St|200200200");
		array2.add("bar|30|111 B St|300300300");
		mismatches = comparatorBase.simpleDelimitedArrayListsComparator(array1, array2, "\\|", true, true, "name");
		for (String mismatch: mismatches) {
			System.out.println(mismatch);
		}
		
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		map1.put("name", "khurram");
		map1.put("age", "2");
		map1.put("address", "foobar st");
		map1.put("zip", "11111");
		map2.put("name", "khurram");
		map2.put("age", "22");
		map2.put("address", "foobar st");
		map2.put("zip", "1111");
		System.out.println("results");
		HashMap<String, String> map3 = comparatorBase.getMismatchesFromHashMaps(map1, map2, "|");
		for (String key: map3.keySet()) {
			System.out.println(key+"\t"+map3.get(key));
		}
	}
}
