/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.ProveedorDAO;
import com.ar.dev.tierra.api.model.Proveedor;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.Usuarios;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping("/proveedor")
public class ProveedorController implements Serializable {

    @Autowired
    UsuariosDAO usuariosDAO;

    @Autowired
    ProveedorDAO proveedorDAO;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<Proveedor> proveedor = proveedorDAO.getAll();
        if (!proveedor.isEmpty()) {
            return new ResponseEntity<>(proveedor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(OAuth2Authentication authentication,
            @RequestBody Proveedor proveedor) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        proveedor.setUsuarioCreacion(user.getIdUsuario());
        proveedor.setFechaCreacion(new Date());
        proveedor.setEstadoProveedor(true);
        proveedorDAO.add(proveedor);
        JsonResponse msg = new JsonResponse("success", "Proveedor agregado con exito.");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(OAuth2Authentication authentication,
            @RequestBody Proveedor proveedor) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        proveedor.setUsuarioModificacion(user.getIdUsuario());
        proveedor.setFechaModificacion(new Date());
        proveedorDAO.update(proveedor);
        JsonResponse msg = new JsonResponse("Success", "Categoria modificada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> delete(OAuth2Authentication authentication,
            @RequestBody Proveedor proveedor) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        proveedor.setUsuarioModificacion(user.getIdUsuario());
        proveedor.setFechaModificacion(new Date());
        proveedor.setEstadoProveedor(false);
        proveedorDAO.update(proveedor);
        JsonResponse msg = new JsonResponse("Success", "Categoria modificada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<?> searchById(@RequestParam("id") int id) {
        Proveedor proveedor = proveedorDAO.searchById(id);
        if (proveedor != null) {
            return new ResponseEntity<>(proveedor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(value = "/searchText", method = RequestMethod.POST)
    public ResponseEntity<?> findByText(@RequestParam("text") String text) {
        List<Proveedor> list = proveedorDAO.searchByText(text);
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
        }
    }

}
