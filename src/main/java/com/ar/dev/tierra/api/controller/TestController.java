/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

//import hfl.argentina.Estados_Fiscales_RG3561;
//import hfl.argentina.EventoFiscal;
//import hfl.argentina.HasarException;
//import hfl.argentina.HasarImpresoraFiscalRG3561;
//import hfl.argentina.ProtocolHTTP;
import java.io.Serializable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author PauloGaldo
 */
@RestController
@RequestMapping("/hasar")
public class TestController implements Serializable {

    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll(){
//        EventoFiscal fiscal = new EventoFiscal();
//        Estados_Fiscales_RG3561 rG3561 = new Estados_Fiscales_RG3561();
//        ProtocolHTTP protocolHTTP = new ProtocolHTTP();
//        HasarImpresoraFiscalRG3561 rG35611 = new HasarImpresoraFiscalRG3561();
//        rG35611.AbrirCajonDinero();
        return null;
    }

}
