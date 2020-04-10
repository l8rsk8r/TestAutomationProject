package tableauReportReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import utils.StringHelper;
import utils.fileHelpers.CsvReportReader;

public class MarketShareWeeklyReportReader extends CsvReportReader{

	InputStream reader;
	BufferedReader bufferedReader;
	StringHelper stringHelper = new StringHelper();
	private HashMap<String, String> dataRecord;
	private List<HashMap<String, String>> data = new ArrayList<HashMap<String,String>>();
	private int recordCount = 0;
	private ArrayList<String> hotels = new ArrayList<String>();	
	private ArrayList<String> incodes = new ArrayList<String>();
	
	public List<HashMap<String, String>> getData(){
		return data;
	}
	
	public int getRecordCount() {
		return recordCount;
	}
	
	public ArrayList<String> getHotels() {
		return hotels;
	}
	
	public ArrayList<String> getIncodes() {
		return incodes;
	}
	
	public enum Dimensions {
		HOTEL,
		INCODE
	}
	
	public MarketShareWeeklyReportReader(String inputFilePath, Dimensions dimension) throws Exception {
		super(inputFilePath);
		setDataByDimension(dimension);
	}
	
	public void readTempFile() throws FileNotFoundException {
		reader = new FileInputStream(tempFilePath);
		bufferedReader = new BufferedReader(new InputStreamReader(reader));
	}
	
	public String hotelNamesMappedToMstr(String hotelName) {
		switch(hotelName) {
		case "Tapestry Collection By Hilton":
			return "Tapestry By Hilton";
		case "Waldorf Astoria":
			return "Waldorf Astoria Hotels & Resorts";
		default:
			return hotelName;
		}
	}
	
	public String incodesMappedToMstr(String incode) {
		String[] split = incode.split(" -");
		return split[0];
	}
	
	public String negativeValuesMappedToMstr(String value) {
		String mappedValue = null;
		if (value.startsWith("-")) {
			mappedValue = "("+value.replace("-", "")+")";
		} else {
			mappedValue = value;
		}
		return mappedValue;
	}
	
	public String roundToMillion(String input) {
		String output = null;
		input = input.replace("$", "");
		input = input.replace(",", "");
		int inputToInt = Integer.parseInt(input);
		double roundtoMillion = inputToInt * .000001;
		roundtoMillion = Math. round(roundtoMillion * 100.0) / 100.0;
		String outputString = Double.toString(roundtoMillion);
		output = "$"+outputString+"M";
		if (output.contains("-")) {
			output = output.replace("-", "");
			output = "-"+output;
		}
		return output;
	}

	private void pupulateDataRecordPerRow(HashMap<String, String> dataRecord, String[] columnHeadings, String[] dataSet) {
		int cellNumber = 0;
		for (String columnHeading: columnHeadings) {
			switch(columnHeading) {
			case "Quadrant":
				dataRecord.put("quadrant", negativeValuesMappedToMstr(dataSet[cellNumber]));
				break;
			case "Hotel Count":
				dataRecord.put("hotel-count", negativeValuesMappedToMstr(dataSet[cellNumber]));
				break;
			case "TY OCI":
				dataRecord.put("occupancy-index", negativeValuesMappedToMstr(dataSet[cellNumber]));
				break;
			case "YoY OCI":
				dataRecord.put("yoy-occupancy-index", negativeValuesMappedToMstr(dataSet[cellNumber]));
				break;
			case "TY ADRI":
				dataRecord.put("average-daily-rate-index", negativeValuesMappedToMstr(dataSet[cellNumber]));
				break;
			case "ADRI":
				dataRecord.put("average-daily-rate-index", negativeValuesMappedToMstr(dataSet[cellNumber]));
				break;
			case "YoY ADRI":
				dataRecord.put("yoy-average-daily-rate-index", negativeValuesMappedToMstr(dataSet[cellNumber]));
				break;
			case "TY RPI":
				dataRecord.put("revenue-per-available-room-index", negativeValuesMappedToMstr(dataSet[cellNumber]));
				break;
			case "RPI":
				dataRecord.put("revenue-per-available-room-index", negativeValuesMappedToMstr(dataSet[cellNumber]));
				break;
			case "YoY RPI":
				dataRecord.put("yoy-revenue-per-available-room-index", negativeValuesMappedToMstr(dataSet[cellNumber]));
				break;
			case "RPI Impact":
				dataRecord.put("revenue-per-available-room-impact", negativeValuesMappedToMstr(dataSet[cellNumber]));
				break;
			}
			cellNumber++;
		}	
	}

	public void setDataByDimension(Dimensions dimension) throws IOException {
		String[] columnHeadings;
		readTempFile();
		String line = bufferedReader.readLine();
		columnHeadings = line.split("\t");
		int rowNumber = 0;
		while ((line=bufferedReader.readLine()) != null) {
			dataRecord = new HashMap<String, String>();
			String[] dataSet = line.split("\t");
			String dimensionValue = null;
			dimensionValue = dataSet[0];
			if (!(dimensionValue.contains("Total"))) {
				switch(dimension) {
				case HOTEL:
					dimensionValue = hotelNamesMappedToMstr(dataSet[0]);
					hotels.add(dimensionValue);
					dataRecord.put("hotel", dimensionValue);
					pupulateDataRecordPerRow(dataRecord, columnHeadings, dataSet);	
					break;
				case INCODE:
					dimensionValue = incodesMappedToMstr(dataSet[0]);
					incodes.add(dimensionValue);
					dataRecord.put("incode", dimensionValue);
					pupulateDataRecordPerRow(dataRecord, columnHeadings, dataSet);	
					break;
				}
				recordCount = rowNumber+1;
				data.add(dataRecord);
				rowNumber++;
			}
		}
		bufferedReader.close();
	}
	
	
	public static void main(String[] args) throws Exception {
		String userPath = System.getProperty("user.dir");
		String originalFile = userPath+"/src/test/resources/deleteme/MarketShareTableau.csv";	
		System.out.println(originalFile);
		MarketShareWeeklyReportReader rct = new MarketShareWeeklyReportReader(originalFile, Dimensions.HOTEL);
		rct.setDataByDimension(Dimensions.HOTEL);
		System.out.println(rct.getRecordCount());
		System.out.println(rct.getHotels());
		System.out.println(rct.getData());
	}
}
