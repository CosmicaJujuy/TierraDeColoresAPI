/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.MedioPagoDAO;
import com.ar.dev.tierra.api.model.MedioPago;
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
public class MedioPagoDAOImpl implements MedioPagoDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MedioPago> getAll() {
        Criteria criteria = getSession().createCriteria(MedioPago.class);
        criteria.add(Restrictions.eq("estado", true));
        criteria.addOrder(Order.asc("idMedioPago"));
        List<MedioPago> list = criteria.list();
        return list;
    }

}
