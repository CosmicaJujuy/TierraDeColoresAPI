/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao;

import com.ar.dev.tierra.api.model.Transferencia;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PauloGaldo
 */
@Component
@Repository
public interface TransferenciaDAO {

    public List<Transferencia> getAll();

    public List<Transferencia> getDaily();

    public List<Transferencia> getMonth();

    public void update(Transferencia transferencia);

    public void add(Transferencia transferencia);

    public void delete(Transferencia transferencia);

}
