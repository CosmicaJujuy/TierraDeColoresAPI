/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.repository;

import com.ar.dev.tierra.api.model.StockBebelandia;
import java.io.Serializable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

/**
 *
 * @author PauloGaldo
 */
public interface StockBebelandiaRepository extends Repository<StockBebelandia, Serializable> {

    @Query("SELECT sb FROM StockBebelandia sb "
            + "WHERE sb.estado = true "
            + "ORDER BY sb.idStock DESC")
    Page<StockBebelandia> getAll(Pageable pageable);

}
