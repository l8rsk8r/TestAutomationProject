package comparators.redShiftToMstrApi;

import java.util.HashMap;

import comparators.ComparatorBase;
import utils.StringHelper;

public class MarketShare extends ComparatorBase {

	StringHelper stringHelper = new StringHelper();
	
	public HashMap<String, String> getWeeklyIndexesMismatches(HashMap<String, String> mstrApiWeightedData, HashMap<String, String> mstrApiNonWeightedData,
		HashMap<String, String> redShiftWeightedData, HashMap<String, String> redShiftNonWeightedData) {
		HashMap<String, String> mismatches = new HashMap<String, String>();
		HashMap<String, String> weightedMismatches = getMismatchesFromHashMaps(mstrApiWeightedData, redShiftWeightedData, "|");
		HashMap<String, String> nonWeightedMismatches = getMismatchesFromHashMaps(mstrApiNonWeightedData, redShiftNonWeightedData, "|");
		mismatches.putAll(weightedMismatches);
		mismatches.putAll(nonWeightedMismatches);
		return mismatches;
	}
	
	public static void main(String[] args) {

	}

}
