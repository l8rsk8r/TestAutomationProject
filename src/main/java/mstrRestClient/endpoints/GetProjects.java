package mstrRestClient.endpoints;

import java.util.HashMap;
import java.util.List;

import exceptions.InvalidResponseException;
import mstrRestClient.endpoints.PostAuthLogin.AuthHeaders;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetProjects extends ClientBase {
	
	public Response getProjectsRequest(String authToken, String sessionId) throws InvalidResponseException {
		Response response;
		RequestSpecification request = null;
		request = setClientBase(request);
		request = setAuthAndSessionHeaders(request, authToken, sessionId);
		response = request.given().get("/projects");
		int responseCode = response.getStatusCode();
		if (responseCode != 200) {
			throw new InvalidResponseException("the reponse code was "+responseCode+" but expected 204");
		}
		return response;
	}
	
	public HashMap<String, String> getProjectIds(String authToken, String sessionId) throws InvalidResponseException {
		HashMap<String, String> projectToIdMap = new HashMap<String, String>();
		Response response = getProjectsRequest(authToken, sessionId);
		List<HashMap<String, String>> projectsInfo = response.jsonPath().getList(".");
		for (HashMap<String, String> projectInfo: projectsInfo) {
			String projectId = projectInfo.get("id");
			String projectName = projectInfo.get("name");
			projectToIdMap.put(projectName, projectId);
		}
		return projectToIdMap;
	}
	
	public String getProjectId(String projectName, String authToken, String sessionId) throws InvalidResponseException {
		HashMap<String, String> projectToIdMap = getProjectIds(authToken, sessionId);
		return projectToIdMap.get(projectName);
	}
	
	public static void main(String[] args) throws InvalidResponseException {
		PostAuthLogin authentication = new PostAuthLogin();
		HashMap<AuthHeaders, String> authResponse = authentication.getAuthTokenAndSession();
		String authToken = authResponse.get(AuthHeaders.AUTH_TOKEN);
		String sessionId = authResponse.get(AuthHeaders.SESSION_ID);
		GetProjects projects = new GetProjects();
		HashMap<String, String> map = projects.getProjectIds(authToken, sessionId);
		System.out.println(map.get(""));
	}
}
