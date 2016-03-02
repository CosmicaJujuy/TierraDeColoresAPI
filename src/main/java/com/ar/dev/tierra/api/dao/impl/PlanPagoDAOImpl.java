/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.PlanPagoDAO;
import com.ar.dev.tierra.api.model.PlanPago;
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
public class PlanPagoDAOImpl implements PlanPagoDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlanPago> getAll() {
        Criteria criteria = getSession().createCriteria(PlanPago.class);
        criteria.add(Restrictions.eq("estadoPlanes", true));
        criteria.addOrder(Order.asc("idPlanesPago"));
        List<PlanPago> list = criteria.list();
        return list;
    }

    @Override
    public void update(PlanPago planPago) {
        getSession().update(planPago);
    }

    @Override
    public void add(PlanPago planPago) {
        getSession().save(planPago);
    }

    @Override
    public void delete(PlanPago planPago) {
        getSession().delete(planPago);
    }

    @Override
    public List<PlanPago> searchPlanByTarjeta(int idTarjeta) {
        Criteria criteria = getSession().createCriteria(PlanPago.class);
        criteria.add(Restrictions.eq("estadoPlanes", true));
        Criteria tarjetaPlan = criteria.createCriteria("tarjeta");
        tarjetaPlan.add(Restrictions.eq("idTarjeta", idTarjeta));
        List<PlanPago> list = criteria.list();
        return list;
    }
}
