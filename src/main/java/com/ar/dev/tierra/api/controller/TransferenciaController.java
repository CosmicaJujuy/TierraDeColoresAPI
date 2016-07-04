/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.TransferenciaDAO;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.Transferencia;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
