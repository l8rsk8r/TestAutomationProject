package tests.spaceJam.marketShare;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import common.TempBase;
import comparators.redShiftToMstrApi.MarketShare;
import exceptions.InvalidResponseException;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.HashMap;

public class WeeklyIndexesApiToRedShiftComparison extends TempBase {
		
	mstr.api.marketShare.WeeklyTotalSummaryIndexesData mstrApiClient;
	mstr.dao.marketShare.WeeklyTotalSummaryIndexesData redShiftClient;
	MarketShare weeklyIndexes = new MarketShare();
	HashMap<String, String> mstrApiWeightedData = new HashMap<String, String>();
	HashMap<String, String> mstrApiNonWeightedData = new HashMap<String, String>();
	HashMap<String, String> redShiftWeightedData = new HashMap<String, String>();
	HashMap<String, String> redShiftNonWeightedData = new HashMap<String, String>();
		
	private void compareWeeklyIndexesRedShiftToMstrApiSetup() throws InvalidResponseException, SQLException {
		mstrApiClient = new mstr.api.marketShare.WeeklyTotalSummaryIndexesData();
		mstrApiClient.getRawData();
		mstrApiClient.formatData(2);
		mstrApiWeightedData = mstrApiClient.weightedResponseData;
		mstrApiNonWeightedData = mstrApiClient.nonWeightedResponseData;
		redShiftClient = new mstr.dao.marketShare.WeeklyTotalSummaryIndexesData();
		redShiftClient.getRawData();
		redShiftClient.formatData(2);
		redShiftWeightedData = redShiftClient.weightedMetricsData;
		redShiftNonWeightedData = redShiftClient.nonWeightedMetricsData;
	}
	
	private void addDataTableToReport(HashMap<String, String> map, ExtentTest test) {
		Markup markup;
		String[][] data = new String[map.keySet().size()][2];
		int i = 0;
		for (String key: map.keySet()) {
			data[i][0] = key;
			data[i][1] = map.get(key);
			i++;
		}
		markup = MarkupHelper.createTable(data);
		test.info(markup);
	}
	
	@Test (groups = {"compareWeeklyIndexesRedShiftToMstrApi", "regression", "smoke"})
	private void compareWeeklyIndexesRedShiftToMstrApi() throws Exception {
		compareWeeklyIndexesRedShiftToMstrApiSetup();
		test.info("Begin: Comparison of Market Share Weekly Indexes - RedShift to MSTR API. "
				+ "See nodes below for the input data from RedShift Data base and MSTR API");
		test.info("MSTR API WEIGHTED DATA");
		addDataTableToReport(mstrApiWeightedData, test);
		test.info("MSTR API NON WEIGHTED DATA");
		addDataTableToReport(mstrApiNonWeightedData, test);
		test.info("REDSHIFT WEIGHTED DATA");
		addDataTableToReport(redShiftWeightedData, test);
		test.info("REDSHIFT NON WEIGHTED DATA");
		addDataTableToReport(redShiftNonWeightedData, test);
		HashMap<String, String> mismatches = weeklyIndexes.getWeeklyIndexesMismatches(mstrApiWeightedData, mstrApiNonWeightedData, redShiftWeightedData, redShiftNonWeightedData);
		if (mismatches.size() == 0) {
			test.pass("Test Passed: no data mismatches found");
		} else {
			test.info("Metrics\t"+"API value|DB value");
		}
		for (String key: mismatches.keySet()) {
			test.fail(key+"\t"+mismatches.get(key));
		}
	}
}
