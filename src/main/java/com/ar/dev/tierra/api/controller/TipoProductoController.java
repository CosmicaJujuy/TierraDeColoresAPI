/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.TipoProducto;
import com.ar.dev.tierra.api.model.Usuarios;
import com.ar.dev.tierra.api.resource.FacadeService;
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
@RequestMapping("/tipo")
public class TipoProductoController implements Serializable {

    @Autowired
    FacadeService facadeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<TipoProducto> tipoProducto = facadeService.getTipoProductoDAO().getAll();
        if (!tipoProducto.isEmpty()) {
            return new ResponseEntity<>(tipoProducto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(OAuth2Authentication authentication,
            @RequestBody TipoProducto tipoProducto) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        tipoProducto.setUsuarioCreacion(user.getIdUsuario());
        tipoProducto.setFechaCreacion(new Date());
        tipoProducto.setEstado(true);
        facadeService.getTipoProductoDAO().add(tipoProducto);
        JsonResponse msg = new JsonResponse("Success", "Tipo de Producto agregada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(OAuth2Authentication authentication,
            @RequestBody TipoProducto tipoProducto) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        tipoProducto.setUsuarioModificacion(user.getIdUsuario());
        tipoProducto.setFechaModificacion(new Date());
        facadeService.getTipoProductoDAO().update(tipoProducto);
        JsonResponse msg = new JsonResponse("Success", "Tipo de Producto modificada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> delete(OAuth2Authentication authentication,
            @RequestBody TipoProducto tipoProducto) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        tipoProducto.setUsuarioModificacion(user.getIdUsuario());
        tipoProducto.setFechaModificacion(new Date());
        tipoProducto.setEstado(false);
        facadeService.getTipoProductoDAO().update(tipoProducto);
        JsonResponse msg = new JsonResponse("Success", "Categoria eliminada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
