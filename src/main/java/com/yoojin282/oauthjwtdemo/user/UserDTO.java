package com.yoojin282.oauthjwtdemo.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

public class UserDTO {

	@Getter @Setter
	static class Response {
		public Response(String name, Collection<? extends GrantedAuthority> authorities) {
			this.name = name;
			this.roles = new HashSet<>(authorities.size());
			authorities.forEach(authority -> {
				this.roles.add(authority.getAuthority());
			});
		}
		private String name;
		private Set<String> roles;
	}
}
