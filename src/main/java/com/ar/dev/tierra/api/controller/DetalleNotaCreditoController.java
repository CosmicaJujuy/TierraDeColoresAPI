/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.model.DetalleFactura;
import com.ar.dev.tierra.api.model.DetalleNotaCredito;
import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.Usuarios;
import com.ar.dev.tierra.api.resource.FacadeService;
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
@RequestMapping("/notadetalle")
public class DetalleNotaCreditoController implements Serializable {

    @Autowired
    FacadeService facadeService;

    @RequestMapping(value = "/nota", method = RequestMethod.GET)
    public ResponseEntity<?> getByNotaCredito(@RequestParam("idNota") int idNota) {
        List<DetalleNotaCredito> list = facadeService.getDetalleNotaCreditoDAO().getByNotaCredito(idNota);
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/barcode", method = RequestMethod.GET)
    public ResponseEntity<?> getByProductoOnFactura(@RequestParam("barcode") String barcode) {
        List<DetalleFactura> list = facadeService.getDetalleNotaCreditoDAO().getByBarcodeOnFactura(barcode);
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody DetalleNotaCredito detalleNotaCredito, 
            OAuth2Authentication authentication) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        detalleNotaCredito.setFechaCreacion(new Date());
        detalleNotaCredito.setUsuarioCreacion(user.getIdUsuario());
        facadeService.getDetalleNotaCreditoDAO().add(detalleNotaCredito);
        JsonResponse msg = new JsonResponse("Success", "Detalle agregado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(@RequestBody DetalleNotaCredito detalleNotaCredito, 
            OAuth2Authentication authentication) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        detalleNotaCredito.setFechaModificacion(new Date());
        detalleNotaCredito.setUsuarioModificacion(user.getIdUsuario());
        facadeService.getDetalleNotaCreditoDAO().update(detalleNotaCredito);
        JsonResponse msg = new JsonResponse("Success", "Detalle modificado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> delete(@RequestBody DetalleNotaCredito detalleNotaCredito) {
        facadeService.getDetalleNotaCreditoDAO().delete(detalleNotaCredito);
        JsonResponse msg = new JsonResponse("Success", "Detalle eliminado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
