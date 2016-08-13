/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.repository;

import com.ar.dev.tierra.api.model.MetodoPagoFactura;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author PauloGaldo
 */
public interface MetodoPagoFacturaRepository extends Repository<MetodoPagoFactura, Serializable> {

    @Query("SELECT SUM(m.montoPago) FROM MetodoPagoFactura m "
            + "WHERE m.planPago = 1 AND "
            + "m.fechaCreacion BETWEEN "
            + ":from AND :to")
    BigDecimal sumEfectivoByDate(@Param("from") Date from, @Param("to") Date to);

}
