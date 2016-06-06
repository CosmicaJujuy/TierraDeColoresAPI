/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao;

import com.ar.dev.tierra.api.model.Cliente;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PauloGaldo
 */
@Component
@Repository
public interface ClienteDAO {

    public List<Cliente> getAll();

    public void update(Cliente cliente);

    public int add(Cliente cliente);

    public void delete(Cliente cliente);

    public Cliente searchById(int idCliente);

    public List<Cliente> searchByNombre(String nombre);

}
