/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.NotaCreditoDAO;
import com.ar.dev.tierra.api.model.NotaCredito;
import java.io.Serializable;
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
public class NotaCreditoDAOImpl implements NotaCreditoDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NotaCredito> getAll() {
        Criteria criteria = getSession().createCriteria(NotaCredito.class);
        criteria.addOrder(Order.desc("idNotaCredito"));
        List<NotaCredito> list = criteria.list();
        return list;
    }

    @Override
    public int add(NotaCredito notaCredito) {
        Serializable idNotaCredito = getSession().save(notaCredito);
        return (int) idNotaCredito;
    }

    @Override
    public void update(NotaCredito notaCredito) {
        getSession().update(notaCredito);
    }

    @Override
    public void delete(NotaCredito notaCredito) {
        getSession().delete(notaCredito);
    }

    @Override
    public List<NotaCredito> searchByIdCliente(int idCliente) {
        Criteria criteria = getSession().createCriteria(NotaCredito.class);
        criteria.add(Restrictions.eq("idCliente", idCliente));
        criteria.addOrder(Order.desc("idNotaCredito"));
        List<NotaCredito> list = criteria.list();
        return list;
    }

    @Override
    public List<NotaCredito> getDaily() {
        Criteria criteria = getSession().createCriteria(NotaCredito.class);
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
        criteria.addOrder(Order.desc("idNotaCredito"));
        List<NotaCredito> list = criteria.list();
        return list;
    }

    @Override
    public List<NotaCredito> getMonth() {
        Criteria criteria = getSession().createCriteria(NotaCredito.class);
        Calendar calendar = Calendar.getInstance();
        Date toDate = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);
        Date fromDate = calendar.getTime();
        criteria.add(Restrictions.between("fechaCreacion", fromDate, toDate));
        criteria.addOrder(Order.desc("idNotaCredito"));
        List<NotaCredito> list = criteria.list();
        return list;
    }

    @Override
    public NotaCredito getByNumero(String numero) {
        Criteria criteria = getSession().createCriteria(NotaCredito.class);
        criteria.add(Restrictions.eq("numero", numero));
        NotaCredito notaCredito = (NotaCredito) criteria.uniqueResult();
        return notaCredito;
    }
    
    @Override
    public NotaCredito getById(int idNota) {
        Criteria criteria = getSession().createCriteria(NotaCredito.class);
        criteria.add(Restrictions.eq("idNotaCredito", idNota));
        NotaCredito notaCredito = (NotaCredito) criteria.uniqueResult();
        return notaCredito;
    }

}
