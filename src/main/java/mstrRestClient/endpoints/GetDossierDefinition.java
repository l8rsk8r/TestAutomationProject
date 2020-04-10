package mstrRestClient.endpoints;

import java.util.HashMap;

import exceptions.InvalidResponseException;
import mstrRestClient.endpoints.PostAuthLogin.AuthHeaders;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetDossierDefinition extends ClientBase {
	
	public Response getDossierDefinitionRequest(String projectId, String dossierId, String authToken, String sessionId) throws InvalidResponseException {
		Response response = null;
		RequestSpecification request = null;
		request = setClientBase(request);
		request = setAuthAndSessionHeaders(request, authToken, sessionId);
		request.given().header("X-MSTR-ProjectID", projectId);
		String resourceParams = "/dossiers/"+dossierId+"/definition";
		response = request.given().get(resourceParams);
		int responseCode = response.getStatusCode();
		if (responseCode != 200) {
			throw new InvalidResponseException("the reponse code was "+responseCode+" but expected 204");
		}
		return response;
	}
	
	public HashMap<String, String> getVisualizationsKey(String projectId, String dossierId, String chapterName, String pageName, String visualizationName, String authToken, String sessionId) throws InvalidResponseException {
		Response response = getDossierDefinitionRequest(projectId, dossierId, authToken, sessionId);
		System.out.println(response.asString());
		String chapterKey = null;
		String visualizationKey = null;
		HashMap<String, String> visualizationInfo = new HashMap<String, String>();
		int totalChapters = response.jsonPath().getList("chapters").size();
		int chapterIndex;
		for (chapterIndex = 0; chapterIndex < totalChapters; chapterIndex ++) {
			String responseChapterName = response.jsonPath().get("chapters["+chapterIndex+"].name");
			if (responseChapterName.equalsIgnoreCase(chapterName)) {
				chapterKey = response.jsonPath().get("chapters["+chapterIndex+"].key");
				break;
			}
		}		
		int totalPagesForChapter = response.jsonPath().getList("chapters["+chapterIndex+"].pages").size();
		int pageIndex;
		for (pageIndex = 0; pageIndex < totalPagesForChapter; pageIndex++) {
			String responsePageName = response.jsonPath().get("chapters["+chapterIndex+"].pages["+pageIndex+"].name");
			if (responsePageName.equalsIgnoreCase(pageName)) {
				break;
			}
		}		
		int totalVisualizationsForPage = response.jsonPath().getList("chapters["+chapterIndex+"].pages["+pageIndex+"].visualizations").size();
		int visualizationIndex;
		for (visualizationIndex = 0; visualizationIndex < totalVisualizationsForPage; visualizationIndex++) {
			String responseVisualizationName = response.jsonPath().get("chapters["+chapterIndex+"].pages["+pageIndex+"].visualizations["+visualizationIndex+"].name");
			if (responseVisualizationName.equalsIgnoreCase(visualizationName)) {
				visualizationKey = response.jsonPath().get("chapters["+chapterIndex+"].pages["+pageIndex+"].visualizations["+visualizationIndex+"].key");
				break;
			}
		}
		visualizationInfo.put("chapterKey", chapterKey);
		visualizationInfo.put("visualizationKey", visualizationKey);
		return visualizationInfo;
	}
	
	public static void main(String[] args) throws InvalidResponseException, IllegalArgumentException, IllegalAccessException {
		PostAuthLogin authentication = new PostAuthLogin();
		HashMap<AuthHeaders, String> authResponse = authentication.getAuthTokenAndSession();
		String authToken;
		String sessionId;
		authToken = authResponse.get(AuthHeaders.AUTH_TOKEN);
		sessionId = authResponse.get(AuthHeaders.SESSION_ID);
		GetProjects p = new GetProjects();
		String projectId = p.getProjectId("Revenue Management QA", authToken, sessionId);
		GetDossierDefinition d = new GetDossierDefinition();
		HashMap<String, String> dossierKey = d.getVisualizationsKey(projectId, "3AAB381F11E97D88438F0080EF65B039", "Weekly Market Share copy ", "Total Summary - Indexes", "Visualization 1", authToken, sessionId);
		System.out.println(dossierKey);
	}
}
