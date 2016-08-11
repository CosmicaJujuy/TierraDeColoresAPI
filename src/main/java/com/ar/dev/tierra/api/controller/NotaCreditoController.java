/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.model.DetalleNotaCredito;
import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.NotaCredito;
import com.ar.dev.tierra.api.model.Usuarios;
import com.ar.dev.tierra.api.resource.FacadeService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author PauloGaldo
 */
@RestController
@RequestMapping("/nota")
public class NotaCreditoController implements Serializable {

    @Autowired
    FacadeService facadeService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        List<NotaCredito> list = facadeService.getNotaCreditoDAO().getAll();
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/search/id", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@RequestParam("idNota") int idNota) {
        NotaCredito notaCredito = facadeService.getNotaCreditoDAO().getById(idNota);
        if (notaCredito != null) {
            return new ResponseEntity<>(notaCredito, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(OAuth2Authentication authentication,
            @RequestBody NotaCredito notaCredito) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        notaCredito.setUsuarioCreacion(user.getIdUsuario());
        notaCredito.setFechaCreacion(new Date());
        notaCredito.setEstadoUso("CARGANDO");
        int idNota = facadeService.getNotaCreditoDAO().add(notaCredito);
        JsonResponse msg = new JsonResponse("Success", String.valueOf(idNota));
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> delete(OAuth2Authentication authentication,
            @RequestParam("idNota") int notaCredito) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        List<DetalleNotaCredito> list = facadeService.getDetalleNotaCreditoDAO().getByNotaCredito(notaCredito);
        NotaCredito nota = facadeService.getNotaCreditoDAO().getById(notaCredito);
        if (!list.isEmpty()) {
            for (DetalleNotaCredito detalleNotaCredito : list) {
                facadeService.getDetalleNotaCreditoDAO().delete(detalleNotaCredito);
            }
        }
        nota.setUsuarioCreacion(user.getIdUsuario());
        nota.setFechaCreacion(new Date());
        nota.setEstadoUso("CANCELADO");
        facadeService.getNotaCreditoDAO().update(nota);
        JsonResponse msg = new JsonResponse("Success", "Nota de credito cancelada.");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(OAuth2Authentication authentication,
            @RequestBody NotaCredito notaCredito) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        notaCredito.setUsuarioModificacion(user.getIdUsuario());
        notaCredito.setFechaModificacion(new Date());
        BigDecimal montoUpdate = new BigDecimal(BigInteger.ZERO);
        List<DetalleNotaCredito> list = facadeService.getDetalleNotaCreditoDAO().getByNotaCredito(notaCredito.getIdNotaCredito());
        for (DetalleNotaCredito detalleNotaCredito : list) {
            montoUpdate = montoUpdate.add(detalleNotaCredito.getMonto());
        }
        notaCredito.setMontoTotal(montoUpdate);
        facadeService.getNotaCreditoDAO().update(notaCredito);
        JsonResponse msg = new JsonResponse("Success", "Nota de credito modificada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/day", method = RequestMethod.GET)
    public ResponseEntity<?> getDay() {
        List<NotaCredito> list = facadeService.getNotaCreditoDAO().getDaily();
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/month", method = RequestMethod.GET)
    public ResponseEntity<?> getMonth() {
        List<NotaCredito> list = facadeService.getNotaCreditoDAO().getMonth();
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
