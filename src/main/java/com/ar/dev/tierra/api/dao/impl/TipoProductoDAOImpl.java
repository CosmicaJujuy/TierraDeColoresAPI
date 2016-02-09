/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.TipoProductoDAO;
import com.ar.dev.tierra.api.model.TipoProducto;
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
public class TipoProductoDAOImpl implements TipoProductoDAO {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<TipoProducto> getAll() {
        Criteria criteria = getSession().createCriteria(TipoProducto.class);
        criteria.addOrder(Order.asc("idTipo"));
        List<TipoProducto> list = criteria.list();
        return list;
    }
    
    @Override
    public void update(TipoProducto tipoProducto) {
        getSession().update(tipoProducto);
    }
    
    @Override
    public void add(TipoProducto tipoProducto) {
        getSession().save(tipoProducto);
    }
    
    @Override
    public void delete(TipoProducto tipoProducto) {
        getSession().delete(tipoProducto);
    }
    
}
