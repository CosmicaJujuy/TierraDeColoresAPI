/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.repository;

import com.ar.dev.tierra.api.model.FacturaProducto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

/**
 *
 * @author PauloGaldo
 */
public interface FacturaProductoRepository extends Repository<FacturaProducto, String> {

    @Query("SELECT fp FROM FacturaProducto fp "
            + "WHERE fp.estado = true "
            + "ORDER BY fp.idFacturaProducto DESC")
    Page<FacturaProducto>getAll(Pageable pageable);
}
