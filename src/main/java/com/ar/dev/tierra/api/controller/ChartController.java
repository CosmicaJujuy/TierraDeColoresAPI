/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.ChartDAO;
import com.ar.dev.tierra.api.model.chart.Chart;
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
@RequestMapping("/chart")
public class ChartController implements Serializable {

    @Autowired
    ChartDAO impl;

    @RequestMapping(value = "/vendedor/cantidad", method = RequestMethod.GET)
    public ResponseEntity<?> getRowCountVendedor(@RequestParam("idVendedor") int idVendedor) {
        List<Chart> chart = impl.getVentaVendedores(idVendedor);
        if(!chart.isEmpty()){
            return new ResponseEntity<>(chart, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value = "/vendedor/ventas", method = RequestMethod.GET)
    public ResponseEntity<?> getVentaVendedor(@RequestParam("idVendedor") int idVendedor) {
        List<Chart> chart = impl.getDineroVendedores(idVendedor);
        if(!chart.isEmpty()){
            return new ResponseEntity<>(chart, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value = "/medio/cantidad", method = RequestMethod.GET)
    public ResponseEntity<?> getMedioCantidad(@RequestParam("idMedioPago") int idMedioPago) {
        List<Chart> chart = impl.getVentaMedioPago(idMedioPago);
        if(!chart.isEmpty()){
            return new ResponseEntity<>(chart, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value = "/medio/ventas", method = RequestMethod.GET)
    public ResponseEntity<?> getMedioMonto(@RequestParam("idMedioPago") int idMedioPago) {
        List<Chart> chart = impl.getMontoMedioPago(idMedioPago);
        if(!chart.isEmpty()){
            return new ResponseEntity<>(chart, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
