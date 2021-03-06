/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.model.Factura;
import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.MetodoPagoFactura;
import com.ar.dev.tierra.api.model.NotaCredito;
import com.ar.dev.tierra.api.model.PlanPago;
import com.ar.dev.tierra.api.model.Usuarios;
import com.ar.dev.tierra.api.resource.FacadeService;
import java.io.Serializable;
import java.math.BigDecimal;
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
    FacadeService facadeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<MetodoPagoFactura> list = facadeService.getMetodoPagoFacturaDAO().getAll();
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @SuppressWarnings("StringEquality")
    public ResponseEntity<?> add(OAuth2Authentication authentication,
            @RequestBody MetodoPagoFactura pagoFactura) throws Exception {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        boolean control = true;
        JsonResponse msg = new JsonResponse();
        @SuppressWarnings("UnusedAssignment")
        NotaCredito notaCredito = null;
        switch (pagoFactura.getPlanPago().getIdPlanesPago()) {
            case 1:
                PlanPago plan = facadeService.getPlanPagoDAO().searchById(1);
                pagoFactura.setPlanPago(plan);
                break;
            case 4:
                PlanPago planNota = facadeService.getPlanPagoDAO().searchById(2);
                notaCredito = facadeService.getNotaCreditoDAO().getByNumero(pagoFactura.getComprobante());
                if (notaCredito != null) {
                    if (pagoFactura.getMontoPago().compareTo(notaCredito.getMontoTotal()) == 0) {
                        if (notaCredito.getEstadoUso().equals("SIN USO")) {
                            pagoFactura.setPlanPago(planNota);
                        } else if (notaCredito.getEstadoUso().equals("CANCELADO")) {
                            msg = new JsonResponse("Error", "Nota de credito cancelada.");
                            control = false;
                        } else {
                            msg = new JsonResponse("Error", "Ya ha sido usada la nota de credito.");
                            control = false;
                        }
                    } else {
                        msg = new JsonResponse("Error", "Monto de la nota de credito invalido.");
                        control = false;
                    }
                } else {
                    msg = new JsonResponse("Error", "Nota de credito invalida.");
                    control = false;
                }
        }
        if (control) {
            Factura factura = facadeService.getFacturaDAO().searchById(pagoFactura.getFactura().getIdFactura());
            List<MetodoPagoFactura> list = facadeService.getMetodoPagoFacturaDAO().getFacturaMetodo(factura.getIdFactura());
            /*POSIBLE FALLA, DECIMAL INMUTABLE NO SE SUMAN ENTRE SI, NECESITA TEST*/
            BigDecimal totalFactura = BigDecimal.ZERO;
            for (MetodoPagoFactura metodoPagoFactura : list) {
                totalFactura = totalFactura.add(metodoPagoFactura.getMontoPago());
            }
            if (factura.getTotal().longValue() > totalFactura.longValue()) {
                pagoFactura.setUsuarioCreacion(user.getIdUsuario());
                pagoFactura.setFechaCreacion(new Date());
                pagoFactura.setEstado(true);
                if (notaCredito != null) {
                    notaCredito.setFacturaUso(pagoFactura.getFactura().getIdFactura());
                    notaCredito.setEstadoUso("USADO");
                    facadeService.getNotaCreditoDAO().update(notaCredito);
                }
                facadeService.getMetodoPagoFacturaDAO().add(pagoFactura);
                msg = new JsonResponse("Success", "Metodo de pago agregado con exito");
                return new ResponseEntity<>(msg, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(OAuth2Authentication authentication,
            @RequestBody MetodoPagoFactura pagoFactura) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        pagoFactura.setUsuarioModificacion(user.getIdUsuario());
        pagoFactura.setFechaModificacion(new Date());
        facadeService.getMetodoPagoFacturaDAO().update(pagoFactura);
        JsonResponse msg = new JsonResponse("Success", "Metodo de pago modificado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN, ROLE_CONTADOR')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> delete(OAuth2Authentication authentication,
            @RequestBody MetodoPagoFactura pagoFactura) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        pagoFactura.setEstado(false);
        pagoFactura.setUsuarioModificacion(user.getIdUsuario());
        pagoFactura.setFechaModificacion(new Date());
        facadeService.getMetodoPagoFacturaDAO().update(pagoFactura);
        JsonResponse msg = new JsonResponse("Success", "Metodo de pago eliminado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/factura", method = RequestMethod.GET)
    public ResponseEntity<?> getFacturaMetodo(@RequestParam("idFactura") int idFactura) {
        List<MetodoPagoFactura> list = facadeService.getMetodoPagoFacturaDAO().getFacturaMetodo(idFactura);
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/day", method = RequestMethod.GET)
    public ResponseEntity<?> getDay() {
        List<MetodoPagoFactura> list = facadeService.getMetodoPagoFacturaDAO().getDay();
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
