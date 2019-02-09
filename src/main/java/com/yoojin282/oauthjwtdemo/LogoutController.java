package com.yoojin282.oauthjwtdemo;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {

	@RequestMapping("/exit")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		new SecurityContextLogoutHandler().logout(request, null, null);
		String redirectUri = request.getParameter("redirect_uri");
		if(StringUtils.isEmpty(redirectUri))
			response.sendRedirect(request.getHeader("referer"));
		else
			response.sendRedirect(redirectUri);
	}
}
