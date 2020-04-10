package mstrRestClient.endpoints;

import java.util.ArrayList;
import java.util.HashMap;

import exceptions.InvalidResponseException;
import mstrRestClient.endpoints.PostAuthLogin.AuthHeaders;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetDossierVisualization extends ClientBase {
	
	public String getDossierVisualizationDataRequestLimit(String projectId, String dossierId, String instanceId, String chapterKey, String visualizationKey, String authToken, String sessionId) throws InvalidResponseException {
		Response response = null;
		RequestSpecification request = null;
		request = setClientBase(request);
		request = setAuthAndSessionHeaders(request, authToken, sessionId);
		request.given().header("X-MSTR-ProjectID", projectId);
		String resourceParams = "/dossiers/"+dossierId+"/instances/"+instanceId+"/chapters/"+chapterKey+"/visualizations/"+visualizationKey+"?limit=0";
		response = request.given().get(resourceParams);
		int responseCode = response.getStatusCode();
		if (responseCode != 200) {
			throw new InvalidResponseException("the reponse code was "+responseCode+" but expected 200");
		}
		int totalNumberOfDataRows = response.jsonPath().get("result.data.paging.total");
		int totalNumberOfDataRowsExcludingTotal = totalNumberOfDataRows-1;
		String limit = Integer.toString(totalNumberOfDataRowsExcludingTotal);
		return limit;
	}	
	
	//TODO: add limit -1 here and add a boolean to remove last set for limit
	public Response getDossierVisualizationDataRequest(String projectId, String dossierId, String instanceId, String chapterKey, String visualizationKey, String authToken, String sessionId) throws InvalidResponseException {
		Response response = null;
		RequestSpecification request = null;
		request = setClientBase(request);
		request = setAuthAndSessionHeaders(request, authToken, sessionId);
		String limit = getDossierVisualizationDataRequestLimit(projectId, dossierId, instanceId, chapterKey, visualizationKey, authToken, sessionId);
		request.given().header("X-MSTR-ProjectID", projectId);
		String resourceParams = "/dossiers/"+dossierId+"/instances/"+instanceId+"/chapters/"+chapterKey+"/visualizations/"+visualizationKey+"?limit="+limit;
		response = request.given().get(resourceParams);
		int responseCode = response.getStatusCode();
		if (responseCode != 200) {
			throw new InvalidResponseException("the reponse code was "+responseCode+" but expected 200");
		}
		return response;
	}		
	
	public ArrayList<String> getVisualizationData(String projectId, String dossierId, String instanceId, String chapterKey, String visualizationKey, String authToken, String sessionId) throws InvalidResponseException {
		ArrayList<String> data = new ArrayList<String>();
		Response response = getDossierVisualizationDataRequest(projectId, dossierId, instanceId, chapterKey, visualizationKey, authToken, sessionId);
		System.out.println(response.asString());
		int childrenSize = response.jsonPath().getList("result.data.root.children").size();
		System.out.println("brand"+","+"hotelCount"+","+"quadrant"+","+"occupancyIndex"+","+"yoyOccupancyIndex,averageDailyRateIndex,yoyAverageDailyRateIndex"+","+"revenuePerAvailableRoomIndex"+","+"yoyRevenuePerAvailableRoomIndex"+","+"revenuePerAvailableRoomImpact");
		data.add("brand"+","+"hotelCount"+","+"quadrant"+","+"occupancyIndex"+","+"yoyOccupancyIndex,averageDailyRateIndex,yoyAverageDailyRateIndex"+","+"revenuePerAvailableRoomIndex"+","+"yoyRevenuePerAvailableRoomIndex"+","+"revenuePerAvailableRoomImpact");
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
			System.out.println(brand+","+hotelCount.toString().replace(",", "")+","+quadrant+","+occupancyIndex+","+yoyOccupancyIndex+","+averageDailyRateIndex+","+yoyAverageDailyRateIndex+","+revenuePerAvailableRoomIndex+","+yoyRevenuePerAvailableRoomIndex+","+revenuePerAvailableRoomImpact);
			data.add(brand+","+hotelCount.toString().replace(",", "")+","+quadrant+","+occupancyIndex+","+yoyOccupancyIndex+","+averageDailyRateIndex+","+yoyAverageDailyRateIndex+","+revenuePerAvailableRoomIndex+","+yoyRevenuePerAvailableRoomIndex+","+revenuePerAvailableRoomImpact);
		}
		return data;
	}
	
	public static void main(String[] args) throws InvalidResponseException, IllegalArgumentException, IllegalAccessException {
		PostAuthLogin authentication = new PostAuthLogin();
		HashMap<AuthHeaders, String> authResponse = authentication.getAuthTokenAndSession();
		String authToken = authResponse.get(AuthHeaders.AUTH_TOKEN);
		String sessionId = authResponse.get(AuthHeaders.SESSION_ID);
		String projectName = "Revenue Management QA";
		String dossierId = "";
		String chapterName = "Weekly Market Share copy ";
		String pageName = "Total Summary - Indexes";
		String visualization = "Visualization 1";
		GetProjects p = new GetProjects();
		String projectId = p.getProjectId(projectName, authToken, sessionId);
		GetDossierDefinition d = new GetDossierDefinition();
		HashMap<String, String> dossierInfo = d.getVisualizationsKey(projectId, dossierId, chapterName, pageName, visualization, authToken, sessionId);
		String chapterKey = dossierInfo.get("chapterKey");
		String visualizationKey = dossierInfo.get("visualizationKey");
		PostDossierInstance pdi = new PostDossierInstance();
		String instanceId = pdi.getDossierInstanceId(projectId, dossierId, authToken, sessionId);
		GetDossierVisualization gdvd = new GetDossierVisualization();
		gdvd.getVisualizationData(projectId, dossierId, instanceId, chapterKey, visualizationKey, authToken, sessionId);
	}
}
