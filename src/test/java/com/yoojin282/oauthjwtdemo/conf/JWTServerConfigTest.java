package com.yoojin282.oauthjwtdemo.conf;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.yoojin282.oauthjwtdemo.helllo.HelloController;

@RunWith(SpringRunner.class)
@WebMvcTest(value= {HelloController.class})
@Import({SecurityConfig.class, JWTServerConfig.class})
public class JWTServerConfigTest {
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void testPassword() throws Exception {
		String accessToken = obtainAccessToken("testuser", "testpassword").get("access_token").toString();
		assertThat(accessToken).isNotBlank();
	}
	
	@Test
	public void testClientCredentials() throws Exception {
		mvc.perform(post("/oauth/token")
				.with(httpBasic("test-client-id", "test-client-secret"))
				.param("grant_type", "client_credentials"))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testGetTokenKey() throws Exception {
		mvc.perform(get("/oauth/token_key")
				.with(httpBasic("test-client-id", "test-client-secret")))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testRefreshToken() throws Exception {
		
		Map<String, Object> result = obtainAccessToken("testuser", "testpassword");
		String resultString = mvc.perform(post("/oauth/token")
				.with(httpBasic("test-client-id", "test-client-secret"))
				.param("grant_type", "refresh_token")
				.param("refresh_token", result.get("refresh_token").toString()))
			.andExpect(status().isOk())
			.andDo(print())
			.andReturn().getResponse().getContentAsString();
		
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		Map<String, Object> tokenMap = jsonParser.parseMap(resultString);
		assertThat(tokenMap.get("access_token").toString()).isNotEqualTo(result.get("access_token").toString());
	}
	
	
	
	private Map<String, Object> obtainAccessToken(String username, String password) throws Exception {
		  
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
	    return jsonParser.parseMap(resultString);
	}
	
}
