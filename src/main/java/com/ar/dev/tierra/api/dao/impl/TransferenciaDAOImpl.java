/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.TransferenciaDAO;
import com.ar.dev.tierra.api.model.Transferencia;
import java.util.Calendar;
import java.util.Date;
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
public class TransferenciaDAOImpl implements TransferenciaDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Transferencia> getAll() {
        Criteria criteria = getSession().createCriteria(Transferencia.class);
        criteria.addOrder(Order.desc("idTransferencia"));
        List<Transferencia> list = criteria.list();
        return list;
    }

    @Override
    public List<Transferencia> getDaily() {
        Criteria criteria = getSession().createCriteria(Transferencia.class);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date fromDate = calendar.getTime();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date toDate = calendar.getTime();
        criteria.add(Restrictions.between("fechaCreacion", fromDate, toDate));
        criteria.addOrder(Order.desc("idTransferencia"));
        List<Transferencia> list = criteria.list();
        return list;
    }

    @Override
    public List<Transferencia> getMonth() {
        Criteria criteria = getSession().createCriteria(Transferencia.class);
        Calendar calendar = Calendar.getInstance();
        Date toDate = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);
        Date fromDate = calendar.getTime();
        criteria.add(Restrictions.between("fechaCreacion", fromDate, toDate));
        criteria.addOrder(Order.desc("idTransferencia"));
        List<Transferencia> list = criteria.list();
        return list;
    }

    @Override
    public void update(Transferencia transferencia) {
        getSession().save(transferencia);
    }

    @Override
    public void add(Transferencia transferencia) {
        getSession().update(transferencia);
    }

    @Override
    public void delete(Transferencia transferencia) {
        getSession().delete(transferencia);
    }

}
