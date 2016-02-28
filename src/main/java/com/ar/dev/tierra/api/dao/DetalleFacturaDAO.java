/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao;

import com.ar.dev.tierra.api.model.DetalleFactura;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PauloGaldo
 */
@Component
@Repository
public interface DetalleFacturaDAO {

    public List<DetalleFactura> getAll();

    public void add(DetalleFactura detalleFactura);

    public void update(DetalleFactura detalleFactura);

    public void delete(DetalleFactura detalleFactura);

    public List<DetalleFactura> facturaDetalle(int idFactura);

}
