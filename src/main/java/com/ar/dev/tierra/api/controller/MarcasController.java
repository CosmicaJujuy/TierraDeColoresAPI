/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.MarcasDAO;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.Marcas;
import com.ar.dev.tierra.api.model.Usuarios;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author PauloGaldo
 */
@RestController
@RequestMapping("/marcas")
public class MarcasController implements Serializable {

    @Autowired
    MarcasDAO marcasDAO;

    @Autowired
    UsuariosDAO usuariosDAO;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<Marcas> marcas = marcasDAO.getAll();
        if (!marcas.isEmpty()) {
            return new ResponseEntity<>(marcas, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(OAuth2Authentication authentication,
            @RequestBody Marcas marcas) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        marcas.setUsuarioCreacion(user.getIdUsuario());
        marcas.setFechaCreacion(new Date());
        marcas.setEstado(true);
        marcasDAO.add(marcas);
        JsonResponse msg = new JsonResponse("Success", "Marca agregada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(OAuth2Authentication authentication,
            @RequestBody Marcas marcas) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        marcas.setUsuarioModificacion(user.getIdUsuario());
        marcas.setFechaModificacion(new Date());
        marcasDAO.update(marcas);
        JsonResponse msg = new JsonResponse("Success", "Marca modificada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> delete(OAuth2Authentication authentication,
            @RequestBody Marcas marcas) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        marcas.setUsuarioModificacion(user.getIdUsuario());
        marcas.setFechaModificacion(new Date());
        marcas.setEstado(false);
        marcasDAO.update(marcas);
        JsonResponse msg = new JsonResponse("Success", "Marca eliminada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "/searchText", method = RequestMethod.POST)
    public ResponseEntity<?> findByText(@RequestParam("text") String text) {
        List<Marcas> list = marcasDAO.searchByText(text);
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
        }
    }
}
