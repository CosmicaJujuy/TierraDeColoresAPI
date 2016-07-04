/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.DetalleTransferenciaDAO;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.DetalleTransferencia;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author PauloGaldo
 */
@RestController
@RequestMapping("transferencia/detalle")
public class DetalleTransferenciaController implements Serializable {

    @Autowired
    DetalleTransferenciaDAO detalleTransferenciaDAO;

    @Autowired
    UsuariosDAO usuariosDAO;

    @RequestMapping(value = "/trans", method = RequestMethod.GET)
    public ResponseEntity<?> getByTransferencia(@RequestParam("idTransferencia") int idTransferencia) {
        List<DetalleTransferencia> list = detalleTransferenciaDAO.getByTransferencia(idTransferencia);
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
