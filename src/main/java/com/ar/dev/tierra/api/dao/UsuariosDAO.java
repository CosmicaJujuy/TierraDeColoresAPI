/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao;

import com.ar.dev.tierra.api.model.Usuarios;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PauloGaldo
 */
@Component
@Repository
public interface UsuariosDAO {

    public List<Usuarios> allUsuarios();

    public List<Usuarios> getVendedores();

    public void addUsuario(Usuarios usuarios);

    public void updateUsuario(Usuarios usuarios);

    public Usuarios findUsuarioByUsername(String username);

    public Usuarios findUsuarioByDNI(Integer dni);

    public Usuarios findUsuarioById(Integer id);

}
