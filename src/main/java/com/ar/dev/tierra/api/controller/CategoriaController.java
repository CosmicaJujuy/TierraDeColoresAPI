/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.CategoriaDAO;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.Categoria;
import com.ar.dev.tierra.api.model.JsonResponse;
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
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author PauloGaldo
 */
@RestController
@RequestMapping("/categoria")
public class CategoriaController implements Serializable {

    @Autowired
    CategoriaDAO categoriaDAO;

    @Autowired
    UsuariosDAO usuariosDAO;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllCategorias() {
        List<Categoria> categorias = categoriaDAO.getAll();
        if (!categorias.isEmpty()) {
            return new ResponseEntity<>(categorias, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addCategoria(OAuth2Authentication authentication,
            @RequestBody Categoria categoria) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        categoria.setUsuarioCreacion(user.getIdUsuario());
        categoria.setFechaCreacion(new Date());
        categoriaDAO.add(categoria);
        JsonResponse msg = new JsonResponse("Success", "Categoria agregada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateCategoria(OAuth2Authentication authentication,
            @RequestBody Categoria categoria) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        categoria.setUsuarioCreacion(user.getIdUsuario());
        categoria.setFechaCreacion(new Date());
        categoriaDAO.update(categoria);
        JsonResponse msg = new JsonResponse("Success", "Categoria modificada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> deleteCategoria(OAuth2Authentication authentication,
            @RequestBody Categoria categoria) {
        categoriaDAO.delete(categoria);
        JsonResponse msg = new JsonResponse("Success", "Categoria eliminada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
