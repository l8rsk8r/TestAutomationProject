package mstrRestClient.endpoints;

import java.util.HashMap;
import java.util.logging.Logger;

import base.Properties;
import exceptions.InvalidResponseException;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PostAuthLogin extends ClientBase {
	
	protected static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private String username;
	private String password;
	
	public PostAuthLogin() {
		setCredentials();
	}
	
	public enum AuthHeaders {
		AUTH_TOKEN,
		SESSION_ID
	}
	
	private void setCredentials() {
		username = Properties.environmentProperties.get("mstrApiServiceAccountUsername");
		password = Properties.environmentProperties.get("mstrApiServiceAccountPassword");
		LOGGER.info("values set for username and password");
	}
	
	private String getLoginBody() {
		String loginMode = "16";
		String body =  "{\n" + 
				"	\"username\": \""+username+"\",\n" + 
				"	\"password\": \""+password+"\",\n" + 
				"	\"loginMode\": 1\n" + 
				"}";
		LOGGER.info("Request body for auth/login set to use "+loginMode+" as the login mode");
		return body;
	}
	
	public Response postAuthLoginRequest() throws InvalidResponseException {
		Response response;
		RequestSpecification request = null;
		request = setClientBase(request);
		request.given().header("Content-Type", "application/json");
		request.given().body(getLoginBody());
		response = request.given().post("/auth/login");
		int responseCode = response.getStatusCode();
		if (responseCode != 204) {
			throw new InvalidResponseException("the reponse code was "+responseCode+" but expected 204");
		}
		return response;
	}
	
	public HashMap<AuthHeaders, String> getAuthTokenAndSession() throws InvalidResponseException {
		HashMap<AuthHeaders, String> authInfo = new HashMap<AuthHeaders, String>();
		Response response = postAuthLoginRequest();
		String authToken = response.getHeader("X-MSTR-AuthToken");
		String setCookie = response.getHeader("Set-Cookie");
		setCookie = setCookie.split(";")[0];
		authInfo.put(AuthHeaders.AUTH_TOKEN, authToken);
		authInfo.put(AuthHeaders.SESSION_ID, setCookie);
		return authInfo;
	}
	
	public static void main(String[] args) throws InvalidResponseException {
		PostAuthLogin a = new PostAuthLogin();
		HashMap<AuthHeaders, String> token = a.getAuthTokenAndSession();
		System.out.println("and the token is "+token.get(AuthHeaders.SESSION_ID));
	}
}
