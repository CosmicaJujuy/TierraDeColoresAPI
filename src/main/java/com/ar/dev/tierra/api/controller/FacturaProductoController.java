/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.FacturaProductoDAO;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.FacturaProducto;
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
@RequestMapping("/facturaProducto")
public class FacturaProductoController implements Serializable {

    @Autowired
    private UsuariosDAO usuariosDAO;

    @Autowired
    private FacturaProductoDAO facturaProductoDAO;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<FacturaProducto> list = facturaProductoDAO.getAll();
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(OAuth2Authentication authentication,
            @RequestBody FacturaProducto facturaProducto) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        facturaProducto.setUsuarioCreacion(user.getIdUsuario());
        facturaProducto.setFechaCreacion(new Date());
        facturaProducto.setEstado(true);
        facturaProductoDAO.add(facturaProducto);
        JsonResponse msg = new JsonResponse("Success", "Factura de producto/s agregada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(OAuth2Authentication authentication,
            @RequestBody FacturaProducto facturaProducto) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        facturaProducto.setUsuarioModificacion(user.getIdUsuario());
        facturaProducto.setFechaModificacion(new Date());
        facturaProductoDAO.update(facturaProducto);
        JsonResponse msg = new JsonResponse("Success", "Factura de producto/s  modificada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> delete(OAuth2Authentication authentication,
            @RequestBody FacturaProducto facturaProducto) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        facturaProducto.setEstado(false);
        facturaProducto.setUsuarioModificacion(user.getIdUsuario());
        facturaProducto.setFechaModificacion(new Date());
        facturaProductoDAO.update(facturaProducto);
        JsonResponse msg = new JsonResponse("Success", "Factura de producto/s eliminada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
