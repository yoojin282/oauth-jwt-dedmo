package com.yoojin282.oauthjwtdemo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(LogoutController.class)
public class LogoutControllerTest {
	@Autowired
	private MockMvc mvc;
	
	@Test
	public void testLogout_withParam() throws Exception {
		mvc.perform(get("/exit")
				.param("redirect_uri", "http://localhost"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("http://localhost"));
	}
	
	@Test
	public void testLogout_withReferer() throws Exception {
		mvc.perform(get("/exit")
				.header("referer", "http://localhost"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("http://localhost"));
	}

}
