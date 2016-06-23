/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao;

import com.ar.dev.tierra.api.model.NotaCredito;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PauloGaldo
 */
@Component
@Repository
public interface NotaCreditoDAO {

    public List<NotaCredito> getAll();

    public int add(NotaCredito notaCredito);

    public void update(NotaCredito notaCredito);

    public void delete(NotaCredito notaCredito);

    public List<NotaCredito> searchByIdCliente(int idCliente);

    public List<NotaCredito> getDaily();

    public List<NotaCredito> getMonth();
    
    public NotaCredito getByNumero(String numero);
    
    public NotaCredito getById(int idNota);

}
