/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.service;

import com.ar.dev.tierra.api.model.stock.StockBebelandia;
import com.ar.dev.tierra.api.model.stock.StockLibertador;
import com.ar.dev.tierra.api.model.stock.StockTierra;
import com.ar.dev.tierra.api.repository.StockBebelandiaRepository;
import com.ar.dev.tierra.api.repository.StockLibertadorRepository;
import com.ar.dev.tierra.api.repository.StockTierraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author PauloGaldo
 */
@Service
public class StockService {

    @Autowired
    StockTierraRepository stockTierraRepository;

    @Autowired
    StockBebelandiaRepository stockBebelandiaRepository;

    @Autowired
    StockLibertadorRepository stockLibertadorRepository;

    public Page<StockTierra> getAllTierra(Integer page, Integer size) {
        Page paged = stockTierraRepository.getAll(new PageRequest(page, size));
        return paged;
    }

    public Page<StockBebelandia> getAllBebelandia(Integer page, Integer size) {
        Page paged = stockBebelandiaRepository.getAll(new PageRequest(page, size));
        return paged;
    }

    public Page<StockLibertador> getAllLibertador(Integer page, Integer size) {
        Page paged = stockLibertadorRepository.getAll(new PageRequest(page, size));
        return paged;
    }
}
