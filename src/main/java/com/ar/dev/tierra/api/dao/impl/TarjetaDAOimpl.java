/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.TarjetaDAO;
import com.ar.dev.tierra.api.model.Tarjeta;
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
public class TarjetaDAOimpl implements TarjetaDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tarjeta> getAll() {
        Criteria criteria = getSession().createCriteria(Tarjeta.class);
        criteria.add(Restrictions.eq("estadoTarjeta", true));
        criteria.addOrder(Order.asc("idTarjeta"));
        List<Tarjeta> list = criteria.list();
        return list;
    }

    @Override
    public void update(Tarjeta tarjeta) {
        getSession().update(tarjeta);
    }

    @Override
    public void add(Tarjeta tarjeta) {
        getSession().save(tarjeta);
    }

    @Override
    public void delete(Tarjeta tarjeta) {
        getSession().delete(tarjeta);
    }

}
