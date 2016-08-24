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
            + "WHERE lower(m.nombreMarca) LIKE concat('%', lower(:marca), '%') AND "
            + "lower(c.nombreCategoria) LIKE concat('%', lower(:categoria), '%') AND "
            + "lower(p.descripcion) LIKE concat('%', lower(:descripcion), '%') AND "
            + "p.codigoProducto LIKE %:codigo% AND "
            + "lower(p.talla) LIKE concat('%', lower(:talla), '%') AND "
            + "p.estadoProducto = true "
            + "ORDER BY p.idProducto DESC"
    )
    Page<Producto> findByParams(
            @Param("descripcion") String descripcion,
            @Param("marca") String marca,
            @Param("talla") String talla,
            @Param("codigo") String codigo,
            @Param("categoria") String categoria,
            Pageable pageable);

    @Query("SELECT p FROM Producto p "
            + "INNER JOIN p.facturaProducto fp "
            + "WHERE fp.idFacturaProducto = :idFactura AND "
            + "p.estadoProducto = true "
            + "ORDER BY p.idProducto DESC")
    Page<Producto> findByIdFactura(@Param("idFactura") int idFactura, Pageable pageable);

    @Query(
            "SELECT p from Producto p "
            + "INNER JOIN p.marcas m "
            + "INNER JOIN p.categoria c "
            + "INNER JOIN p.temporada t "
            + "INNER JOIN p.sexo s "
            + "INNER JOIN p.facturaProducto fp "
            + "INNER JOIN fp.proveedor pr "
            + "WHERE m.nombreMarca LIKE %:marca% AND "
            + "c.nombreCategoria LIKE %:categoria% AND "
            + "t.nombreTemporada LIKE %:temporada% AND "
            + "s.nombreSexo LIKE  %:sexo% AND "
            + "fp.numeroFactura LIKE %:factura% AND "
            + "lower(pr.nombreProveedor) LIKE concat('%', lower(:proveedor), '%') AND "
            + "lower(p.descripcion) LIKE concat('%', lower(:descripcion), '%') AND "
            + "lower(p.talla) LIKE concat('%', lower(:talla), '%') AND "
            + "p.codigoProducto LIKE %:codigo% AND "
            + "p.claseProducto LIKE %:clase% AND "
            + "lower(p.colorProducto) LIKE concat('%', lower(:color), '%') AND "
            + "p.estadoProducto = true "
            + "ORDER BY p.idProducto DESC"
    )
    Page<Producto> findByAllParams(
            @Param("descripcion") String descripcion,
            @Param("marca") String marca,
            @Param("talla") String talla,
            @Param("codigo") String codigo,
            @Param("categoria") String categoria,
            @Param("temporada") String temporada,
            @Param("sexo") String sexo,
            @Param("clase") String clase,
            @Param("color") String color,
            @Param("proveedor") String proveedor,
            @Param("factura") String factura,
            Pageable pageable);
}
