/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.model.DetalleFactura;
import com.ar.dev.tierra.api.model.Factura;
import com.ar.dev.tierra.api.model.JsonResponse;
import com.ar.dev.tierra.api.model.MetodoPagoFactura;
import com.ar.dev.tierra.api.model.Metric;
import com.ar.dev.tierra.api.model.Producto;
import com.ar.dev.tierra.api.model.Usuarios;
import com.ar.dev.tierra.api.model.WrapperStock;
import com.ar.dev.tierra.api.resource.FacadeService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/factura")
public class FacturaController implements Serializable {

    @Autowired
    FacadeService facadeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        List<Factura> list = facadeService.getFacturaDAO().getAll();
        if (!list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> add(OAuth2Authentication authentication,
            @RequestBody Factura factura) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        factura.setUsuarioCreacion(user.getIdUsuario());
        factura.setFechaCreacion(new Date());
        factura.setEstado("INICIADO");
        factura.setIdSucursal(user.getUsuarioSucursal().getIdSucursal());
        factura.setTotal(BigDecimal.ZERO);
        int idFactura = facadeService.getFacturaDAO().add(factura);
        JsonResponse msg = new JsonResponse("Success", String.valueOf(idFactura));
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<?> update(OAuth2Authentication authentication,
            @RequestBody Factura factura) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        factura.setUsuarioModificacion(user.getIdUsuario());
        factura.setFechaModificacion(new Date());
        facadeService.getFacturaDAO().update(factura);
        JsonResponse msg = new JsonResponse("Success", "Factura modificada con exito");
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN, ROLE_ENCARGADO/VENDEDOR')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<?> delete(OAuth2Authentication authentication,
            @RequestParam("idFactura") int idFactura) {
        Usuarios user = facadeService.getUsuariosDAO().findUsuarioByUsername(authentication.getName());
        List<DetalleFactura> detalles = facadeService.getDetalleFacturaDAO().facturaDetalle(idFactura);
        List<MetodoPagoFactura> metodos = facadeService.getMetodoPagoFacturaDAO().getFacturaMetodo(idFactura);
        if (metodos.isEmpty()) {
            if (!detalles.isEmpty()) {
                for (DetalleFactura detalleFactura : detalles) {
                    detalleFactura.setEstadoDetalle(false);
                    detalleFactura.setUsuarioModificacion(user.getIdUsuario());
                    detalleFactura.setFechaModificacion(new Date());
                    facadeService.getDetalleFacturaDAO().update(detalleFactura);
                    @SuppressWarnings("UnusedAssignment")
                    int cantidadActual = 0;
                    WrapperStock stock = facadeService.getStockDAO().searchStockById(detalleFactura.getIdStock(), user.getUsuarioSucursal().getIdSucursal());
                    if (stock.getStockTierra() != null) {
                        cantidadActual = stock.getStockTierra().getCantidad();
                        stock.getStockTierra().setCantidad(cantidadActual + detalleFactura.getCantidadDetalle());
                    } else if (stock.getStockBebelandia() != null) {
                        cantidadActual = stock.getStockBebelandia().getCantidad();
                        stock.getStockBebelandia().setCantidad(cantidadActual + detalleFactura.getCantidadDetalle());
                    } else {
                        cantidadActual = stock.getStockLibertador().getCantidad();
                        stock.getStockLibertador().setCantidad(cantidadActual + detalleFactura.getCantidadDetalle());
                    }
                    facadeService.getStockDAO().update(stock);
                    Producto prodRest = facadeService.getProductoDAO().findById(detalleFactura.getProducto().getIdProducto());
                    int cantProd = prodRest.getCantidadTotal();
                    prodRest.setCantidadTotal(cantProd + detalleFactura.getCantidadDetalle());
                    facadeService.getProductoDAO().update(prodRest);
                }
            }
            Factura factura = facadeService.getFacturaDAO().searchById(idFactura);
            factura.setEstado("CANCELADO");
            factura.setFechaModificacion(new Date());
            factura.setTotal(BigDecimal.ZERO);
            factura.setUsuarioModificacion(user.getIdUsuario());
            facadeService.getFacturaDAO().update(factura);
            JsonResponse msg = new JsonResponse("Success", "Factura cancelada con exito.");
            return new ResponseEntity<>(msg, HttpStatus.OK);
        } else {
            JsonResponse msg = new JsonResponse("Error", "No puedes eliminar una factura con pagos realizados");
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<?> searchById(@RequestParam("idFactura") int id) {
        Factura factura = facadeService.getFacturaDAO().searchById(id);
        if (factura != null) {
            return new ResponseEntity<>(factura, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/day", method = RequestMethod.GET)
    public ResponseEntity<?> getDay() {
        List<Factura> factura = facadeService.getFacturaDAO().getDiary();
        if (factura != null) {
            return new ResponseEntity<>(factura, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/day/paged", method = RequestMethod.GET)
    public ResponseEntity<?> getDayPaged(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        Page factura = facadeService.getFacturaService().getFacturasDay(page, size);
        if (factura != null) {
            return new ResponseEntity<>(factura, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/month", method = RequestMethod.GET)
    public ResponseEntity<?> getMonth() {
        List<Factura> factura = facadeService.getFacturaDAO().getMonth();
        if (factura != null) {
            return new ResponseEntity<>(factura, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/month/paged", method = RequestMethod.GET)
    public ResponseEntity<?> getMonthPaged(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        Page factura = facadeService.getFacturaService().getFacturasMonth(page, size);
        if (factura != null) {
            return new ResponseEntity<>(factura, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/metrics", method = RequestMethod.GET)
    @SuppressWarnings("null")
    public ResponseEntity<?> metrics() {
        Metric metric = new Metric();
        metric.setEfectivoHoy(facadeService.getFacturaService().getEfectivoHoy());
        metric.setEfectivoMensual(facadeService.getFacturaService().getEfectivoMensual());
        metric.setImpresasHoy(facadeService.getFacturaService().getCountByImpresionHoy());
        metric.setImpresasMensual(facadeService.getFacturaService().getCountByImpresionMensual());
        metric.setTotalFacturasHoy(facadeService.getFacturaService().getFacturaSumByDay());
        metric.setTotalFacturasMensual(facadeService.getFacturaService().getFacturaSumByMonth());
        metric.setTotalReservasHoy(facadeService.getFacturaService().getReservaSumByDay());
        metric.setTotalReservasMensual(facadeService.getFacturaService().getReservaSumByMonth());
        return new ResponseEntity<>(metric, HttpStatus.OK);
    }

}
