/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.ProductoDAO;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.Producto;
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
@RequestMapping("/producto")
public class ProductoController implements Serializable {

    @Autowired
    ProductoDAO productoDAO;

    @Autowired
    UsuariosDAO usuariosDAO;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<Producto> categorias = productoDAO.getAll();
        if (!categorias.isEmpty()) {
            return new ResponseEntity<>(categorias, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(OAuth2Authentication authentication,
            @RequestBody Producto producto) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        String descripcion = producto.getDescripcion();
        producto.setDescripcion(descripcion.toUpperCase());
        producto.setUsuarioCreacion(user.getIdUsuario());
        producto.setFechaCreacion(new Date());
        productoDAO.add(producto);
        JsonResponse msg = new JsonResponse("Success", "Producto agregado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(OAuth2Authentication authentication,
            @RequestBody Producto producto) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        producto.setUsuarioModificacion(user.getIdUsuario());
        producto.setFechaModificacion(new Date());
        productoDAO.update(producto);
        JsonResponse msg = new JsonResponse("Success", "Producto modificado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> delete(OAuth2Authentication authentication,
            @RequestBody Producto producto) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        producto.setEstadoProducto(false);
        producto.setUsuarioModificacion(user.getIdUsuario());
        producto.setFechaModificacion(new Date());
        productoDAO.update(producto);
        JsonResponse msg = new JsonResponse("Success", "Categoria eliminada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<?> findById(@RequestParam("id") int id) {
        Producto p = productoDAO.findById(id);
        if (p == null || p.isEstadoProducto() == false) {
            JsonResponse jr = new JsonResponse("error", "No se encontro el producto");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(p, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/searchText", method = RequestMethod.POST)
    public ResponseEntity<?> findByText(@RequestParam("text") String text) {
        List<Producto> productos = productoDAO.findByText(text.toUpperCase());
        if (!productos.isEmpty()) {
            return new ResponseEntity<>(productos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(productos, HttpStatus.BAD_REQUEST);
        }
    }
}
