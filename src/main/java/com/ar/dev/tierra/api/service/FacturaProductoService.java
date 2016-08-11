/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.service;

import com.ar.dev.tierra.api.repository.FacturaProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author PauloGaldo
 */
@Service
public class FacturaProductoService {
    
    @Autowired
    FacturaProductoRepository facturaProductoRepository;
    
    public Page getAllPaged(Integer page, Integer size){
        Page paged = facturaProductoRepository.getAll(new PageRequest(page, size));
        return paged;
    }
}
