/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.ChartDAO;
import com.ar.dev.tierra.api.model.DetalleFactura;
import com.ar.dev.tierra.api.model.Factura;
import com.ar.dev.tierra.api.model.chart.ChartVentaVendedores;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author PauloGaldo
 */
@Repository
@Transactional
public class ChartDAOImpl implements ChartDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ChartVentaVendedores> getVentaVendedores(int idVendedor) {
        Calendar calendarInitial = Calendar.getInstance();
        Calendar calendarClosing = Calendar.getInstance();
        calendarInitial.set(Calendar.HOUR_OF_DAY, 0);
        calendarInitial.set(Calendar.MINUTE, 0);
        calendarInitial.set(Calendar.SECOND, 0);
        Date fromDate = calendarInitial.getTime();
        calendarClosing.set(Calendar.HOUR_OF_DAY, 23);
        calendarClosing.set(Calendar.MINUTE, 59);
        calendarClosing.set(Calendar.SECOND, 59);
        Date toDate = calendarClosing.getTime();
        int days = 0;
        List<ChartVentaVendedores> chartVenta = new ArrayList<>();
        while (days <= 6) {
            ChartVentaVendedores chart = new ChartVentaVendedores();
            Criteria facturas = getSession().createCriteria(Factura.class);
            facturas.add(Restrictions.like("estado", "CONFIRMADO"));
            facturas.add(Restrictions.between("fechaCreacion", fromDate, toDate));
            Criteria vendedorFactura = facturas.createCriteria("idVendedor");
            vendedorFactura.add(Restrictions.eq("idUsuario", idVendedor));
            vendedorFactura.setProjection(Projections.rowCount());
            Long counter = (Long) facturas.uniqueResult();
            chart.setRowCount(counter.intValue());
            chart.setDate(fromDate);
            chartVenta.add(chart);
            calendarInitial.add(Calendar.DAY_OF_MONTH, -1);
            fromDate = calendarInitial.getTime();
            calendarClosing.add(Calendar.DAY_OF_MONTH, -1);
            toDate = calendarClosing.getTime();
            days++;
        }
        return chartVenta;
    }

}
