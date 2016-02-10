/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.ProveedorDAO;
import com.ar.dev.tierra.api.model.Proveedor;
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
public class ProveedorDAOImpl implements ProveedorDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Proveedor> getAll() {
        Criteria criteria = getSession().createCriteria(Proveedor.class);
        criteria.add(Restrictions.eq("estadoProveedor", true));
        criteria.addOrder(Order.asc("idProveedor"));
        List<Proveedor> list = criteria.list();
        return list;
    }

    @Override
    public void update(Proveedor proveedor) {
        getSession().update(proveedor);
    }

    @Override
    public void add(Proveedor proveedor) {
        getSession().save(proveedor);
    }

    @Override
    public void delete(Proveedor proveedor) {
        getSession().delete(proveedor);
    }

}
