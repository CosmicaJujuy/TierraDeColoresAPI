/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.Usuarios;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
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

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/addUsuario", method = RequestMethod.POST)
    public ResponseEntity<?> addNewUsuario(@RequestBody Usuarios usuarios) throws IOException {
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
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("dd.png");
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] bytes = new byte[16384];
                while ((nRead = is.read(bytes, 0, bytes.length)) != -1) {
                    buffer.write(bytes, 0, nRead);
                }
                buffer.flush();
                usuarios.setImagen(buffer.toByteArray());
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

    @RequestMapping(value = "/updatePhoto", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<?> updateUsuario(@RequestParam("file") MultipartFile file,
            OAuth2Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            Usuarios u = usuariosDAO.findUsuarioByUsername(user.getUsername());
            if (file.getName().isEmpty() == false) {
                InputStream inputStream = file.getInputStream();
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] bytes = new byte[16384];
                while ((nRead = inputStream.read(bytes, 0, bytes.length)) != -1) {
                    buffer.write(bytes, 0, nRead);
                }
                buffer.flush();
                u.setImagen(buffer.toByteArray());
                usuariosDAO.updateUsuario(u);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/updateUsuario", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@RequestBody Usuarios usuario, OAuth2Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            Usuarios u = usuariosDAO.findUsuarioByUsername(user.getUsername());
            if (u.getIdUsuario() == usuario.getIdUsuario()
                    && u.getUsername().equals(usuario.getUsername())
                    && u.getRoles().getNombreRol().equals(usuario.getRoles().getNombreRol())
                    && u.getRoles().getIdRol() == usuario.getRoles().getIdRol()) {
                usuario.setIdUsuarioModificacion(u.getIdUsuario());
                usuario.setFechaModificacion(new Date());
                usuariosDAO.updateUsuario(usuario);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    } // method uploadFile

    @RequestMapping(value = "/detail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Usuarios> detailsUsuario(OAuth2Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Usuarios u = usuariosDAO.findUsuarioByUsername(user.getUsername());
        if (u == null) {
            throw new BadCredentialsException("Bad Credentials");
        } else {
            return new ResponseEntity(u, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ResponseEntity<?> changePassword(OAuth2Authentication authentication,
            @RequestParam("oldPw") String oldPassword,
            @RequestParam("newPw") String newPassword,
            @RequestParam("repPw") String repPassword) {
        String username = authentication.getName();
        Usuarios user = usuariosDAO.findUsuarioByUsername(username);
        boolean rightPassword = passwordEncoder.matches(oldPassword, user.getPasswordUsuario());
        if (rightPassword) {
            if (newPassword.equals(repPassword)) {
                user.setPasswordUsuario(passwordEncoder.encode(newPassword));
                user.setIdUsuarioModificacion(user.getIdUsuario());
                user.setFechaModificacion(new Date());
                usuariosDAO.updateUsuario(user);
                JsonResponse response = new JsonResponse("Success", "Contraseña cambiada con exito.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                JsonResponse response = new JsonResponse("Error", "Contraseñas no coinciden.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } else {
            JsonResponse response = new JsonResponse("Error", "Contraseña incorrecta.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
    public ResponseEntity<?> changeStatus(OAuth2Authentication authentication,
            @RequestParam("status") boolean status,
            @RequestBody Usuarios usuario) {
        Usuarios userAdmin = usuariosDAO.findUsuarioByUsername(authentication.getName());
        Usuarios user = usuariosDAO.findUsuarioByUsername(usuario.getUsername());
        user.setEstado(status);
        user.setFechaModificacion(new Date());
        user.setIdUsuarioModificacion(userAdmin.getIdUsuario());
        usuariosDAO.updateUsuario(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
