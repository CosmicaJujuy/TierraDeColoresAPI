/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.CategoriaDAO;
import com.ar.dev.tierra.api.model.Categoria;
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
public class CategoriaDAOImpl implements CategoriaDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Categoria> getAll() {
        Criteria criteria = getSession().createCriteria(Categoria.class);
        criteria.add(Restrictions.eq("estado", true));
        criteria.addOrder(Order.asc("idCategoria"));
        List<Categoria> list = criteria.list();
        return list;
    }

    @Override
    public void update(Categoria categoria) {
        getSession().update(categoria);
    }

    @Override
    public void add(Categoria categoria) {
        getSession().save(categoria);
    }

    @Override
    public void delete(Categoria categoria) {
        getSession().delete(categoria);
    }

}
