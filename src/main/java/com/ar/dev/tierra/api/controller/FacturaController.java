/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.DetalleFacturaDAO;
import com.ar.dev.tierra.api.dao.FacturaDAO;
import com.ar.dev.tierra.api.dao.ProductoDAO;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.Factura;
import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.Usuarios;
import java.io.Serializable;
import java.math.BigDecimal;
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
@RequestMapping("/factura")
public class FacturaController implements Serializable {

    @Autowired
    FacturaDAO facturaDAO;

    @Autowired
    UsuariosDAO usuariosDAO;

    @Autowired
    ProductoDAO productoDAO;

    @Autowired
    DetalleFacturaDAO detalleFacturaDAO;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<Factura> list = facturaDAO.getAll();
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(OAuth2Authentication authentication,
            @RequestBody Factura factura) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        factura.setUsuarioCreacion(user.getIdUsuario());
        factura.setFechaCreacion(new Date());
        factura.setEstado("INICIADO");
        factura.setTotal(BigDecimal.ZERO);
        int idFactura = facturaDAO.add(factura);
        JsonResponse msg = new JsonResponse("Success", String.valueOf(idFactura));
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(OAuth2Authentication authentication,
            @RequestBody Factura factura) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        factura.setUsuarioModificacion(user.getIdUsuario());
        factura.setFechaModificacion(new Date());
        facturaDAO.update(factura);
        JsonResponse msg = new JsonResponse("Success", "Factura modificada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> delete(OAuth2Authentication authentication,
            @RequestBody Factura factura) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        factura.setUsuarioModificacion(user.getIdUsuario());
        factura.setFechaModificacion(new Date());
        facturaDAO.delete(factura);
        JsonResponse msg = new JsonResponse("Success", "Factura eliminada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<?> searchById(@RequestParam("idFactura") int id) {
        Factura factura = facturaDAO.searchById(id);
        if (factura != null) {
            return new ResponseEntity<>(factura, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/day", method = RequestMethod.GET)
    public ResponseEntity<?> searchById() {
        List<Factura> factura = facturaDAO.getDiary();
        if (factura != null) {
            return new ResponseEntity<>(factura, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
