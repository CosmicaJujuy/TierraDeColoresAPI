/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao;

import com.ar.dev.tierra.api.model.Factura;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PauloGaldo
 */
@Component
@Repository
public interface FacturaDAO {

    public List<Factura> getAll();
    
    public List<Factura> getDiary();

    public void update(Factura factura);

    public int add(Factura factura);

    public void delete(Factura factura);

    public Factura searchById(int idFactura);
    
}
