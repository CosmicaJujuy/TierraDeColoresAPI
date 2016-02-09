/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao;

import com.ar.dev.tierra.api.model.TipoProducto;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PauloGaldo
 */
@Component
@Repository
public interface TipoProductoDAO {

    public List<TipoProducto> getAll();

    public void update(TipoProducto tipoProducto);

    public void add(TipoProducto tipoProducto);

    public void delete(TipoProducto tipoProducto);
}
