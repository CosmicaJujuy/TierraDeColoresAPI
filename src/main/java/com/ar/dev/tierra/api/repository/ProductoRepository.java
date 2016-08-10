/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.repository;

import com.ar.dev.tierra.api.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author PauloGaldo
 */
public interface ProductoRepository extends Repository<Producto, String> {

    Page<Producto> findByEstadoProductoOrderByIdProductoDesc(boolean estadoProducto, Pageable pageable);

    @Query(
            "SELECT p from Producto p "
            + "INNER JOIN p.marcas m "
            + "INNER JOIN p.categoria c "
            + "WHERE m.nombreMarca LIKE %:marca% AND "
            + "c.nombreCategoria LIKE %:categoria% AND "
            + "p.descripcion LIKE %:descripcion% AND "
            + "p.codigoProducto LIKE %:codigo% AND "
            + "p.talla LIKE %:talla% AND p.estadoProducto = true "
            + "ORDER BY p.idProducto DESC"
    )
    Page<Producto> findByParams(
            @Param("descripcion") String descripcion,
            @Param("marca") String marca,
            @Param("talla") String talla,
            @Param("codigo") String codigo,
            @Param("categoria") String categoria,
            Pageable pageable);

    @Query("SELECT p FROM Producto "
            + "INNER JOIN p.facturaProducto fp "
            + "WHERE fp.idFacturaProducto = :idFactura AND "
            + "p.estadoProducto = true "
            + "ORDER BY´p.idProducto DESC")
    Page<Producto> findByIdFactura(@Param("idFactura") int idFactura, Pageable pageable);
}
