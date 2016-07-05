/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao;

import com.ar.dev.tierra.api.model.DetalleTransferencia;
import com.ar.dev.tierra.api.model.stock.WrapperStock;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PauloGaldo
 */
@Component
@Repository
public interface DetalleTransferenciaDAO {

    public List<DetalleTransferencia> getAll();

    public List<DetalleTransferencia> getDaily();

    public List<DetalleTransferencia> getMonth();

    public void update(DetalleTransferencia detalleTransferencia);

    public void add(DetalleTransferencia detalleTransferencia);

    public void delete(DetalleTransferencia detalleTransferencia);

    public List<DetalleTransferencia> getByTransferencia(int idTransferencia);

    public List<WrapperStock> findByParams(String descripcion, String marca, String talla, String codigo, String categoria, int sucursal);

}
