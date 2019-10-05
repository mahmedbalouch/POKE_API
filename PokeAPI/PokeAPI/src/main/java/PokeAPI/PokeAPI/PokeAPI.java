package PokeAPI.PokeAPI;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyData;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;



public class PokeAPI {
	
	 private String BASE_URL = "https://pokeapi.co/api/v2/pokemon";
	 private String POKE_NAME="overgrow";
	 private String Last_POKE_NAME="raticate";
	 private String ABILITY_NAME="run-away";
	 private int STATUS_CODE=200;
	 private RequestSpecification httpRequest;
	
	 public PokeAPI(){
		 RestAssured.baseURI = BASE_URL;
		 this.httpRequest = RestAssured.given();
	 }
	 
	@Test
	public void pokeByID(){
		Response response = httpRequest.request(Method.GET,"/1");
		String responseBody=response.getBody().asString();
		int responseCode=response.getStatusCode();
		//validating response status code
		Assert.assertEquals(STATUS_CODE,responseCode);
		JsonPath jp = new JsonPath(responseBody);
		Assert.assertEquals(POKE_NAME, jp.get("abilities.ability[1].name"));
	}

	@Test
	public void pokePaginatioin(){
		Response response = httpRequest.request(Method.GET,"?offset=10&limit=10");
		String responseBody=response.getBody().asString();
		int responseCode=response.getStatusCode();
		//validating response status code
		Assert.assertEquals(STATUS_CODE,responseCode);
		JsonPath jp = new JsonPath(responseBody);
		//validate item in last object
		Assert.assertEquals(Last_POKE_NAME, jp.get("results.name[9]"));
	}
	
	@Test
	public void pokeByName(){
		Response response = httpRequest.request(Method.GET,"/caterpie");
		String responseBody=response.getBody().asString();
		int responseCode=response.getStatusCode();
		//validating response status code
		Assert.assertEquals(STATUS_CODE,responseCode);
		JsonPath jp = new JsonPath(responseBody);
		Assert.assertEquals(ABILITY_NAME,jp.get("abilities.ability[0].name"));
		Assert.assertEquals("https://pokeapi.co/api/v2/ability/50/",jp.get("abilities.ability[0].url"));
	}
}