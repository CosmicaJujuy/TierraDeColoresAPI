/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao;

import com.ar.dev.tierra.api.model.Proveedor;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PauloGaldo
 */
@Component
@Repository
public interface ProveedorDAO {

    public List<Proveedor> getAll();

    public void update(Proveedor proveedor);

    public void add(Proveedor proveedor);

    public void delete(Proveedor proveedor);
    
    public Proveedor searchById(int idProveedor);
    
    public List<Proveedor> searchByText(String text);
}
