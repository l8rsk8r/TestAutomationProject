package mstrRestClient.scenarioSteps;

import java.util.HashMap;

import exceptions.InvalidResponseException;
import mstrRestClient.endpoints.GetDossierDefinition;
import mstrRestClient.endpoints.GetDossierVisualization;
import mstrRestClient.endpoints.GetProjects;
import mstrRestClient.endpoints.PostAuthLogin;
import mstrRestClient.endpoints.PostDossierInstance;
import mstrRestClient.endpoints.PostAuthLogin.AuthHeaders;
import utils.StringHelper;

import io.restassured.response.Response;

public class GetDossiersData {

	StringHelper stringHelper = new StringHelper();
	
	PostAuthLogin authentication = new PostAuthLogin();
	GetProjects getProjects = new GetProjects();
	GetDossierDefinition getDossierDefinition = new GetDossierDefinition();
	PostDossierInstance postDossierInstance = new PostDossierInstance();
	GetDossierVisualization getDossierVisualizationData = new GetDossierVisualization();

	public Response getVisualization(String projectName, String dossierId, String chapterName, String pageName, String visualization) throws InvalidResponseException {
		HashMap<AuthHeaders, String> authResponse = authentication.getAuthTokenAndSession();
		String authToken = authResponse.get(AuthHeaders.AUTH_TOKEN);
		String sessionId = authResponse.get(AuthHeaders.SESSION_ID);
		String projectId = getProjects.getProjectId(projectName, authToken, sessionId);
		HashMap<String, String> dossierInfo = getDossierDefinition.getVisualizationsKey(projectId, dossierId, chapterName, pageName, visualization, authToken, sessionId);
		String chapterKey = dossierInfo.get("chapterKey");
		String visualizationKey = dossierInfo.get("visualizationKey");
		String instanceId = postDossierInstance.getDossierInstanceId(projectId, dossierId, authToken, sessionId);
		return getDossierVisualizationData.getDossierVisualizationDataRequest(projectId, dossierId, instanceId, chapterKey, visualizationKey, authToken, sessionId);
	}
	
	
	public static void main(String[] args) throws InvalidResponseException, IllegalArgumentException, IllegalAccessException {
		GetDossiersData dd = new GetDossiersData();
		Response response = dd.getVisualization("Revenue Management QA", "3AAB381F11E97D88438F0080EF65B039", "Weekly Market Share", "Total Summary - Indexes", "Visualization 1");
		Response response2 = dd.getVisualization("CELP", "A98315AC11E9FB4D28BA0080EF2539E4", "Total Marketable Universe", "Monthly Trend", "Monthly Trend of Marketable Universe");
		System.out.println(response.asString());
		System.out.println(response2.asString());
	}
}
