/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.ClienteDAO;
import com.ar.dev.tierra.api.model.Cliente;
import java.io.Serializable;
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
public class ClienteDAOImpl implements ClienteDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Cliente> getAll() {
        Criteria criteria = getSession().createCriteria(Cliente.class);
        criteria.addOrder(Order.asc("idCliente"));
        List<Cliente> list = criteria.list();
        return list;
    }

    @Override
    public void update(Cliente cliente) {
        getSession().update(cliente);
    }

    @Override
    public int add(Cliente cliente) {
        Serializable idCliente = getSession().save(cliente);
        return (int) idCliente;
    }

    @Override
    public void delete(Cliente cliente) {
        getSession().delete(cliente);
    }

    @Override
    public Cliente searchById(int idCliente) {
        Criteria criteria = getSession().createCriteria(Cliente.class);
        criteria.add(Restrictions.eq("idCliente", idCliente));
        Cliente cliente = (Cliente) criteria.uniqueResult();
        return cliente;
    }

    @Override
    public List<Cliente> searchByApellido(String nombreApellido) {
        Criteria criteria = getSession().createCriteria(Cliente.class);
        criteria.add(Restrictions.ilike("apellidoCliente", nombreApellido, MatchMode.ANYWHERE));
        List<Cliente> list = criteria.list();
        return list;
    }

}
