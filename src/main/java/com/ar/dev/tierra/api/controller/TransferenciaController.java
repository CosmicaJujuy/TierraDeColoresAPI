/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.TransferenciaDAO;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.Transferencia;
import com.ar.dev.tierra.api.model.Usuarios;
import java.io.Serializable;
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
@RequestMapping("transferencia")
public class TransferenciaController implements Serializable {

    @Autowired
    TransferenciaDAO transferenciaDAO;

    @Autowired
    UsuariosDAO usuariosDAO;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        List<Transferencia> list = transferenciaDAO.getAll();
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@RequestParam("idTransferencia") int idTransferencia) {
        Transferencia trans = transferenciaDAO.getById(idTransferencia);
        if (trans != null) {
            return new ResponseEntity<>(trans, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Transferencia transferencia,
            OAuth2Authentication authentication) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        transferencia.setEstadoPedido(false);
        transferencia.setFechaCreacion(new Date());
        transferencia.setUsuarioCreacion(user.getIdUsuario());
        transferencia.setSucursalPedido(user.getUsuarioSucursal().getIdSucursal());
        int response = transferenciaDAO.add(transferencia);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(@RequestBody Transferencia transferencia,
            OAuth2Authentication authentication) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        transferencia.setFechaModificacion(new Date());
        transferencia.setEstadoPedido(true);
        transferencia.setUsuarioModificacion(user.getIdUsuario());
        transferencia.setSucursalRespuesta(user.getUsuarioSucursal().getIdSucursal());
        if (transferencia.getSucursalPedido() != user.getUsuarioSucursal().getIdSucursal()) {
            transferenciaDAO.add(transferencia);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            JsonResponse response = new JsonResponse("Error", "No puedes confirmar tu propia transferencia.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/day", method = RequestMethod.GET)
    public ResponseEntity<?> getDay() {
        List<Transferencia> list = transferenciaDAO.getDaily();
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/month", method = RequestMethod.GET)
    public ResponseEntity<?> getMonth() {
        List<Transferencia> list = transferenciaDAO.getMonth();
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
