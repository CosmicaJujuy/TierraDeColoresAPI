/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.FacturaDAO;
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
import org.springframework.http.ResponseEntity;
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
@RequestMapping("reserva")
public class ReservaController implements Serializable {

    @Autowired
    FacturaDAO facturaDAO;

    @Autowired
    UsuariosDAO usuariosDAO;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(OAuth2Authentication authentication,
            @RequestBody Factura factura) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        factura.setUsuarioCreacion(user.getIdUsuario());
        factura.setFechaCreacion(new Date());
        factura.setEstado("RESERVADO");
        factura.setIdSucursal(user.getUsuarioSucursal().getIdSucursal());
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
        JsonResponse msg = new JsonResponse("Success", "Reserva modificada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/day", method = RequestMethod.GET)
    public ResponseEntity<?> getDay() {
        List<Factura> list = facturaDAO.getDiaryReserva();
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value = "/month", method = RequestMethod.GET)
    public ResponseEntity<?> getMonth() {
        List<Factura> list = facturaDAO.getMonthReserva();
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
