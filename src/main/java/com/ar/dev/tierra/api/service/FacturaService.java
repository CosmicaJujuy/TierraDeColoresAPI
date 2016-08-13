/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.service;

import com.ar.dev.tierra.api.repository.FacturaRepository;
import com.ar.dev.tierra.api.repository.MetodoPagoFacturaRepository;
import java.math.BigDecimal;
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
    MetodoPagoFacturaRepository metodoPagoFacturaRepository;

    @Autowired
    FacturaRepository facturaRepository;

    public Page getFacturasMonth(Integer page, Integer size) {
        Calendar calendar = Calendar.getInstance();
        Date toDate = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);
        Date fromDate = calendar.getTime();
        Page facturas = facturaRepository.findFacturasByDate(fromDate, toDate, new PageRequest(page, size));
        return facturas;
    }

    public Page getFacturasDay(Integer page, Integer size) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date fromDate = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date toDate = calendar.getTime();
        Page facturas = facturaRepository.findFacturasByDate(fromDate, toDate, new PageRequest(page, size));
        return facturas;
    }

    public Page getReservasMonth(Integer page, Integer size) {
        Calendar calendar = Calendar.getInstance();
        Date to = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);
        Date from = calendar.getTime();
        Page paged = facturaRepository.findReservasByDate(from, to, new PageRequest(page, size));
        return paged;
    }

    public Page getReservasDay(Integer page, Integer size) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date from = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date to = calendar.getTime();
        Page paged = facturaRepository.findReservasByDate(from, to, new PageRequest(page, size));
        return paged;
    }

    public BigDecimal getFacturaSumByMonth() {
        Calendar calendar = Calendar.getInstance();
        Date toDate = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);
        Date fromDate = calendar.getTime();
        BigDecimal total = facturaRepository.sumFacturasEnded(fromDate, toDate);
        return total;
    }

    public BigDecimal getFacturaSumByDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date fromDate = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date toDate = calendar.getTime();
        BigDecimal total = facturaRepository.sumFacturasEnded(fromDate, toDate);
        return total;
    }

    public BigDecimal getReservaSumByMonth() {
        Calendar calendar = Calendar.getInstance();
        Date toDate = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);
        Date fromDate = calendar.getTime();
        BigDecimal total = facturaRepository.sumReservas(fromDate, toDate);
        return total;
    }

    public BigDecimal getReservaSumByDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date fromDate = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date toDate = calendar.getTime();
        BigDecimal total = facturaRepository.sumReservas(fromDate, toDate);
        return total;
    }

    public BigDecimal getEfectivoMensual() {
        Calendar calendar = Calendar.getInstance();
        Date toDate = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);
        Date fromDate = calendar.getTime();
        BigDecimal sum = metodoPagoFacturaRepository.sumEfectivoByDate(fromDate, toDate);
        return sum;
    }

    public BigDecimal getEfectivoHoy() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date fromDate = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date toDate = calendar.getTime();
        BigDecimal sum = metodoPagoFacturaRepository.sumEfectivoByDate(fromDate, toDate);
        return sum;
    }

    public int getCountByImpresionMensual() {
        Calendar calendar = Calendar.getInstance();
        Date to = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);
        Date from = calendar.getTime();
        int count = facturaRepository.countByImpresionByDate(from, to);
        return count;
    }

    public int getCountByImpresionHoy() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date from = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date to = calendar.getTime();
        int count = facturaRepository.countByImpresionByDate(from, to);
        return count;
    }

}
