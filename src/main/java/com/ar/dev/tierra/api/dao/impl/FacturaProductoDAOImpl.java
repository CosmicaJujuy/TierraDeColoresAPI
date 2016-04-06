/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.FacturaProductoDAO;
import com.ar.dev.tierra.api.model.FacturaProducto;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
public class FacturaProductoDAOImpl implements FacturaProductoDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FacturaProducto> getAll() {
        Criteria criteria = getSession().createCriteria(FacturaProducto.class);
        criteria.add(Restrictions.eq("estado", true));
        List<FacturaProducto> list = criteria.list();
        return list;
    }

    @Override
    public int add(FacturaProducto facturaProducto) {
        Serializable idFacturaProducto = getSession().save(facturaProducto);
        return (int) idFacturaProducto;
    }

    @Override
    public void update(FacturaProducto facturaProducto) {
        getSession().update(facturaProducto);
    }

    @Override
    public void delete(FacturaProducto facturaProducto) {
        getSession().delete(facturaProducto);
    }

    @Override
    public FacturaProducto detail(int idFacturaProducto) {
        Criteria criteria = getSession().createCriteria(FacturaProducto.class);
        criteria.add(Restrictions.eq("idFacturaProducto", idFacturaProducto));
        FacturaProducto facturaProducto = (FacturaProducto) criteria.uniqueResult();
        return facturaProducto;
    }

}