package com.ar.dev.tierra.api.config.security;

import com.ar.dev.tierra.api.config.UserNotActivatedException;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Component("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UsuariosDAO userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {

        String lowercaseLogin = login.toLowerCase();

        Usuarios userFromDatabase;

        userFromDatabase = userRepository.findUsuarioByUsername(lowercaseLogin);

        if (userFromDatabase == null) {
            throw new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the database");
        } else if (!userFromDatabase.isEstado() == false) {
            throw new UserNotActivatedException("User " + lowercaseLogin + " is not activated");
        }

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String authority;
        switch (userFromDatabase.getRoles().getNombreRol()) {
            case "administrador":
                authority = "ROLE_ADMIN";
                break;
            case "vendedor":
                authority = "ROLE_VENDEDOR";
                break;
            default:
                authority = "ROLE_NONE";
                break;
        }
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority);
        grantedAuthorities.add(grantedAuthority);
        return new org.springframework.security.core.userdetails.User(String.valueOf(userFromDatabase.getUsername()), userFromDatabase.getPasswordUsuario(), grantedAuthorities);
    }

}
