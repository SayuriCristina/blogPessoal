package com.generation.blogpessoal.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.generation.blogpessoal.model.Usuario;

public class UserDetailsImpl implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	private List<GrantedAuthority> authorities;

	public UserDetailsImpl(Usuario user) {
		this.userName = user.getEmail();
		this.password = user.getSenha();
	}

	public UserDetailsImpl() {}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// se a conta não estiver expirada = true
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// se a conta não estiver bloqueada = true
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// se o token não estiver expirado = true
		return true;
	}

	@Override
	public boolean isEnabled() {
		// se o usuario estiver habilitado = true
		return true;
	}
	
	

}
