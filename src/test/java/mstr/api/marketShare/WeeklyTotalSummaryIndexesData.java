package mstr.api.marketShare;

import exceptions.InvalidResponseException;
import io.restassured.response.Response;
import mstrRestClient.scenarioSteps.GetDossiersData;
import utils.CollectionsHelper;

import java.util.HashMap;

public class WeeklyTotalSummaryIndexesData extends GetDossiersData {

	Response response;
	public HashMap<String, String> responseData = new HashMap<String, String>();
	public HashMap<String, String> weightedResponseData = new HashMap<String, String>();
	public HashMap<String, String> nonWeightedResponseData = new HashMap<String, String>();

	CollectionsHelper collectionsHelper = new CollectionsHelper();
	
	public Response getResponse() throws InvalidResponseException {
		response = getVisualization("Revenue Management QA", "3AAB381F11E97D88438F0080EF65B039", "Weekly Market Share", "Total Summary - Indexes", "Visualization 1");
		return response;
	}
	
	public void getRawData() throws InvalidResponseException {
		Response response = getResponse();
		int childrenSize = response.jsonPath().getList("result.data.root.children").size();
		for (int i = 0; i < childrenSize; i++) {	
			String brand = response.jsonPath().get("result.data.root.children["+i+"].element.name");
			String hotelCount = response.jsonPath().get("result.data.root.children["+i+"].metrics.'Hotel Count'.fv");
			String quadrant = response.jsonPath().get("result.data.root.children["+i+"].metrics.Quadrant.fv");
			Float occupancyIndex = response.jsonPath().get("result.data.root.children["+i+"].metrics.'TY OCI'.rv");
			Float yoyOccupancyIndex = response.jsonPath().get("result.data.root.children["+i+"].metrics.'YoY OCI'.rv");
			Float averageDailyRateIndex = response.jsonPath().get("result.data.root.children["+i+"].metrics.'TY ARI'.rv");
			Float yoyAverageDailyRateIndex = response.jsonPath().get("result.data.root.children["+i+"].metrics.'YoY ARI'.rv");
			Float revenuePerAvailableRoomIndex = response.jsonPath().get("result.data.root.children["+i+"].metrics.'TY RPI'.rv");
			Float yoyRevenuePerAvailableRoomIndex = response.jsonPath().get("result.data.root.children["+i+"].metrics.'YoY RPI'.rv");
			Float revenuePerAvailableRoomImpact = response.jsonPath().get("result.data.root.children["+i+"].metrics.'RPI Impact'.rv");
			responseData.put(brand+"HotelCount", hotelCount);
			responseData.put(brand+"Quadrant", quadrant);
			responseData.put(brand+"occupancyIndex", Float.toString(occupancyIndex));
			responseData.put(brand+"yoyOccupancyIndex", Float.toString(yoyOccupancyIndex));
			responseData.put(brand+"averageDailyRateIndex", Float.toString(averageDailyRateIndex));
			responseData.put(brand+"yoyAverageDailyRateIndex", Float.toString(yoyAverageDailyRateIndex));
			responseData.put(brand+"revenuePerAvailableRoomIndex", Float.toString(revenuePerAvailableRoomIndex));
			responseData.put(brand+"yoyRevenuePerAvailableRoomIndex", Float.toString(yoyRevenuePerAvailableRoomIndex));
			responseData.put(brand+"revenuePerAvailableRoomImpact", Float.toString(revenuePerAvailableRoomImpact));
			if (Integer.parseInt(hotelCount.replace(",", "")) > 1) {
				weightedResponseData.put(brand+"HotelCount", hotelCount);
				weightedResponseData.put(brand+"Quadrant", quadrant);
				weightedResponseData.put(brand+"occupancyIndex", Float.toString(occupancyIndex));
				weightedResponseData.put(brand+"yoyOccupancyIndex", Float.toString(yoyOccupancyIndex));
				weightedResponseData.put(brand+"averageDailyRateIndex", Float.toString(averageDailyRateIndex));
				weightedResponseData.put(brand+"yoyAverageDailyRateIndex", Float.toString(yoyAverageDailyRateIndex));
				weightedResponseData.put(brand+"revenuePerAvailableRoomIndex", Float.toString(revenuePerAvailableRoomIndex));
				weightedResponseData.put(brand+"yoyRevenuePerAvailableRoomIndex", Float.toString(yoyRevenuePerAvailableRoomIndex));
				weightedResponseData.put(brand+"revenuePerAvailableRoomImpact", Float.toString(revenuePerAvailableRoomImpact));
			} else {
				nonWeightedResponseData.put(brand+"HotelCount", hotelCount);
				nonWeightedResponseData.put(brand+"Quadrant", quadrant);
				nonWeightedResponseData.put(brand+"occupancyIndex", Float.toString(occupancyIndex));
				nonWeightedResponseData.put(brand+"yoyOccupancyIndex", Float.toString(yoyOccupancyIndex));
				nonWeightedResponseData.put(brand+"averageDailyRateIndex", Float.toString(averageDailyRateIndex));
				nonWeightedResponseData.put(brand+"yoyAverageDailyRateIndex", Float.toString(yoyAverageDailyRateIndex));
				nonWeightedResponseData.put(brand+"revenuePerAvailableRoomIndex", Float.toString(revenuePerAvailableRoomIndex));
				nonWeightedResponseData.put(brand+"yoyRevenuePerAvailableRoomIndex", Float.toString(yoyRevenuePerAvailableRoomIndex));
				nonWeightedResponseData.put(brand+"revenuePerAvailableRoomImpact", Float.toString(revenuePerAvailableRoomImpact));
			}
		}
	}
	
	public void formatData(int decimalPrecision) {
		responseData = collectionsHelper.changeDecimalPrecisionOfHashMapValues(responseData, decimalPrecision);
		weightedResponseData = collectionsHelper.changeDecimalPrecisionOfHashMapValues(weightedResponseData, decimalPrecision);
		nonWeightedResponseData = collectionsHelper.changeDecimalPrecisionOfHashMapValues(nonWeightedResponseData, decimalPrecision);

	}

	public static void main(String[] args) throws InvalidResponseException {
		WeeklyTotalSummaryIndexesData marketShareData = new WeeklyTotalSummaryIndexesData();
		marketShareData.getRawData();
		marketShareData.formatData(2);
		HashMap<String, String> data = marketShareData.responseData;
		for (String key: data.keySet()) {
			System.out.println(key+"\t"+data.get(key));
		}
	}

}
