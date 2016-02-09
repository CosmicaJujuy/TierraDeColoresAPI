/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.Usuarios;
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
public class UsuariosDAOImpl implements UsuariosDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Usuarios> allUsuarios() {
        Criteria criteria = getSession().createCriteria(Usuarios.class);
        criteria.addOrder(Order.asc("idUsuario"));
        List<Usuarios> us = criteria.list();
        return us;
    }

    @Override
    public void addUsuario(Usuarios usuarios) {
        getSession().save(usuarios);
    }

    @Override
    public void updateUsuario(Usuarios usuarios) {
        getSession().update(usuarios);
    }

    @Override
    public Usuarios findUsuarioByUsername(String username) {
        Criteria criteria = getSession().createCriteria(Usuarios.class);
        criteria.add(Restrictions.like("username", username));
        Usuarios us = (Usuarios) criteria.uniqueResult();
        return us;
    }

    @Override
    public Usuarios findUsuarioByDNI(Integer dni) {
        Criteria criteria = getSession().createCriteria(Usuarios.class);
        criteria.add(Restrictions.eq("dni", dni));
        Usuarios us = (Usuarios) criteria.uniqueResult();
        return us;
    }

}
