/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.Usuarios;
import com.ar.dev.tierra.api.model.StockBebelandia;
import com.ar.dev.tierra.api.model.StockLibertador;
import com.ar.dev.tierra.api.model.StockTierra;
import com.ar.dev.tierra.api.model.WrapperStock;
import com.ar.dev.tierra.api.resource.FacadeService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    FacadeService facadeService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<?> list(@RequestParam("idStock") int idStock) {
        List<WrapperStock> list = facadeService.getStockDAO().getAll(idStock);
        List<StockTierra> tierra = new ArrayList<>();
        List<StockBebelandia> bebelandia = new ArrayList<>();
        List<StockLibertador> libertador = new ArrayList<>();
        switch (idStock) {
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

    @RequestMapping(value = "/list/paged", method = RequestMethod.GET)
    @SuppressWarnings("UnusedAssignment")
    public ResponseEntity<?> listPaged(
            @RequestParam("idStock") int idStock,
            @RequestParam(value = "page", required = false, defaultValue = "") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "") Integer size) {
        Page paged = null;
        switch (idStock) {
            case 1:
                paged = facadeService.getStockService().getAllTierra(page, size);
                break;
            case 2:
                paged = facadeService.getStockService().getAllBebelandia(page, size);
                break;
            case 3:
                paged = facadeService.getStockService().getAllLibertador(page, size);
                break;
        }
        return new ResponseEntity<>(paged, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addStockTierra(OAuth2Authentication authentication,
            @RequestBody WrapperStock wrapper) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        Date today = new Date();
        if (wrapper.getStockTierra() != null) {
            wrapper.getStockTierra().setFechaCreacion(today);
            wrapper.getStockTierra().setUsuarioCreacion(user.getIdUsuario());
            wrapper.getStockTierra().setEstado(true);
            wrapper.getStockTierra().setIdSucursal(1);
        }
        if (wrapper.getStockBebelandia() != null) {
            wrapper.getStockBebelandia().setFechaCreacion(today);
            wrapper.getStockBebelandia().setUsuarioCreacion(user.getIdUsuario());
            wrapper.getStockBebelandia().setEstado(true);
            wrapper.getStockBebelandia().setIdSucursal(2);
        }
        if (wrapper.getStockLibertador() != null) {
            wrapper.getStockLibertador().setFechaCreacion(today);
            wrapper.getStockLibertador().setUsuarioCreacion(user.getIdUsuario());
            wrapper.getStockLibertador().setEstado(true);
            wrapper.getStockLibertador().setIdSucursal(3);
        }
        facadeService.getStockDAO().add(wrapper);
        JsonResponse msg = new JsonResponse("Success", "Producto agregado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/tierra/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateStockTierra(OAuth2Authentication authentication,
            @RequestBody WrapperStock wrapper) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        if (wrapper.getStockTierra() != null) {
            wrapper.getStockTierra().setFechaCreacion(new Date());
            wrapper.getStockTierra().setUsuarioCreacion(user.getIdUsuario());
            wrapper.getStockTierra().setEstado(true);
        }
        if (wrapper.getStockBebelandia() != null) {
            wrapper.getStockBebelandia().setFechaCreacion(new Date());
            wrapper.getStockBebelandia().setUsuarioCreacion(user.getIdUsuario());
            wrapper.getStockBebelandia().setEstado(true);
        }
        if (wrapper.getStockLibertador() != null) {
            wrapper.getStockLibertador().setFechaCreacion(new Date());
            wrapper.getStockLibertador().setUsuarioCreacion(user.getIdUsuario());
            wrapper.getStockLibertador().setEstado(true);
        }
        facadeService.getStockDAO().update(wrapper);
        JsonResponse msg = new JsonResponse("Success", "Producto modificado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/tierra/delete", method = RequestMethod.POST)
    public ResponseEntity<?> deleteStockTierra(OAuth2Authentication authentication,
            @RequestBody WrapperStock wrapper) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        if (wrapper.getStockTierra() != null) {
            wrapper.getStockTierra().setFechaCreacion(new Date());
            wrapper.getStockTierra().setUsuarioCreacion(user.getIdUsuario());
            wrapper.getStockTierra().setEstado(true);
        }
        if (wrapper.getStockBebelandia() != null) {
            wrapper.getStockBebelandia().setFechaCreacion(new Date());
            wrapper.getStockBebelandia().setUsuarioCreacion(user.getIdUsuario());
            wrapper.getStockBebelandia().setEstado(true);
        }
        if (wrapper.getStockLibertador() != null) {
            wrapper.getStockLibertador().setFechaCreacion(new Date());
            wrapper.getStockLibertador().setUsuarioCreacion(user.getIdUsuario());
            wrapper.getStockLibertador().setEstado(true);
        }
        /*Posible cambio a borrado logico*/
        facadeService.getStockDAO().delete(wrapper);
        JsonResponse msg = new JsonResponse("Success", "Producto modificado con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/tierra/search", method = RequestMethod.POST)
    public ResponseEntity<?> searchByFacturaTierra(@RequestParam("idFactura") int idFactura) {
        List<StockTierra> tierra = facadeService.getStockDAO().searchByFacturaStockTierra(idFactura);
        if (!tierra.isEmpty()) {
            return new ResponseEntity<>(tierra, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/bebelandia/search", method = RequestMethod.POST)
    public ResponseEntity<?> searchByFacturaBebelandia(@RequestParam("idFactura") int idFactura) {
        List<StockBebelandia> bebelandia = facadeService.getStockDAO().searchByFacturaStockBebelandia(idFactura);
        if (!bebelandia.isEmpty()) {
            return new ResponseEntity<>(bebelandia, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/libertador/search", method = RequestMethod.POST)
    public ResponseEntity<?> searchByFacturaLibertador(@RequestParam("idFactura") int idFactura) {
        List<StockLibertador> libertador = facadeService.getStockDAO().searchByFacturaStockLibertador(idFactura);
        if (!libertador.isEmpty()) {
            return new ResponseEntity<>(libertador, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
