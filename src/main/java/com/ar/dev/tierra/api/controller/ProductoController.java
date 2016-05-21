/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.ProductoDAO;
import com.ar.dev.tierra.api.dao.StockDAO;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.Producto;
import com.ar.dev.tierra.api.model.stock.StockTierra;
import com.ar.dev.tierra.api.model.Usuarios;
import com.ar.dev.tierra.api.model.stock.StockBebelandia;
import com.ar.dev.tierra.api.model.stock.StockLibertador;
import com.ar.dev.tierra.api.model.stock.WrapperStock;
import java.io.Serializable;
import java.util.ArrayList;
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

    @Autowired
    StockDAO stockDAO;

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
        productoDAO.delete(producto);
        JsonResponse msg = new JsonResponse("Success", "Producto eliminado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<?> findById(@RequestParam("id") int id) {
        Producto p = productoDAO.findById(id);
        if (p == null || p.isEstadoProducto() == false) {
            JsonResponse jr = new JsonResponse("error", "No se encontro el producto");
            return new ResponseEntity<>(jr, HttpStatus.BAD_REQUEST);
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

    @RequestMapping(value = "/barcode", method = RequestMethod.POST)
    public ResponseEntity<?> findByBarcode(@RequestParam("barcode") String barcode) {
        List<Producto> list = productoDAO.findByBarcode(barcode);
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/stock", method = RequestMethod.POST)
    public ResponseEntity<?> findByBarcodeInStock(@RequestParam("barcode") String barcode,
            OAuth2Authentication authentication) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        List<WrapperStock> list = stockDAO.searchByBarcodeInStock(user.getUsuarioSucursal().getIdSucursal(), barcode);
        List<StockTierra> tierra = new ArrayList<>();
        List<StockBebelandia> bebelandia = new ArrayList<>();
        List<StockLibertador> libertador = new ArrayList<>();
        switch (user.getUsuarioSucursal().getIdSucursal()) {
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
    }

    @RequestMapping(value = "/list/factura", method = RequestMethod.POST)
    public ResponseEntity<?> findByIdFactura(@RequestParam("idFacturaProducto") int idFacturaProducto) {
        List<Producto> list = productoDAO.findByIdFactura(idFacturaProducto);
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/convert", method = RequestMethod.POST)
    public ResponseEntity<?> convert(@RequestParam("idFacturaProducto") int idFacturaProducto) {
        List<Producto> list = productoDAO.getAll();
        for (Producto producto : list) {
            producto.setEstadoDistribucion(false);
            productoDAO.update(producto);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
