/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.config.security;

import com.ar.dev.tierra.api.config.UserNotActivatedException;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.Usuarios;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author PauloGaldo
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuariosDAO userRepository;

    @Autowired
    public CustomUserDetailsService(UsuariosDAO userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String login) {

        String lowercaseLogin = login.toLowerCase();

        Usuarios userFromDatabase;

        userFromDatabase = userRepository.findUsuarioByUsername(lowercaseLogin);

        if (userFromDatabase == null) {
            throw new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database");
        } else if (!userFromDatabase.isEstado() == true) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " is not activated");
        }

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String authority;
        switch (userFromDatabase.getRoles().getNombreRol()) {
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
        grantedAuthorities.add(grantedAuthority);
        return new User(String.valueOf(userFromDatabase.getUsername()), userFromDatabase.getPasswordUsuario(), grantedAuthorities);
    }
}
