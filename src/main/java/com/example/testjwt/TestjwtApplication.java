package com.example.testjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@SpringBootApplication
public class TestjwtApplication 
{
	private static final String GET_TOKEN = "http://localhost:8080/token/generate-token";
	private static final String GET_USERS_1 = "http://localhost:8080/users/{id}";
	private static final int AuthToken = 0;
	//
	private static RestTemplate restTemplate = new RestTemplate();
	public static UserDto getUser(String id)
	{
		UserDto u = new UserDto();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(getToken());
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		Map <String, String> params = new HashMap<String, String>();
		params.put("id", id);
		ResponseEntity<UserDto> userDtoResponse = restTemplate.exchange(GET_USERS_1, HttpMethod.GET, entity, UserDto.class, params);
		u = userDtoResponse.getBody();
		if (u != null) 
		{
			return u;
		} 
		else 
		{
			u = null;
		}
		return u;
	}
	public static String getToken()
	{
		String token = "";
		JSONObject request = new JSONObject();
		request.put("username", "alex123");
		request.put("password", "alex123");
		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(request.toString(), headers);
		// send request and parse result
		ResponseEntity<ApiResponse> loginResponse = restTemplate.exchange(GET_TOKEN, HttpMethod.POST, entity, ApiResponse.class);
		ApiResponse ar = new ApiResponse();
		ar = loginResponse.getBody();
		if (ar.getStatus() == 200) 
		{
			token = ar.getToken();
		} 
		else 
		{
			token = "";
		}
		return token;
	}
	public static void main(String[] args) 
	{
		SpringApplication.run(TestjwtApplication.class, args);
		System.out.println(getToken());
		System.out.println(getUser("1").getId() + " " + getUser("1").getFirstName() + " " + getUser("1").getAge() + " " + getUser("1").getSalary() + " " + getUser("1").getUsername());
	}
	//
	
}
