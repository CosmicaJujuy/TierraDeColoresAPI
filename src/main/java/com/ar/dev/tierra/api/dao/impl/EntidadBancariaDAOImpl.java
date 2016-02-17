/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.EntidadBancariaDAO;
import com.ar.dev.tierra.api.model.EntidadBancaria;
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
public class EntidadBancariaDAOImpl implements EntidadBancariaDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EntidadBancaria> getAll() {
        Criteria criteria = getSession().createCriteria(EntidadBancaria.class);
        criteria.add(Restrictions.eq("estadoEntidad", true));
        criteria.addOrder(Order.asc("idEntidadMonetaria"));
        List<EntidadBancaria> list = criteria.list();
        return list;
    }

    @Override
    public void update(EntidadBancaria bancaria) {
        getSession().update(bancaria);
    }

    @Override
    public void add(EntidadBancaria bancaria) {
        getSession().save(bancaria);
    }

    @Override
    public void delete(EntidadBancaria bancaria) {
        getSession().delete(bancaria);
    }

}
