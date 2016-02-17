/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao;

import com.ar.dev.tierra.api.model.EntidadBancaria;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PauloGaldo
 */
@Component
@Repository
public interface EntidadBancariaDAO {

    public List<EntidadBancaria> getAll();

    public void update(EntidadBancaria bancaria);

    public void add(EntidadBancaria bancaria);

    public void delete(EntidadBancaria bancaria);
}
