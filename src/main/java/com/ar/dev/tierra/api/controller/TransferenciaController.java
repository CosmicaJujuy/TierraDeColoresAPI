/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.mapper.StockMapper;
import com.ar.dev.tierra.api.model.DetalleTransferencia;
import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.StockBebelandia;
import com.ar.dev.tierra.api.model.StockLibertador;
import com.ar.dev.tierra.api.model.StockTierra;
import com.ar.dev.tierra.api.model.Transferencia;
import com.ar.dev.tierra.api.model.Usuarios;
import com.ar.dev.tierra.api.model.WrapperStock;
import com.ar.dev.tierra.api.resource.FacadeService;
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
@RequestMapping("transferencia")
public class TransferenciaController implements Serializable {

    @Autowired
    FacadeService facadeService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<?> getAll() {
        List<Transferencia> list = facadeService.getTransferenciaDAO().getAll();
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@RequestParam("idTransferencia") int idTransferencia) {
        Transferencia trans = facadeService.getTransferenciaDAO().getById(idTransferencia);
        if (trans != null) {
            return new ResponseEntity<>(trans, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Transferencia transferencia,
            OAuth2Authentication authentication) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        transferencia.setEstadoPedido(false);
        transferencia.setFechaCreacion(new Date());
        transferencia.setUsuarioCreacion(user.getIdUsuario());
        transferencia.setSucursalPedido(user.getUsuarioSucursal().getIdSucursal());
        int response = facadeService.getTransferenciaDAO().add(transferencia);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(@RequestBody Transferencia transferencia,
            OAuth2Authentication authentication) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        transferencia.setFechaModificacion(new Date());
        transferencia.setEstadoPedido(true);
        transferencia.setUsuarioModificacion(user.getIdUsuario());
        transferencia.setSucursalRespuesta(user.getUsuarioSucursal().getIdSucursal());
        if (transferencia.getSucursalPedido() != user.getUsuarioSucursal().getIdSucursal()) {
            facadeService.getTransferenciaDAO().add(transferencia);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            JsonResponse response = new JsonResponse("Error", "No puedes confirmar tu propia transferencia.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/day", method = RequestMethod.GET)
    public ResponseEntity<?> getDay() {
        List<Transferencia> list = facadeService.getTransferenciaDAO().getDaily();
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/month", method = RequestMethod.GET)
    public ResponseEntity<?> getMonth() {
        List<Transferencia> list = facadeService.getTransferenciaDAO().getMonth();
        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public ResponseEntity<?> cancel(@RequestBody Transferencia transferencia,
            OAuth2Authentication authentication) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        transferencia.setEstadoPedido(false);
        transferencia.setSucursalRespuesta(5);
        transferencia.setUsuarioModificacion(user.getIdUsuario());
        transferencia.setFechaModificacion(new Date());
        facadeService.getTransferenciaDAO().update(transferencia);
        JsonResponse msg = new JsonResponse("Exito", "Transferencia cancelada.");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public ResponseEntity<?> approve(@RequestParam("idTransferencia") int idTransferencia,
            OAuth2Authentication authentication) {
        Transferencia transferencia = facadeService.getTransferenciaDAO().getById(idTransferencia);
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        List<DetalleTransferencia> list = facadeService.getDetalleTransferenciaDAO().getByTransferencia(idTransferencia);
        if (user.getRoles().getIdRol() == 1 || user.getUsuarioSucursal().getIdSucursal() != transferencia.getSucursalPedido()) {
            boolean rest = true;
            for (DetalleTransferencia detalleTransferencia : list) {
                WrapperStock wrapperStock = facadeService.getStockDAO().searchStockById(detalleTransferencia.getIdStock(), detalleTransferencia.getIdSucursal());
                if (wrapperStock.getStockBebelandia() != null) {
                    if (wrapperStock.getStockBebelandia().getCantidad() < detalleTransferencia.getCantidad()) {
                        rest = false;
                    }
                }
                if (wrapperStock.getStockLibertador() != null) {
                    if (wrapperStock.getStockLibertador().getCantidad() < detalleTransferencia.getCantidad()) {
                        rest = false;
                    }
                }
                if (wrapperStock.getStockTierra() != null) {
                    if (wrapperStock.getStockTierra().getCantidad() < detalleTransferencia.getCantidad()) {
                        rest = false;
                    }
                }
            }
            if (rest) {
                for (DetalleTransferencia detalleTransferencia : list) {
                    @SuppressWarnings("UnusedAssignment")
                    int cantUpdate = 0;
                    WrapperStock wrapperStock = facadeService.getStockDAO().searchStockById(detalleTransferencia.getIdStock(), detalleTransferencia.getIdSucursal());
                    WrapperStock insertStock = new WrapperStock();
                    if (wrapperStock.getStockBebelandia() != null) {
                        cantUpdate = wrapperStock.getStockBebelandia().getCantidad();
                        wrapperStock.getStockBebelandia().setCantidad(cantUpdate - detalleTransferencia.getCantidad());
                        if (detalleTransferencia.getIdTransferencia().getSucursalPedido() == 1) {
                            StockTierra st = StockMapper.INSTANCE.StockBebelandiaToStockTierra(wrapperStock.getStockBebelandia());
                            st.setCantidad(detalleTransferencia.getCantidad());
                            st.setIdSucursal(1);
                            insertStock.setStockTierra(st);
                        } else if (detalleTransferencia.getIdTransferencia().getSucursalPedido() == 3) {
                            StockLibertador sl = StockMapper.INSTANCE.StockBebelandiaToStockLibertador(wrapperStock.getStockBebelandia());
                            sl.setCantidad(detalleTransferencia.getCantidad());
                            sl.setIdSucursal(3);
                            insertStock.setStockLibertador(sl);
                        }
                    }
                    if (wrapperStock.getStockLibertador() != null) {
                        cantUpdate = wrapperStock.getStockLibertador().getCantidad();
                        wrapperStock.getStockLibertador().setCantidad(cantUpdate - detalleTransferencia.getCantidad());
                        if (detalleTransferencia.getIdTransferencia().getSucursalPedido() == 1) {
                            StockTierra st = StockMapper.INSTANCE.StockLibertadorToStockTierra(wrapperStock.getStockLibertador());
                            st.setCantidad(detalleTransferencia.getCantidad());
                            st.setIdSucursal(1);
                            insertStock.setStockTierra(st);
                        } else if (detalleTransferencia.getIdTransferencia().getSucursalPedido() == 2) {
                            StockBebelandia sb = StockMapper.INSTANCE.StockLibertadorToStockBebelandia(wrapperStock.getStockLibertador());
                            sb.setCantidad(detalleTransferencia.getCantidad());
                            sb.setIdSucursal(3);
                            insertStock.setStockBebelandia(sb);
                        }
                    }
                    if (wrapperStock.getStockTierra() != null) {
                        cantUpdate = wrapperStock.getStockTierra().getCantidad();
                        wrapperStock.getStockTierra().setCantidad(cantUpdate - detalleTransferencia.getCantidad());
                        if (detalleTransferencia.getIdTransferencia().getSucursalPedido() == 3) {
                            StockLibertador sl = StockMapper.INSTANCE.StockTierraToStockLibertador(wrapperStock.getStockTierra());
                            sl.setCantidad(detalleTransferencia.getCantidad());
                            sl.setIdSucursal(1);
                            insertStock.setStockLibertador(sl);
                        } else if (detalleTransferencia.getIdTransferencia().getSucursalPedido() == 2) {
                            StockBebelandia sb = StockMapper.INSTANCE.StockTierraToStockBebelandia(wrapperStock.getStockTierra());
                            sb.setCantidad(detalleTransferencia.getCantidad());
                            sb.setIdSucursal(3);
                            insertStock.setStockBebelandia(sb);
                        }
                    }
                    facadeService.getStockDAO().add(insertStock);
                    facadeService.getStockDAO().update(wrapperStock);
                }
                if (user.getRoles().getIdRol() == 1) {
                    transferencia.setSucursalRespuesta(4);
                } else {
                    transferencia.setSucursalRespuesta(user.getUsuarioSucursal().getIdSucursal());
                }
                transferencia.setFechaModificacion(new Date());
                transferencia.setUsuarioModificacion(user.getIdUsuario());
                transferencia.setEstadoPedido(true);
                facadeService.getTransferenciaDAO().update(transferencia);
                JsonResponse msg = new JsonResponse("Exito", "Tu pedido fue aprobado.");
                return new ResponseEntity<>(msg, HttpStatus.OK);
            } else {
                JsonResponse msg = new JsonResponse("Error", "Stock insuficiente, revisa tu pedido de nuevo.");
                return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
            }
        } else {
            JsonResponse msg = new JsonResponse("Error", "No puedes aceptar tu propio pedido.");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }

}
