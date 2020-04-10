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

import utils.fileHelpers.CsvReportReader;

public class MarketShareMonthlyReportReader extends CsvReportReader{

	InputStream reader;
	BufferedReader bufferedReader;
	private HashMap<String, String> dataRecord;
	private List<HashMap<String, String>> data = new ArrayList<HashMap<String,String>>();
	private int recordCount = 0;
	private ArrayList<String> hotels = new ArrayList<String>();
	
	public List<HashMap<String, String>> getData(){
		return data;
	}
	
	public int getRecordCount() {
		return recordCount;
	}
	
	public ArrayList<String> getHotels() {
		return hotels;
	}
	
	public MarketShareMonthlyReportReader(String inputFilePath) throws Exception {
		super(inputFilePath);
		setDataByHotelName();
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
	
	public void setDataByHotelName() throws IOException {
		String[] columnHeadings;
		readTempFile();
		String line = bufferedReader.readLine();
		columnHeadings = line.split("\t");
		int rowNumber = 0;
		while ((line=bufferedReader.readLine()) != null) {
			String[] dataSet = line.split("\t");
			String hotel = null;
			hotel = hotelNamesMappedToMstr(dataSet[0]);
			if (!(hotel.contains("Total"))) {
				hotels.add(hotel);
				recordCount = rowNumber+1;
			}
			int cellNumber = 0;
			dataRecord = new HashMap<String, String>();
			dataRecord.put("hotel", hotel);
			for (String columnHeading: columnHeadings) {
				switch(columnHeading) {
				case "Quadrant":
					dataRecord.put("quadrant", negativeValuesMappedToMstr(dataSet[cellNumber]));
					break;
				case "OCI":
					dataRecord.put("occupancy-index", negativeValuesMappedToMstr(dataSet[cellNumber]));
					break;
				case "Hotel Count":
					dataRecord.put("hotel-count", negativeValuesMappedToMstr(dataSet[cellNumber]));
					break;
				case "YoY OCI":
					dataRecord.put("yoy-occupancy-index", negativeValuesMappedToMstr(dataSet[cellNumber]));
					break;
				case "ADRI":
					dataRecord.put("average-daily-rate-index", negativeValuesMappedToMstr(dataSet[cellNumber]));
					break;
				case "YoY ARI":
					dataRecord.put("yoy-average-daily-rate-index", negativeValuesMappedToMstr(dataSet[cellNumber]));
					break;
				case "RPI":
					dataRecord.put("revenue-per-available-room-index", negativeValuesMappedToMstr(dataSet[cellNumber]));
					break;
				case "YoY RPI":
					dataRecord.put("yoy-revenue-per-available-room-index", negativeValuesMappedToMstr(dataSet[cellNumber]));
					break;
				case "RevPAR Impact":
					dataRecord.put("revenue-per-available-room-impact", negativeValuesMappedToMstr(dataSet[cellNumber]));
					break;
				}
				cellNumber++;
			}	
			data.add(dataRecord);
			rowNumber++;
		}
		bufferedReader.close();
	}
	
	public static void main(String[] args) throws Exception {
		String userPath = System.getProperty("user.dir");
		String originalFile = userPath+"/src/test/resources/deleteme/MarketShareTableau.csv";	
		System.out.println(originalFile);
		MarketShareMonthlyReportReader rct = new MarketShareMonthlyReportReader(originalFile);
		rct.setDataByHotelName();
		System.out.println(rct.getRecordCount());
		System.out.println(rct.getHotels());
		System.out.println(rct.getData());
	}
}
