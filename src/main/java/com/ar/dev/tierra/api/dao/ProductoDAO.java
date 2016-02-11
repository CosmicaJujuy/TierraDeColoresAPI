/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao;

import com.ar.dev.tierra.api.model.Producto;
import java.math.BigInteger;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PauloGaldo
 */
@Component
@Repository
public interface ProductoDAO {

    public List<Producto> getAll();

    public void update(Producto producto);

    public void add(Producto producto);

    public void delete(Producto producto);
    
    public Producto findById(BigInteger id);
}
