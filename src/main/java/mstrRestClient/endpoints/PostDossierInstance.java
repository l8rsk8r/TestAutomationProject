package mstrRestClient.endpoints;

import java.util.HashMap;

import exceptions.InvalidResponseException;
import mstrRestClient.endpoints.PostAuthLogin.AuthHeaders;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostDossierInstance extends ClientBase {
	
	public Response postDossierInstanceRequest(String projectId, String dossierId, String authToken, String sessionId) throws InvalidResponseException {
		Response response = null;
		RequestSpecification request = null;
		request = setClientBase(request);
		request = setAuthAndSessionHeaders(request, authToken, sessionId);
		request.given().header("X-MSTR-ProjectID", projectId);
		String resourceParams = "/dossiers/"+dossierId+"/instances";
		response = request.given().post(resourceParams);
		int responseCode = response.getStatusCode();
		if (responseCode != 201) {
			throw new InvalidResponseException("the reponse code was "+responseCode+" but expected 201");
		}
		System.out.println(response.asString());
		return response;
	}
	
	public String getDossierInstanceId(String projectId, String dossierId, String authToken, String sessionId) throws InvalidResponseException {
		Response response = postDossierInstanceRequest(projectId, dossierId, authToken, sessionId);
		String dossierInstanceId = response.jsonPath().get("mid");
		return dossierInstanceId;
	}
	
	
	public static void main(String[] args) throws InvalidResponseException, IllegalArgumentException, IllegalAccessException {
		PostAuthLogin authentication = new PostAuthLogin();
		HashMap<AuthHeaders, String> authResponse = authentication.getAuthTokenAndSession();
		String authToken;
		String sessionId;
		authToken = authResponse.get(AuthHeaders.AUTH_TOKEN);
		sessionId = authResponse.get(AuthHeaders.SESSION_ID);
		GetProjects p = new GetProjects();
		String projectId = p.getProjectId("", authToken, sessionId);
		PostDossierInstance pdi = new PostDossierInstance();
		String dossierInstanceId = pdi.getDossierInstanceId(projectId, "", authToken, sessionId);
		System.out.println(dossierInstanceId);
	}
}
