/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao;

import com.ar.dev.tierra.api.model.MetodoPagoFactura;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PauloGaldo
 */
@Component
@Repository
public interface MetodoPagoFacturaDAO {

    public List<MetodoPagoFactura> getAll();

    public void add(MetodoPagoFactura pagoFactura);

    public void update(MetodoPagoFactura pagoFactura);

    public void delete(MetodoPagoFactura pagoFactura);

    public List<MetodoPagoFactura> getFacturaMetodo(int idFactura);

}
