package mstrRestClient.endpoints;

import java.util.logging.Logger;

import base.Properties;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.specification.RequestSpecification;

public class ClientBase {	
	
	protected static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	protected static String baseURI;

	public ClientBase() {
		baseURI = Properties.environmentProperties.get("mstrApiBaseUrl");
	}
	
	protected RequestSpecification setClientBase(RequestSpecification request) {
		setBaseUri();	
		request = RestAssured.given();
		setRequestConfig(request);
		request.given().log().all();
		return request;
	}
	
	protected void setBaseUri() {
		RestAssured.baseURI = baseURI;	
		LOGGER.info("base uri for the request set to: "+baseURI);
	}
	
	protected RequestSpecification setRequestConfig(RequestSpecification request) {
		request.given().
        config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false))).
        contentType("application/json");
		LOGGER.info("Request configured to not add any charset");
		return request;
	}
	
	protected RequestSpecification setAuthAndSessionHeaders(RequestSpecification request, String authToken, String sessionId) {
		request.header("X-MSTR-AuthToken", authToken);
		request.header("Cookie", sessionId);
		return request;
	}
}
