/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.repository;

import com.ar.dev.tierra.api.model.Factura;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author PauloGaldo
 */
public interface FacturaRepository extends PagingAndSortingRepository<Factura, String> {

    Page<Factura> findByEstadoNotAndFechaCreacionBetweenDesc(String estado, Date from, Date to, Pageable pageable);

    Page<Factura> findByEstadoAndFechaCreacionBetweenDesc(String estado, Date from, Date to, Pageable pageable);

}
