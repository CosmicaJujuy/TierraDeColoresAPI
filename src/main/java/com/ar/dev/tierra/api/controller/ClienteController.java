/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.ClienteDAO;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.Cliente;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author PauloGaldo
 */
@RestController
@RequestMapping("/cliente")
public class ClienteController implements Serializable {

    @Autowired
    ClienteDAO clienteDAO;

    @Autowired
    UsuariosDAO usuariosDAO;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<Cliente> list = clienteDAO.getAll();
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(OAuth2Authentication authentication,
            @RequestBody Cliente cliente) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        cliente.setUsuarioCreacion(user.getIdUsuario());
        cliente.setFechaCreacion(new Date());
        int idCliente = clienteDAO.add(cliente);
        JsonResponse msg = new JsonResponse("Success", String.valueOf(idCliente));
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(OAuth2Authentication authentication,
            @RequestBody Cliente cliente) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        cliente.setUsuarioModificacion(user.getIdUsuario());
        cliente.setFechaModificacion(new Date());
        clienteDAO.update(cliente);
        JsonResponse msg = new JsonResponse("Success", "Cliente modificado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> delete(OAuth2Authentication authentication,
            @RequestBody Cliente cliente) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        cliente.setUsuarioModificacion(user.getIdUsuario());
        cliente.setFechaModificacion(new Date());
        clienteDAO.delete(cliente);
        JsonResponse msg = new JsonResponse("Success", "Cliente eliminado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/searchId", method = RequestMethod.POST)
    public ResponseEntity<?> searchById(@RequestParam("idCliente") int idCliente) {
        Cliente cliente = clienteDAO.searchById(idCliente);
        if (cliente != null) {
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/searchApellido", method = RequestMethod.POST)
    public ResponseEntity<?> searchByNombre(@RequestParam("apellidoCliente") String apellidoCliente) {
        List<Cliente> list = clienteDAO.searchByNombre(apellidoCliente);
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
        }
    }
}
