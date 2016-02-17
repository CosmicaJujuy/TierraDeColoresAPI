/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.EntidadBancariaDAO;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.EntidadBancaria;
import com.ar.dev.tierra.api.model.JsonResponse;
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
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author PauloGaldo
 */
@RestController
@RequestMapping("/entidad")
public class EntidadBancariaController implements Serializable {

    @Autowired
    EntidadBancariaDAO entidadBancariaDAO;

    @Autowired
    UsuariosDAO usuariosDAO;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<EntidadBancaria> list = entidadBancariaDAO.getAll();
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(OAuth2Authentication authentication,
            @RequestBody EntidadBancaria bancaria) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        bancaria.setUsuarioCreacion(user.getIdUsuario());
        bancaria.setFechaCreaciion(new Date());
        entidadBancariaDAO.add(bancaria);
        JsonResponse msg = new JsonResponse("Success", "Entidad agregada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(OAuth2Authentication authentication,
            @RequestBody EntidadBancaria bancaria) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        bancaria.setUsuarioModificacion(user.getIdUsuario());
        bancaria.setFechaModificacion(new Date());
        entidadBancariaDAO.update(bancaria);
        JsonResponse msg = new JsonResponse("Success", "Entidad modificada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> delete(OAuth2Authentication authentication,
            @RequestBody EntidadBancaria bancaria) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        bancaria.setUsuarioModificacion(user.getIdUsuario());
        bancaria.setFechaModificacion(new Date());
        bancaria.setEstadoEntidad(false);
        entidadBancariaDAO.update(bancaria);
        JsonResponse msg = new JsonResponse("Success", "Entidad eliminada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
