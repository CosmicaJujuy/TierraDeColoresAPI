/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.service;

import com.ar.dev.tierra.api.repository.FacturaRepository;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 *
 * @author PauloGaldo
 */
@Service
public class FacturaService {

    @Autowired
    FacturaRepository facturaRepository;

    public Page getFacturasMonth(Integer page, Integer size) {
        Calendar calendar = Calendar.getInstance();
        Date toDate = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);
        Date fromDate = calendar.getTime();
        Page facturas = facturaRepository.findByEstadoNotAndFechaCreacionBetweenAndOrderByFacturaIdDesc("RESERVADO", fromDate, toDate, new PageRequest(page, size));
        return facturas;
    }

}
