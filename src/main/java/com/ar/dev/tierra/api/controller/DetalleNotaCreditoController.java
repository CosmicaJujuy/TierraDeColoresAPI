/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.DetalleNotaCreditoDAO;
import com.ar.dev.tierra.api.model.DetalleFactura;
import com.ar.dev.tierra.api.model.DetalleNotaCredito;
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
@RequestMapping("/notadetalle")
public class DetalleNotaCreditoController implements Serializable {

    @Autowired
    DetalleNotaCreditoDAO detalleNotaCreditoDAO;

    @RequestMapping(value = "/nota", method = RequestMethod.GET)
    public ResponseEntity<?> getByNotaCredito(@RequestParam("idNota")int idNota) {
        List<DetalleNotaCredito> list = detalleNotaCreditoDAO.getByNotaCredito(idNota);
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value = "/barcode", method = RequestMethod.GET)
    public ResponseEntity<?> getByProductoOnFactura(@RequestParam("barcode")String barcode) {
        List<DetalleFactura> list = detalleNotaCreditoDAO.getByBarcodeOnFactura(barcode);
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
