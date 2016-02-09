/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.MarcasDAO;
import com.ar.dev.tierra.api.model.Marcas;
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
public class MarcasDAImpl implements MarcasDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Marcas> getAll() {
        Criteria criteria = getSession().createCriteria(Marcas.class);
        criteria.addOrder(Order.asc("idMarca"));
        List<Marcas> list = criteria.list();
        return list;
    }
    
    @Override
    public void update(Marcas marcas) {
        getSession().update(marcas);
    }
    
    @Override
    public void add(Marcas marcas) {
        getSession().save(marcas);
    }
    
    @Override
    public void delete(Marcas marcas) {
        getSession().delete(marcas);
    }
    
}
