/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.DetalleNotaCreditoDAO;
import com.ar.dev.tierra.api.dao.NotaCreditoDAO;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.DetalleNotaCredito;
import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.NotaCredito;
import com.ar.dev.tierra.api.model.Usuarios;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@RequestMapping("/nota")
public class NotaCreditoController implements Serializable {

    @Autowired
    NotaCreditoDAO notaCreditoDAO;

    @Autowired
    UsuariosDAO usuariosDAO;

    @Autowired
    DetalleNotaCreditoDAO detalleNotaCreditoDAO;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        List<NotaCredito> list = notaCreditoDAO.getAll();
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(OAuth2Authentication authentication,
            @RequestBody NotaCredito notaCredito) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        notaCredito.setUsuarioCreacion(user.getIdUsuario());
        notaCredito.setFechaCreacion(new Date());
        notaCredito.setEstadoUso("SIN USO");
        notaCreditoDAO.add(notaCredito);
        JsonResponse msg = new JsonResponse("Success", "Nota de credito agregada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(OAuth2Authentication authentication,
            @RequestBody NotaCredito notaCredito) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        notaCredito.setUsuarioModificacion(user.getIdUsuario());
        notaCredito.setFechaModificacion(new Date());
        BigDecimal montoUpdate = new BigDecimal(BigInteger.ZERO);
        List<DetalleNotaCredito> list = detalleNotaCreditoDAO.getByNotaCredito(notaCredito.getIdNotaCredito());
        for (DetalleNotaCredito detalleNotaCredito : list) {
            montoUpdate = montoUpdate.add(detalleNotaCredito.getMonto());
        }
        notaCredito.setMontoTotal(montoUpdate);
        notaCreditoDAO.update(notaCredito);
        JsonResponse msg = new JsonResponse("Success", "Nota de credito modificada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

}
