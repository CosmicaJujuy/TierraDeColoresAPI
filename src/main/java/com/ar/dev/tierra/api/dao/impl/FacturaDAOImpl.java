/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.FacturaDAO;
import com.ar.dev.tierra.api.model.Factura;
import java.io.Serializable;
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
public class FacturaDAOImpl implements FacturaDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Factura> getAll() {
        Criteria criteria = getSession().createCriteria(Factura.class);
        criteria.addOrder(Order.asc("idFactura"));
        List<Factura> list = criteria.list();
        return list;
    }

    @Override
    public List<Factura> getDiary() {
        Criteria criteria = getSession().createCriteria(Factura.class);
        Date dayControl = new Date(); 
        Date compare = new Date(dayControl.getYear(), dayControl.getMonth(), dayControl.getDate());
        criteria.add(Restrictions.ge("fechaCreacion", compare));
        criteria.addOrder(Order.asc("idFactura"));
        List<Factura> list = criteria.list();
        return list;
    }

    @Override
    public void update(Factura factura) {
        getSession().update(factura);
    }

    @Override
    public int add(Factura factura) {
        Serializable idFactura = getSession().save(factura);
        return (int) idFactura;
    }

    @Override
    public void delete(Factura factura) {
        getSession().delete(factura);
    }

    @Override
    public Factura searchById(int idFactura) {
        Criteria criteria = getSession().createCriteria(Factura.class);
        criteria.add(Restrictions.eq("idFactura", idFactura));
        Factura foundFactura = (Factura) criteria.uniqueResult();
        return foundFactura;
    }

}
