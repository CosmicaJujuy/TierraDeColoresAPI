/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.service;

import com.ar.dev.tierra.api.repository.ProductoRepository;
import com.ar.dev.tierra.api.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author PauloGaldo
 */
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Page<Producto> getAllProdutos(Integer page, Integer size) {
        Page pageOfProductos = productoRepository.findByEstadoProductoOrderByIdProductoDesc(true, new PageRequest(page, size));
        return pageOfProductos;
    }

    public Page<Producto> getByParams(
            String descripcion,
            String marca,
            String talla,
            String codigo,
            String categoria,
            Integer page,
            Integer size) {
        Page pageParams = productoRepository.findByParams(descripcion, marca, talla, codigo, categoria, new PageRequest(page, size));
        return pageParams;
    }

    public Page<Producto> findByFactura(Integer page, Integer size, int idFactura) {
        Page paged = productoRepository.findByIdFactura(idFactura, new PageRequest(page, size));
        return paged;
    }

    public Page<Producto> getByAllParams(String descripcion, String marca, String talla, String codigo,
            String categoria, String temporada, String sexo, String clase, String color, String proveedor,
            String factura, Integer page, Integer size) {
        Page pageParams = productoRepository.findByAllParams(
                descripcion, marca, talla, codigo, categoria, temporada,
                sexo, clase, color, proveedor, factura,
                new PageRequest(page, size));
        return pageParams;
    }
}
