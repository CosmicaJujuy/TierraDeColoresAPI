/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.FacturaDAO;
import com.ar.dev.tierra.api.model.Factura;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
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
    public void update(Factura factura) {
        getSession().update(factura);
    }

    @Override
    public void add(Factura factura) {
        getSession().save(factura);
    }

    @Override
    public void delete(Factura factura) {
        getSession().delete(factura);
    }

}
