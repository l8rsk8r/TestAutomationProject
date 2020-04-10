package mstrReportReader;

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
	
	public void setDataByHotelName() throws IOException {
		String[] columnHeadings;
		readTempFile();
		String line = bufferedReader.readLine();
		line = line.replace("\"", "");
		columnHeadings = line.split(",");
		int rowNumber = 0;
		while ((line=bufferedReader.readLine()) != null) {
			String[] dataSet = line.split("\",\"");
			String hotel = null;
			hotel = dataSet[0].replace("\"", "");
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
					dataRecord.put("quadrant", dataSet[cellNumber]);
					break;
				case "TY OCI":
					dataRecord.put("occupancy-index", dataSet[cellNumber]);
					break;
				case "Hotel Count":
					dataRecord.put("hotel-count", dataSet[cellNumber]);
					break;
				case "YoY OCI":
					dataRecord.put("yoy-occupancy-index", dataSet[cellNumber]);
					break;
				case "TY ARI":
					dataRecord.put("average-daily-rate-index", dataSet[cellNumber]);
					break;
				case "YoY ARI":
					dataRecord.put("yoy-average-daily-rate-index", dataSet[cellNumber]);
					break;
				case "TY RPI":
					dataRecord.put("revenue-per-available-room-index", dataSet[cellNumber]);
					break;
				case "YoY RPI":
					dataRecord.put("yoy-revenue-per-available-room-index", dataSet[cellNumber]);
					break;
				case "RPI Impact":
					dataRecord.put("revenue-per-available-room-impact", convertToCompleteValues(dataSet[cellNumber].replace("\"", "")));
					break;
				}
				cellNumber++;
			}	
			data.add(dataRecord);
			rowNumber++;
		}
		bufferedReader.close();
	}
	
	private String convertToCompleteValues(String value) {
		if (value.contains("K") && value.contains("(")) {
			String[] split$ = value.split("\\$");
			String[] splitK = split$[1].split("K");
			Float valueI = Float.parseFloat(splitK[0]) * 1000;
			value = valueI.toString();
			value = split$[0]+"$"+value+splitK[1];
			return value;
		} else if (value.contains("K")) {
			String[] split$ = value.split("\\$");
			String[] splitK = split$[1].split("K");
			Float valueI = Float.parseFloat(splitK[0]) * 1000;
			value = valueI.toString();
			value = "$"+value;
			return value;
		} else if (value.contains("M") && value.contains("(")) {
			String[] split$ = value.split("\\$");
			String[] splitM = split$[1].split("M");
			Float valueI = Float.parseFloat(splitM[0]) * 1000000;
			value = valueI.toString();
			value = split$[0]+"$"+value+splitM[1];
			return value;
		} else if (value.contains("M")) {
			String[] split$ = value.split("\\$");
			String[] splitM = split$[1].split("M");
			Float valueI = Float.parseFloat(splitM[0]) * 1000000;
			value = valueI.toString();
			value = "$"+value;
			return value;
		}
		return value;
	}
	
	public static void main(String[] args) throws Exception {
		String userPath = System.getProperty("user.dir");
		String originalFile = userPath+"/src/test/resources/deleteme/MarketShareMstr.csv";	
		System.out.println(originalFile);
		MarketShareMonthlyReportReader rct = new MarketShareMonthlyReportReader(originalFile);
		rct.setDataByHotelName();
		System.out.println(rct.getRecordCount());
		System.out.println(rct.getHotels());
		System.out.println(rct.getData());
	}
}
