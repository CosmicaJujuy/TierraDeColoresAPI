/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.DetalleTransferenciaDAO;
import com.ar.dev.tierra.api.model.DetalleTransferencia;
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
public class DetalleTransferenciaDAOimpl implements DetalleTransferenciaDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DetalleTransferencia> getAll() {
        Criteria criteria = getSession().createCriteria(DetalleTransferencia.class);
        criteria.addOrder(Order.desc("idDetalleTransferencia"));
        List<DetalleTransferencia> list = criteria.list();
        return list;
    }

    @Override
    public List<DetalleTransferencia> getDaily() {
        Criteria criteria = getSession().createCriteria(DetalleTransferencia.class);
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
        criteria.addOrder(Order.desc("idDetalleTransferencia"));
        List<DetalleTransferencia> list = criteria.list();
        return list;
    }

    @Override
    public List<DetalleTransferencia> getMonth() {
        Criteria criteria = getSession().createCriteria(DetalleTransferencia.class);
        Calendar calendar = Calendar.getInstance();
        Date toDate = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);
        Date fromDate = calendar.getTime();
        criteria.add(Restrictions.between("fechaCreacion", fromDate, toDate));
        criteria.addOrder(Order.desc("idDetalleTransferencia"));
        List<DetalleTransferencia> list = criteria.list();
        return list;
    }

    @Override
    public void update(DetalleTransferencia detalleTransferencia) {
        getSession().update(detalleTransferencia);
    }

    @Override
    public void add(DetalleTransferencia detalleTransferencia) {
        getSession().save(detalleTransferencia);
    }

    @Override
    public void delete(DetalleTransferencia detalleTransferencia) {
        getSession().delete(detalleTransferencia);
    }

    @Override
    public List<DetalleTransferencia> getByTransferencia(int idTransferencia) {
        Criteria criteria = getSession().createCriteria(DetalleTransferencia.class);
        Criteria transferencia = criteria.createCriteria("idTransferencia");
        transferencia.add(Restrictions.eq("idTransferencia", idTransferencia));
        List<DetalleTransferencia> list = criteria.list();
        return list;
    }

}
