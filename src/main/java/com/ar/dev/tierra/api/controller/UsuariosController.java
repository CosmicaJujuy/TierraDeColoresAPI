/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.Usuarios;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author PauloGaldo
 */
@RestController
@RequestMapping("/usuarios")
public class UsuariosController implements Serializable {

    @Autowired
    UsuariosDAO usuariosDAO;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Usuarios>> getAllUsuarios() {
        List<Usuarios> listUsuarios = usuariosDAO.allUsuarios();
        ResponseEntity responseEntity;
        if (listUsuarios.isEmpty()) {
            responseEntity = new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            responseEntity = new ResponseEntity(listUsuarios, HttpStatus.OK);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Usuarios> addNewUsuario(@RequestBody Usuarios usuarios) {
        String ptoEnc = usuarios.getPasswordUsuario();
        String bCrypt = passwordEncoder.encode(ptoEnc);
        usuarios.setPasswordUsuario(bCrypt);
        usuarios.setEstado(false);
        usuarios.setFechaCreacion(new Date());
        Usuarios replyUsername;
        Usuarios replyDNI;
        replyUsername = usuariosDAO.findUsuarioByUsername(usuarios.getUsername());
        replyDNI = usuariosDAO.findUsuarioByDNI(usuarios.getDni());
        ResponseEntity responseEntity;
        if (replyDNI == null && replyUsername == null) {
            usuariosDAO.addUsuario(usuarios);
            responseEntity = new ResponseEntity(HttpStatus.OK);
        } else {
            JsonResponse msj;
            if (replyDNI != null && replyUsername == null) {
                msj = new JsonResponse("Error", "D.N.I. ya existe.");
            } else if (replyUsername != null && replyDNI == null) {
                msj = new JsonResponse("Error", "Nombre de usuario ya existe.");
            } else {
                msj = new JsonResponse("Error", "Nombre de usuario y D.N.I. ya existen.");
            }
            responseEntity = new ResponseEntity(msj, HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<Usuarios> updateUsuario(
            @RequestBody Usuarios usuarios,
            @RequestParam("file") MultipartFile file) {
        file.getName();
        return null;
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuarios> detailsUsuario(OAuth2Authentication authentication, HttpServletRequest req) {
        Usuarios findUser;
        String bCryp = req.getHeader("credential");
        boolean owner = passwordEncoder.matches(authentication.getName(), bCryp);
        if (owner != true) {
            throw new BadCredentialsException("Bad Credentials");
        } else {
            String username = String.valueOf(authentication.getName());
            findUser = usuariosDAO.findUsuarioByUsername(username);
            if (findUser == null) {
                throw new BadCredentialsException("User not found");
            }
        }
        return new ResponseEntity(findUser, HttpStatus.OK);
    }
}
