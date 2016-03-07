/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.FacturaDAO;
import com.ar.dev.tierra.api.dao.MetodoPagoFacturaDAO;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.Factura;
import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.MetodoPagoFactura;
import com.ar.dev.tierra.api.model.PlanPago;
import com.ar.dev.tierra.api.model.Usuarios;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/pago")
public class MetodoPagoFacturaController implements Serializable {

    @Autowired
    UsuariosDAO usuariosDAO;

    @Autowired
    MetodoPagoFacturaDAO pagoFacturaDAO;

    @Autowired
    FacturaDAO facturaDAO;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<MetodoPagoFactura> list = pagoFacturaDAO.getAll();
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(OAuth2Authentication authentication,
            @RequestBody MetodoPagoFactura pagoFactura) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        if (pagoFactura.getPlanPago() == null) {
            PlanPago plan = new PlanPago(1, null, "Pago contado", 1, new Date(2016 - 02 - 02), 0, true, new Date(2016 - 02 - 02), 1);
            pagoFactura.setPlanPago(plan);
        }
        Factura factura = facturaDAO.searchById(pagoFactura.getFactura().getIdFactura());
        List<MetodoPagoFactura> list = pagoFacturaDAO.getFacturaMetodo(factura.getIdFactura());
        BigInteger totalFactura = BigInteger.ZERO;
        for (MetodoPagoFactura metodoPagoFactura : list) {
            totalFactura = totalFactura.add(metodoPagoFactura.getMontoPago());
        }
        if (factura.getTotal().longValue() > totalFactura.longValue()) {
            pagoFactura.setUsuarioCreacion(user.getIdUsuario());
            pagoFactura.setFechaCreacion(new Date());
            pagoFactura.setEstado(true);
            pagoFacturaDAO.add(pagoFactura);
            JsonResponse msg = new JsonResponse("Success", "Metodo de pago agregado con exito");
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(OAuth2Authentication authentication,
            @RequestBody MetodoPagoFactura pagoFactura) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        pagoFactura.setUsuarioModificacion(user.getIdUsuario());
        pagoFactura.setFechaModificacion(new Date());
        pagoFacturaDAO.update(pagoFactura);
        JsonResponse msg = new JsonResponse("Success", "Metodo de pago modificado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> delete(OAuth2Authentication authentication,
            @RequestBody MetodoPagoFactura pagoFactura) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        pagoFactura.setEstado(false);
        pagoFactura.setUsuarioModificacion(user.getIdUsuario());
        pagoFactura.setFechaModificacion(new Date());
        pagoFacturaDAO.update(pagoFactura);
        JsonResponse msg = new JsonResponse("Success", "Metodo de pago eliminado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/factura", method = RequestMethod.GET)
    public ResponseEntity<?> getFacturaMetodo(@RequestParam("idFactura") int idFactura) {
        List<MetodoPagoFactura> list = pagoFacturaDAO.getFacturaMetodo(idFactura);
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
