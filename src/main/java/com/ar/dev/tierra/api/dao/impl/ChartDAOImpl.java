/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.ChartDAO;
import com.ar.dev.tierra.api.model.DetalleFactura;
import com.ar.dev.tierra.api.model.Usuarios;
import com.ar.dev.tierra.api.model.chart.CantidadFecha;
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
        List<ChartVentaVendedores> chartVentaVendedoreses = new ArrayList<>();
        while (days <= 6) {
            Criteria detallesFactura = getSession().createCriteria(DetalleFactura.class);
            detallesFactura.add(Restrictions.eq("estadoDetalle", true));
            Criteria facturas = detallesFactura.createCriteria("factura");
            facturas.add(Restrictions.like("estado", "CONFIRMADO"));
            facturas.add(Restrictions.eq("idVendedor", idVendedor));
            facturas.add(Restrictions.between("fechaCreacion", fromDate, toDate));
            facturas.setProjection(Projections.sum("total"));
            List<CantidadFecha> venta = new ArrayList<>();
        }

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
