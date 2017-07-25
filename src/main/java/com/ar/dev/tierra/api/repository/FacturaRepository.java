/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.repository;

import com.ar.dev.tierra.api.model.Factura;
import com.ar.dev.tierra.api.model.FacturaProjection;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author PauloGaldo
 */
public interface FacturaRepository extends Repository<Factura, String> {

    @Query("SELECT NEW com.ar.dev.tierra.api.model.FacturaProjection( "
            + "F.idFactura, "
            + "F.estado, "
            + "C.nombreCliente, "
            + "F.total, "
            + "F.fechaCreacion, "
            + "U.idUsuario, "
            + "U.nombre, "
            + "U.apellido, "
            + "F.numeracion, "
            + "F.regalo) "
            + "FROM Factura F "
            + "LEFT JOIN F.cliente C "
            + "LEFT JOIN F.idVendedor U "            
            + "WHERE F.estado <> 'RESERVADO' "
            + "AND F.idSucursal = :idSucursal "
            + "AND F.fechaCreacion BETWEEN "
            + ":from AND :to "
            + "ORDER BY F.idFactura DESC"
    )
    Page<FacturaProjection> findFacturasByDate(@Param("from") Date from, @Param("to") Date to, Pageable pageable, @Param("idSucursal") int idSucursal);

    @Query("SELECT f FROM Factura f "
            + "WHERE f.estado = 'RESERVADO' AND "
            + "(f.idSucursal = :idSucursal) AND "
            + "f.fechaCreacion BETWEEN "
            + ":from AND :to "
            + "ORDER BY f.idFactura DESC")
    Page<Factura> findReservasByDate(@Param("from") Date from, @Param("to") Date to, Pageable pageable, @Param("idSucursal") int idSucursal);

    @Query("SELECT SUM(f.total) FROM Factura f "
            + "WHERE f.estado = 'CONFIRMADO' AND "
            + "(f.idSucursal = :idSucursal) AND "
            + "f.fechaCreacion BETWEEN "
            + ":from AND :to")
    BigDecimal sumFacturasEnded(@Param("from") Date from, @Param("to") Date to, @Param("idSucursal") int idSucursal);

    @Query("SELECT SUM(f.total) FROM Factura f "
            + "WHERE f.estado = 'RESERVADO' AND "
            + "(f.idSucursal = :idSucursal) AND "
            + "f.fechaCreacion BETWEEN "
            + ":from AND :to")
    BigDecimal sumReservas(@Param("from") Date from, @Param("to") Date to, @Param("idSucursal") int idSucursal);

    @Query("SELECT COUNT(f.idFactura) FROM Factura f "
            + "WHERE f.numeracion IS NOT NULL AND "
            + "(f.idSucursal = :idSucursal) AND "
            + "f.fechaCreacion BETWEEN "
            + ":from AND :to")
    int countByImpresionByDate(@Param("from") Date from, @Param("to") Date to, @Param("idSucursal") int idSucursal);

}
