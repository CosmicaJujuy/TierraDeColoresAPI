/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.TarjetaDAO;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.Tarjeta;
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
@RequestMapping("/tarjeta")
public class TarjetaController implements Serializable {

    @Autowired
    TarjetaDAO tarjetaDAO;

    @Autowired
    UsuariosDAO usuariosDAO;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<Tarjeta> categorias = tarjetaDAO.getAll();
        if (!categorias.isEmpty()) {
            return new ResponseEntity<>(categorias, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(OAuth2Authentication authentication,
            @RequestBody Tarjeta tarjeta) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        tarjeta.setUsuarioCreacion(user.getIdUsuario());
        tarjeta.setFechaCreacion(new Date());
        tarjeta.setEstadoTarjeta(true);
        tarjetaDAO.add(tarjeta);
        JsonResponse msg = new JsonResponse("Success", "Tarjeta agregada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(OAuth2Authentication authentication,
            @RequestBody Tarjeta tarjeta) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        tarjeta.setUsuarioModificacion(user.getIdUsuario());
        tarjeta.setFechaModificacion(new Date());
        tarjetaDAO.update(tarjeta);
        JsonResponse msg = new JsonResponse("Success", "Tarjeta modificada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> delete(OAuth2Authentication authentication,
            @RequestBody Tarjeta tarjeta) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        tarjeta.setEstadoTarjeta(false);
        tarjeta.setUsuarioModificacion(user.getIdUsuario());
        tarjeta.setFechaModificacion(new Date());
        tarjetaDAO.update(tarjeta);
        JsonResponse msg = new JsonResponse("Success", "Tarjeta eliminada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/entidadMedio", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchEntidad(
            @RequestParam("idEntidad") int idEntidad,
            @RequestParam("idMedio") int idMedio) {
        List<Tarjeta> list = tarjetaDAO.searchEntidadMedio(idEntidad, idMedio);
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
