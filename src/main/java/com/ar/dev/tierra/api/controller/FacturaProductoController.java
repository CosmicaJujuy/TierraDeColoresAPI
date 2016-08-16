/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.model.FacturaProducto;
import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.Usuarios;
import com.ar.dev.tierra.api.resource.FacadeService;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/facturaProducto")
public class FacturaProductoController implements Serializable {

    @Autowired
    FacadeService facadeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<FacturaProducto> list = facadeService.getFacturaProductoDAO().getAll();
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/list/paged", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllPaged(
            @RequestParam(value = "page", required = false, defaultValue = "") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "") Integer size) {
        Page<FacturaProducto> paged = facadeService.getFacturaProductoService().getAllPaged(page, size);
        if (paged.getSize() != 0) {
            return new ResponseEntity<>(paged, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(OAuth2Authentication authentication,
            @RequestBody FacturaProducto facturaProducto) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        facturaProducto.setUsuarioCreacion(user.getIdUsuario());
        facturaProducto.setFechaCreacion(new Date());
        facturaProducto.setEstadoLocal("SIN REPARTIR");
        facturaProducto.setEstado(true);
        facturaProducto.setCarga(true);
        int idFacturaProducto = facadeService.getFacturaProductoDAO().add(facturaProducto);
        JsonResponse msg = new JsonResponse("Success", String.valueOf(idFacturaProducto));
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(OAuth2Authentication authentication,
            @RequestBody FacturaProducto facturaProducto) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        facturaProducto.setUsuarioModificacion(user.getIdUsuario());
        facturaProducto.setFechaModificacion(new Date());
        facadeService.getFacturaProductoDAO().update(facturaProducto);
        JsonResponse msg = new JsonResponse("Success", "Factura de producto/s  modificada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN, ROLE_REPOSITOR')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> delete(OAuth2Authentication authentication,
            @RequestBody FacturaProducto facturaProducto) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        facturaProducto.setEstado(false);
        facturaProducto.setUsuarioModificacion(user.getIdUsuario());
        facturaProducto.setFechaModificacion(new Date());
        facadeService.getFacturaProductoDAO().update(facturaProducto);
        JsonResponse msg = new JsonResponse("Success", "Factura de producto/s eliminada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public ResponseEntity<?> detail(@RequestParam("idFacturaProducto") int idFacturaProducto) {
        FacturaProducto facturaProducto = facadeService.getFacturaProductoDAO().detail(idFacturaProducto);
        if (facturaProducto != null) {
            return new ResponseEntity<>(facturaProducto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/finish", method = RequestMethod.POST)
    public ResponseEntity<?> finish(@RequestParam("idFacturaProducto") int idFacturaProducto) {
        FacturaProducto facturaProducto = facadeService.getFacturaProductoDAO().detail(idFacturaProducto);
        if (facturaProducto != null) {
            facturaProducto.setCarga(false);
            facadeService.getFacturaProductoDAO().update(facturaProducto);
            JsonResponse msg = new JsonResponse("Success", "La carga finalizo con exito.");
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
