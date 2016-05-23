/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.dao.DetalleFacturaDAO;
import com.ar.dev.tierra.api.dao.FacturaDAO;
import com.ar.dev.tierra.api.dao.ProductoDAO;
import com.ar.dev.tierra.api.dao.StockDAO;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.DetalleFactura;
import com.ar.dev.tierra.api.model.Factura;
import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.Producto;
import com.ar.dev.tierra.api.model.Usuarios;
import com.ar.dev.tierra.api.model.stock.WrapperStock;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    FacturaDAO facturaDAO;

    @Autowired
    StockDAO stockDAO;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
            @RequestParam("idFactura") int idFactura,
            @RequestParam("idProducto") int idProducto,
            @RequestParam("idItem") int idItem,
            @RequestParam("cantidadItem") int cantidadItem) {
        /*Instancia de nuevo detalle*/
        DetalleFactura detalleFactura = new DetalleFactura();
        /*Traemos los objectos necesarios para añadir el detalle*/
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        Producto prod = productoDAO.findById(idProducto);
        WrapperStock stock = stockDAO.searchStockById(idItem, user.getUsuarioSucursal().getIdSucursal());
        Factura factura = facturaDAO.searchById(idFactura);
        /*Bandera de control*/
        boolean control = false;
        /*Variable de calculo de cantidad*/
        @SuppressWarnings("UnusedAssignment")
        int cantidadStock = 0;

        detalleFactura.setUsuarioCreacion(user.getIdUsuario());
        detalleFactura.setFechaCreacion(new Date());
        detalleFactura.setEstadoDetalle(true);

        if (prod.getCantidadTotal() >= cantidadItem) {
            if (stock.getStockTierra() != null) {
                if (stock.getStockTierra().getCantidad() >= cantidadItem) {
                    cantidadStock = stock.getStockTierra().getCantidad() - cantidadItem;
                    stock.getStockTierra().setCantidad(cantidadStock);
                    control = true;
                }
            }
            if (stock.getStockBebelandia() != null) {
                if (stock.getStockBebelandia().getCantidad() >= cantidadItem) {
                    cantidadStock = stock.getStockBebelandia().getCantidad() - cantidadItem;
                    stock.getStockBebelandia().setCantidad(cantidadStock);
                    control = true;
                }
            }
            if (stock.getStockLibertador() != null) {
                if (stock.getStockLibertador().getCantidad() >= cantidadItem) {
                    cantidadStock = stock.getStockLibertador().getCantidad() - cantidadItem;
                    stock.getStockLibertador().setCantidad(cantidadStock);
                    control = true;
                }
            }
            if (control) {
                int cantidadTotal = prod.getCantidadTotal();
                /*seteamos cantidad nueva de productos*/
                prod.setCantidadTotal(cantidadTotal - cantidadItem);
                /*seteamos producto en el detalle*/
                detalleFactura.setProducto(prod);
                /*Calculamos el total del detalle*/
                BigDecimal monto = detalleFactura.getProducto().getPrecioVenta().multiply(BigDecimal.valueOf(cantidadItem));
                /*seteamos el total del detalle*/
                detalleFactura.setTotalDetalle(monto);
                /*seteamos la factura del detalle*/
                detalleFactura.setFactura(factura);
                /*seteamos descuento en cero*/
                detalleFactura.setDescuentoDetalle(BigDecimal.ZERO);
                /*seteamos la cantidad de items en el detalle*/
                detalleFactura.setCantidadDetalle(cantidadItem);
                /*Actualizamos producto*/
                productoDAO.update(prod);
                /*Actualizamos el stock*/
                stockDAO.update(stock);
                /*Insertamos el nuevo detalle*/
                detalleFacturaDAO.add(detalleFactura);
                /*Traemos lista de detalles, calculamos su nuevo total y actualizamos*/
                List<DetalleFactura> detallesFactura = detalleFacturaDAO.facturaDetalle(idFactura);
                BigDecimal sumMonto = new BigDecimal(BigInteger.ZERO);
                for (DetalleFactura detailList : detallesFactura) {
                    sumMonto = sumMonto.add(detailList.getTotalDetalle());
                }
                factura.setTotal(sumMonto);
                factura.setFechaModificacion(new Date());
                factura.setUsuarioModificacion(user.getIdUsuario());
                facturaDAO.update(factura);
                JsonResponse msg = new JsonResponse("Success", "Detalle agregado con exito");
                return new ResponseEntity<>(msg, HttpStatus.OK);
            } else {
                JsonResponse msg = new JsonResponse("Error", "Stock insuficiente.");
                return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
            }
        } else {
            JsonResponse msg = new JsonResponse("Error", "Stock insuficiente.");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(OAuth2Authentication authentication,
            @RequestBody DetalleFactura detalleFactura) {
        Usuarios user = usuariosDAO.findUsuarioByUsername(authentication.getName());
        Factura factura = facturaDAO.searchById(detalleFactura.getFactura().getIdFactura());
        detalleFactura.setUsuarioModificacion(user.getIdUsuario());
        detalleFactura.setFechaModificacion(new Date());
        detalleFacturaDAO.update(detalleFactura);
        /*Traemos lista de detalles, calculamos su nuevo total y actualizamos*/
        List<DetalleFactura> detallesFactura = detalleFacturaDAO.facturaDetalle(detalleFactura.getFactura().getIdFactura());
        BigDecimal sumMonto = new BigDecimal(BigInteger.ZERO);
        for (DetalleFactura detailList : detallesFactura) {
            sumMonto = sumMonto.add(detailList.getTotalDetalle());
        }
        factura.setTotal(sumMonto);
        factura.setFechaModificacion(new Date());
        factura.setUsuarioModificacion(user.getIdUsuario());
        facturaDAO.update(factura);
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
    public ResponseEntity<?> listDetalleFactura(@RequestParam("idFactura") int idFactura) {
        List<DetalleFactura> list = detalleFacturaDAO.facturaDetalle(idFactura);
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/delete/discount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteDiscount(@RequestParam("dni") int dni,
            @RequestParam("password") String password,
            @RequestBody DetalleFactura detalleFactura) {
        Usuarios user = usuariosDAO.findUsuarioByDNI(dni);
        boolean permiso = passwordEncoder.matches(password, user.getPassword());
        if (permiso) {
            if (user.getRoles().getIdRol() == 1 || user.getRoles().getIdRol() == 6) {
                detalleFactura.setDescuentoDetalle(BigDecimal.ZERO);
                BigDecimal monto = detalleFactura.getProducto().getPrecioVenta().multiply(BigDecimal.valueOf(detalleFactura.getCantidadDetalle()));
                detalleFactura.setTotalDetalle(monto);
                detalleFacturaDAO.update(detalleFactura);
                List<DetalleFactura> detallesFactura = detalleFacturaDAO.facturaDetalle(detalleFactura.getFactura().getIdFactura());
                BigDecimal sumMonto = new BigDecimal(BigInteger.ZERO);
                Factura factura = facturaDAO.searchById(detalleFactura.getFactura().getIdFactura());
                for (DetalleFactura detailList : detallesFactura) {
                    sumMonto = sumMonto.add(detailList.getTotalDetalle());
                }
                factura.setTotal(sumMonto);
                factura.setFechaModificacion(new Date());
                factura.setUsuarioModificacion(user.getIdUsuario());
                facturaDAO.update(factura);
                JsonResponse msg = new JsonResponse("Success", "Descuento eliminado con exito.");
                return new ResponseEntity<>(msg, HttpStatus.OK);
            } else {
                JsonResponse msg = new JsonResponse("Error", "No tienes los permisos necesarios para realizar esta operacion.");
                return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
            }
        } else {
            JsonResponse msg = new JsonResponse("Error", "No tienes los permisos necesarios para realizar esta operacion.");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }

}
