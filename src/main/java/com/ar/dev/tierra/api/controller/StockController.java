/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.StockDAO;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.Usuarios;
import com.ar.dev.tierra.api.model.stock.StockBebelandia;
import com.ar.dev.tierra.api.model.stock.StockTierra;
import com.ar.dev.tierra.api.model.stock.WrapperStock;
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
@RequestMapping("/stock")
public class StockController implements Serializable {

    @Autowired
    StockDAO stockDAO;

    @Autowired
    UsuariosDAO usuariosDAO;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<?> list(@RequestParam("idStock") int idStock) {
        List<WrapperStock> list = stockDAO.getAll(idStock);
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addStockTierra(OAuth2Authentication authentication,
            @RequestBody WrapperStock wrapper,
            @RequestParam("idScursal") int idSucursal) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        switch (idSucursal) {
            case 1:
                wrapper.getStockTierra().setFechaCreacion(new Date());
                wrapper.getStockTierra().setUsuarioCreacion(user.getIdUsuario());
                wrapper.getStockTierra().setEstado(true);
                break;
            case 2:
                wrapper.getStockBebelandia().setFechaCreacion(new Date());
                wrapper.getStockBebelandia().setUsuarioCreacion(user.getIdUsuario());
                wrapper.getStockBebelandia().setEstado(true);
                break;
            case 3:
                wrapper.getStockLibertador().setFechaCreacion(new Date());
                wrapper.getStockLibertador().setUsuarioCreacion(user.getIdUsuario());
                wrapper.getStockLibertador().setEstado(true);
                break;
        }
        stockDAO.add(wrapper, idSucursal);
        JsonResponse msg = new JsonResponse("Success", "Producto agregado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/tierra/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateStockTierra(OAuth2Authentication authentication,
            @RequestBody WrapperStock wrapper,
            @RequestParam("idScursal") int idSucursal) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        switch (idSucursal) {
            case 1:
                wrapper.getStockTierra().setFechaCreacion(new Date());
                wrapper.getStockTierra().setUsuarioCreacion(user.getIdUsuario());
                wrapper.getStockTierra().setEstado(true);
                break;
            case 2:
                wrapper.getStockBebelandia().setFechaCreacion(new Date());
                wrapper.getStockBebelandia().setUsuarioCreacion(user.getIdUsuario());
                wrapper.getStockBebelandia().setEstado(true);
                break;
            case 3:
                wrapper.getStockLibertador().setFechaCreacion(new Date());
                wrapper.getStockLibertador().setUsuarioCreacion(user.getIdUsuario());
                wrapper.getStockLibertador().setEstado(true);
                break;
        }
        stockDAO.update(wrapper, idSucursal);
        JsonResponse msg = new JsonResponse("Success", "Producto modificado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/tierra/delete", method = RequestMethod.POST)
    public ResponseEntity<?> deleteStockTierra(OAuth2Authentication authentication,
            @RequestBody WrapperStock wrapper,
            @RequestParam("idScursal") int idSucursal) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        switch (idSucursal) {
            case 1:
                wrapper.getStockTierra().setFechaCreacion(new Date());
                wrapper.getStockTierra().setUsuarioCreacion(user.getIdUsuario());
                wrapper.getStockTierra().setEstado(true);
                break;
            case 2:
                wrapper.getStockBebelandia().setFechaCreacion(new Date());
                wrapper.getStockBebelandia().setUsuarioCreacion(user.getIdUsuario());
                wrapper.getStockBebelandia().setEstado(true);
                break;
            case 3:
                wrapper.getStockLibertador().setFechaCreacion(new Date());
                wrapper.getStockLibertador().setUsuarioCreacion(user.getIdUsuario());
                wrapper.getStockLibertador().setEstado(true);
                break;
        }
        /*Posible cambio a borrado logico*/
        stockDAO.delete(wrapper, idSucursal);
        JsonResponse msg = new JsonResponse("Success", "Producto modificado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
