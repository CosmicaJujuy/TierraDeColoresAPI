/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.DetalleTransferenciaDAO;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.DetalleTransferencia;
import com.ar.dev.tierra.api.model.Usuarios;
import com.ar.dev.tierra.api.model.stock.StockBebelandia;
import com.ar.dev.tierra.api.model.stock.StockLibertador;
import com.ar.dev.tierra.api.model.stock.StockTierra;
import com.ar.dev.tierra.api.model.stock.WrapperStock;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author PauloGaldo
 */
@RestController
@RequestMapping("transferencia/detalle")
public class DetalleTransferenciaController implements Serializable {

    @Autowired
    DetalleTransferenciaDAO detalleTransferenciaDAO;

    @Autowired
    UsuariosDAO usuariosDAO;

    @RequestMapping(value = "/trans", method = RequestMethod.GET)
    public ResponseEntity<?> getByTransferencia(@RequestParam("idTransferencia") int idTransferencia) {
        List<DetalleTransferencia> list = detalleTransferenciaDAO.getByTransferencia(idTransferencia);
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<?> findByParams(
            @RequestParam("descripcion") String descripcion,
            @RequestParam("marca") String marca,
            @RequestParam("talla") String talla,
            @RequestParam("codigo") String codigo,
            @RequestParam("categoria") String categoria,
            @RequestParam("sucursal") int sucursal,
            OAuth2Authentication authentication
    ) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        if (user.getUsuarioSucursal().getIdSucursal() != sucursal) {

            List<WrapperStock> list = detalleTransferenciaDAO.findByParams(descripcion, marca, talla, codigo, categoria, sucursal);
            List<StockTierra> tierra = new ArrayList<>();
            List<StockBebelandia> bebelandia = new ArrayList<>();
            List<StockLibertador> libertador = new ArrayList<>();
            switch (sucursal) {
                case 1:
                    for (WrapperStock wrapperStock : list) {
                        tierra.add(wrapperStock.getStockTierra());
                    }
                    break;
                case 2:
                    for (WrapperStock wrapperStock : list) {
                        bebelandia.add(wrapperStock.getStockBebelandia());
                    }
                    break;
                case 3:
                    for (WrapperStock wrapperStock : list) {
                        libertador.add(wrapperStock.getStockLibertador());
                    }
                    break;
            }
            if (tierra.isEmpty() && bebelandia.isEmpty()) {
                return new ResponseEntity<>(libertador, HttpStatus.OK);
            } else if (bebelandia.isEmpty() && libertador.isEmpty()) {
                return new ResponseEntity<>(tierra, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(bebelandia, HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
