/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.ProductoDAO;
import com.ar.dev.tierra.api.model.Producto;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
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
public class ProductoDAOImpl implements ProductoDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Producto> getAll() {
        Criteria criteria = getSession().createCriteria(Producto.class);
        criteria.add(Restrictions.eq("estadoProducto", true));
        criteria.addOrder(Order.asc("idProducto"));
        List<Producto> list = criteria.list();
        return list;
    }

    @Override
    public void update(Producto producto) {
        getSession().update(producto);
    }

    @Override
    public void add(Producto producto) {
        getSession().save(producto);
    }

    @Override
    public void delete(Producto producto) {
        getSession().delete(producto);
    }

    @Override
    public Producto findById(int id) {
        Criteria criteria = getSession().createCriteria(Producto.class);
        criteria.add(Restrictions.eq("idProducto", id));
        Producto producto = (Producto) criteria.uniqueResult();
        return producto;
    }

    @Override
    public List<Producto> findByText(String text) {
        Criteria criteria = getSession().createCriteria(Producto.class);
        criteria.add(Restrictions.ilike("descripcion", text, MatchMode.ANYWHERE));
        List<Producto> productos = criteria.list();
        return productos;
    }

    @Override
    public List<Producto> findByBarcode(String barcode) {
        Criteria criteria = getSession().createCriteria(Producto.class);
        criteria.add(Restrictions.ilike("codigoProducto", barcode, MatchMode.START));
        List<Producto> list = criteria.list();
        return list;
    }

}
