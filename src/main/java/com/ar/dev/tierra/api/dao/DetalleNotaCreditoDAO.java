/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao;

import com.ar.dev.tierra.api.model.DetalleNotaCredito;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PauloGaldo
 */
@Component
@Repository
public interface DetalleNotaCreditoDAO {

    public List<DetalleNotaCredito> getByNotaCredito(int idNotaCredito);

    public void add(DetalleNotaCredito detalleNotaCredito);

    public void update(DetalleNotaCredito detalleNotaCredito);

    public void delete(DetalleNotaCredito detalleNotaCredito);

}
