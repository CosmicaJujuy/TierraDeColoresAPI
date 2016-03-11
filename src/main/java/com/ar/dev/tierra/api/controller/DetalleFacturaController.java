/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.DetalleFacturaDAO;
import com.ar.dev.tierra.api.dao.ProductoDAO;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.DetalleFactura;
import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.Producto;
import com.ar.dev.tierra.api.model.Usuarios;
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
@RequestMapping("/detalle")
public class DetalleFacturaController implements Serializable {

    @Autowired
    UsuariosDAO usuariosDAO;

    @Autowired
    DetalleFacturaDAO detalleFacturaDAO;
    
    @Autowired
    ProductoDAO productoDAO;
    
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<DetalleFactura> list = detalleFacturaDAO.getAll();
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(OAuth2Authentication authentication,
            @RequestBody DetalleFactura detalleFactura) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        detalleFactura.setUsuarioCreacion(user.getIdUsuario());
        detalleFactura.setFechaCreacion(new Date());
        detalleFactura.setEstadoDetalle(true);
        Producto prod = productoDAO.findById(detalleFactura.getProducto().getIdProducto());
        detalleFactura.setProducto(prod);
        int monto = detalleFactura.getProducto().getPrecioVenta()* detalleFactura.getCantidadDetalle();
        monto = monto - detalleFactura.getDescuentoDetalle();
        detalleFactura.setTotalDetalle(monto);
        detalleFacturaDAO.add(detalleFactura);
        JsonResponse msg = new JsonResponse("Success", "Detalle agregado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(OAuth2Authentication authentication,
            @RequestBody DetalleFactura detalleFactura) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        detalleFactura.setUsuarioModificacion(user.getIdUsuario());
        detalleFactura.setFechaModificacion(new Date());
        detalleFacturaDAO.update(detalleFactura);
        JsonResponse msg = new JsonResponse("Success", "Detalle modificado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> delete(OAuth2Authentication authentication,
            @RequestBody DetalleFactura detalleFactura) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        detalleFactura.setEstadoDetalle(false);
        detalleFactura.setUsuarioModificacion(user.getIdUsuario());
        detalleFactura.setFechaModificacion(new Date());
        detalleFacturaDAO.update(detalleFactura);
        JsonResponse msg = new JsonResponse("Success", "Detalle eliminado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/factura", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listDetalleFactura(@RequestParam("idFactura")int idFactura){
    List<DetalleFactura> list = detalleFacturaDAO.facturaDetalle(idFactura);
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }        
    }
    
}
