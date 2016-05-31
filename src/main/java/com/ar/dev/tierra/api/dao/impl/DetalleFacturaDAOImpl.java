/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.DetalleFacturaDAO;
import com.ar.dev.tierra.api.model.DetalleFactura;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
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
public class DetalleFacturaDAOImpl implements DetalleFacturaDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DetalleFactura> getAll() {
        Criteria criteria = getSession().createCriteria(DetalleFactura.class);
        criteria.add(Restrictions.eq("estadoDetalle", true));
        criteria.addOrder(Order.asc("idDetalleFactura"));
        List<DetalleFactura> list = criteria.list();
        return list;
    }

    @Override
    public void add(DetalleFactura detalleFactura) {
        getSession().save(detalleFactura);
    }

    @Override
    public void update(DetalleFactura detalleFactura) {
        getSession().update(detalleFactura);
    }

    @Override
    public void delete(DetalleFactura detalleFactura) {
        getSession().delete(detalleFactura);
    }

    @Override
    public List<DetalleFactura> facturaDetalle(int idFactura) {
        Criteria detalleCriteria = getSession().createCriteria(DetalleFactura.class);
        detalleCriteria.add(Restrictions.eq("estadoDetalle", true));
        Criteria facturaCriteria = detalleCriteria.createCriteria("factura");
        facturaCriteria.add(Restrictions.eq("idFactura", idFactura));
        List<DetalleFactura> list = detalleCriteria.list();
        return list;
    }

    @Override
    public List<DetalleFactura> getDay() {
        Criteria criteria = getSession().createCriteria(DetalleFactura.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date fromDate = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date toDate = calendar.getTime();
        criteria.add(Restrictions.between("fechaCreacion", fromDate, toDate));
        criteria.addOrder(Order.asc("idDetalleFactura"));
        List<DetalleFactura> list = criteria.list();
        return list;
    }

}
