/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.PlanPago;
import com.ar.dev.tierra.api.model.Usuarios;
import com.ar.dev.tierra.api.resource.FacadeService;
import java.io.Serializable;
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
@RequestMapping("/plan")
public class PlanPagoController implements Serializable {

    @Autowired
    FacadeService facadeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<PlanPago> list = facadeService.getPlanPagoDAO().getAll();
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(OAuth2Authentication authentication,
            @RequestBody PlanPago planPago) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        planPago.setUsuarioCreacion(user.getIdUsuario());
        planPago.setFechaCreacion(new Date());
        planPago.setEstadoPlanes(true);
        facadeService.getPlanPagoDAO().add(planPago);
        JsonResponse msg = new JsonResponse("Success", "Plan de pago agregado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(OAuth2Authentication authentication,
            @RequestBody PlanPago planPago) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        planPago.setUsuarioMoficacion(user.getIdUsuario());
        planPago.setFechaModificacion(new Date());
        facadeService.getPlanPagoDAO().update(planPago);
        JsonResponse msg = new JsonResponse("Success", "Plan de pago modificado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN, ROLE_CONTADOR')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> delete(OAuth2Authentication authentication,
            @RequestBody PlanPago planPago) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        planPago.setEstadoPlanes(false);
        planPago.setUsuarioMoficacion(user.getIdUsuario());
        planPago.setFechaModificacion(new Date());
        facadeService.getPlanPagoDAO().update(planPago);
        JsonResponse msg = new JsonResponse("Success", "Plan de pago eliminado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    
    @RequestMapping(value = "/tarjeta", method = RequestMethod.GET)
    public ResponseEntity<?> searchByTarjeta(@RequestParam("idTarjeta")int idTarjeta){
    List<PlanPago> list = facadeService.getPlanPagoDAO().searchPlanByTarjeta(idTarjeta);
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    
    }
}
