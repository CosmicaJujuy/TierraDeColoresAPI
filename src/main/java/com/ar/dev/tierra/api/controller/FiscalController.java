/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import com.ar.dev.tierra.api.model.Cliente;
import com.ar.dev.tierra.api.model.DetalleFactura;
import com.ar.dev.tierra.api.resource.FacadeService;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author PauloGaldo
 */
@RestController
@RequestMapping("/fiscal")
public class FiscalController implements Serializable {

    @Autowired
    FacadeService facadeService;

    @RequestMapping(value = "/ticket", method = RequestMethod.POST)
    @ResponseBody
    public FileSystemResource getTicket(@PathParam("factura") int factura) throws IOException, InterruptedException {
        List<DetalleFactura> detalles = facadeService.getDetalleFacturaDAO().facturaDetalle(factura);
        facadeService.getFiscalDAO().ticket(detalles);
        return new FileSystemResource("command/ticket.200");
    }

    @RequestMapping(value = "/factura_a", method = RequestMethod.POST)
    @ResponseBody
    public FileSystemResource getFactura_a(@PathParam("factura") int factura, @PathParam("cliente") int cliente) throws IOException, InterruptedException {
        List<DetalleFactura> detalles = facadeService.getDetalleFacturaDAO().facturaDetalle(factura);
        Cliente c = facadeService.getClienteDAO().searchById(cliente);
        facadeService.getFiscalDAO().factura_a(detalles, c);
        return new FileSystemResource("command/factura_a.200");
    }

    @RequestMapping(value = "/factura_b", method = RequestMethod.POST)
    @ResponseBody
    public FileSystemResource getFactura_b(@PathParam("factura") int factura, @PathParam("cliente") int cliente) throws IOException, InterruptedException {
        List<DetalleFactura> detalles = facadeService.getDetalleFacturaDAO().facturaDetalle(factura);
        Cliente c = facadeService.getClienteDAO().searchById(cliente);
        facadeService.getFiscalDAO().factura_a(detalles, c);
        return new FileSystemResource("command/factura_b.200");
    }

    @RequestMapping(value = "/regalo", method = RequestMethod.POST)
    @ResponseBody
    public FileSystemResource getRegalo(@PathParam("factura") int factura, @PathParam("serial") String serial) throws IOException, InterruptedException {
        List<DetalleFactura> detalles = facadeService.getDetalleFacturaDAO().facturaDetalle(factura);
        facadeService.getFiscalDAO().regalo(detalles, serial);
        return new FileSystemResource("command/regalo.200");
    }

}
