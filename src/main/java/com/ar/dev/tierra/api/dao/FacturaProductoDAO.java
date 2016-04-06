/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao;

import com.ar.dev.tierra.api.model.FacturaProducto;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PauloGaldo
 */
@Component
@Repository
public interface FacturaProductoDAO {

    public List<FacturaProducto> getAll();

    public int add(FacturaProducto facturaProducto);

    public void update(FacturaProducto facturaProducto);

    public void delete(FacturaProducto facturaProducto);
    
    public FacturaProducto detail(int idFacturaProducto);

}
