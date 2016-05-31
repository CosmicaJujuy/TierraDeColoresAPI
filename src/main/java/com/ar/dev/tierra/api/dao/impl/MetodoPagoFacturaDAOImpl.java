/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.MetodoPagoFacturaDAO;
import com.ar.dev.tierra.api.model.MetodoPagoFactura;
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
public class MetodoPagoFacturaDAOImpl implements MetodoPagoFacturaDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MetodoPagoFactura> getAll() {
        Criteria criteria = getSession().createCriteria(MetodoPagoFactura.class);
        criteria.add(Restrictions.eq("estado", true));
        criteria.addOrder(Order.asc("idMetodoPagoFactura"));
        List<MetodoPagoFactura> list = criteria.list();
        return list;
    }

    @Override
    public void add(MetodoPagoFactura pagoFactura) {
        getSession().save(pagoFactura);
    }

    @Override
    public void update(MetodoPagoFactura pagoFactura) {
        getSession().update(pagoFactura);
    }

    @Override
    public void delete(MetodoPagoFactura pagoFactura) {
        getSession().delete(pagoFactura);
    }

    @Override
    public List<MetodoPagoFactura> getFacturaMetodo(int idFactura) {
        Criteria criteria = getSession().createCriteria(MetodoPagoFactura.class);
        criteria.add(Restrictions.eq("estado", true));
        Criteria facturaCriteria = criteria.createCriteria("factura");
        facturaCriteria.add(Restrictions.eq("idFactura", idFactura));
        List<MetodoPagoFactura> list = criteria.list();
        return list;
    }

    @Override
    public List<MetodoPagoFactura> getDay() {
        Criteria criteria = getSession().createCriteria(MetodoPagoFactura.class);
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
        criteria.addOrder(Order.asc("idMetodoPagoFactura"));
        List<MetodoPagoFactura> list = criteria.list();
        return list;
    }

}
