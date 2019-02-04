package com.yoojin282.oauthjwtdemo.helllo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(HelloController.class)
public class HelloControllerTest {
	@Autowired
	MockMvc mvc;
	
	@Test
	public void test() throws Exception {
		mvc.perform(get("/hello"))
			.andExpect(status().isOk())
			.andExpect(content().string("hello"))
			.andDo(print());
	}
	
	@Test
	public void testImGroot_noCredential() throws Exception {
		mvc.perform(get("/imgroot"))
			.andExpect(status().isForbidden())
			.andDo(print());
	}
	
	@Test
	@WithMockUser(username="testuser", roles= {"USER"})
	public void testImGroot_withCredential() throws Exception {
		mvc.perform(get("/imgroot"))
			.andExpect(status().isOk())
			.andExpect(content().string("imgroot"))
			.andDo(print());
	}

}
