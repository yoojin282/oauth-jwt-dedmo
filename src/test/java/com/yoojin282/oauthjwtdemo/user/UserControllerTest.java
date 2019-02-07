package com.yoojin282.oauthjwtdemo.user;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.yoojin282.oauthjwtdemo.conf.JWTServerConfig;
import com.yoojin282.oauthjwtdemo.conf.ResourceServerConfig;
import com.yoojin282.oauthjwtdemo.conf.SecurityConfig;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@Import({SecurityConfig.class, ResourceServerConfig.class, JWTServerConfig.class})
public class UserControllerTest {
	@Autowired
	MockMvc mvc;
	
	@Test
	public void testMe() throws Exception {
		String token = obtainAccessToken("testuser", "testpassword");
		mvc.perform(get("/users/me")
				.header("Authorization", "Bearer " + token)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
	}

	private String obtainAccessToken(String username, String password) throws Exception {
		  
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("grant_type", "password");
	    params.add("username", username);
	    params.add("password", password);
	 
	    ResultActions result = mvc.perform(post("/oauth/token")
	        .params(params)
	        .with(httpBasic("test-client-id","test-client-secret"))
	        .accept("application/json;charset=UTF-8"))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType("application/json;charset=UTF-8"));
	 
	    String resultString = result.andReturn().getResponse().getContentAsString();
	 
	    JacksonJsonParser jsonParser = new JacksonJsonParser();
	    return jsonParser.parseMap(resultString).get("access_token").toString();
	}

}
