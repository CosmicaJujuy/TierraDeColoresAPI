/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao;

import com.ar.dev.tierra.api.model.Marcas;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PauloGaldo
 */
@Component
@Repository
public interface MarcasDAO {

    public List<Marcas> getAll();

    public void update(Marcas marcas);

    public void add(Marcas marcas);

    public void delete(Marcas marcas);

    public List<Marcas> searchByText(String text);
}
