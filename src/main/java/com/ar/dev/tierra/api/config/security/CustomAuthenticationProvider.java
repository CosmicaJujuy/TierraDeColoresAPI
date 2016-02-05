/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.config.security;

import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.Usuarios;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author PauloGaldo
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    public CustomAuthenticationProvider() {
        super();
    }
    @Autowired
    private UsuariosDAO user;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String username = String.valueOf(auth.getName());
        String password = String.valueOf(auth.getCredentials().toString());

        Usuarios us = null;
        boolean success = false;
        try {
            us = user.findUsuarioByUsername(username);
            success = passwordEncoder.matches(password, us.getPasswordUsuario());
        } catch (Exception ex) {
        }
        if (success == true) {
            final List<GrantedAuthority> grantedAuths = new ArrayList<>();
            String authority;
            switch (us.getRoles().getNombreRol()) {
                case "ADMINISTRADOR":
                    authority = "ROLE_ADMIN";
                    break;
                case "VENDEDOR":
                    authority = "ROLE_VENDEDOR";
                    break;
                default:
                    authority = "ROLE_NONE";
                    break;
            }
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority);
            grantedAuths.add(grantedAuthority);
            final UserDetails principal = new User(username, password, grantedAuths);
            final Authentication authentication = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);
            us = null;
            return authentication;
        } else {
            throw new BadCredentialsException("Bad Credentials");
        }
    }

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
