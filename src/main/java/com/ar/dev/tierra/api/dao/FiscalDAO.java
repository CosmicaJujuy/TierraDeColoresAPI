/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao;

import com.ar.dev.tierra.api.model.Cliente;
import com.ar.dev.tierra.api.model.DetalleFactura;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author PauloGaldo
 */
@Component
public interface FiscalDAO {

    public void ticket(List<DetalleFactura> detalles);

    public void factura_a(List<DetalleFactura> detalles, Cliente cliente);

    public void factura_b(List<DetalleFactura> detalles, Cliente cliente);

    public void factura_c(List<DetalleFactura> detalles, Cliente cliente);
    
    public void regalo(List<DetalleFactura> detalles, String serial);

}
