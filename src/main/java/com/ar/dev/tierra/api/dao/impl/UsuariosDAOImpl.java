/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.model.DetalleFactura;
import com.ar.dev.tierra.api.model.Usuarios;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
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
        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.property("idUsuario"));
        projList.add(Projections.property("roles"));
        projList.add(Projections.property("nombre"));
        projList.add(Projections.property("apellido"));
        projList.add(Projections.property("dni"));
        projList.add(Projections.property("telefono"));
        projList.add(Projections.property("email"));
        projList.add(Projections.property("fechaNacimiento"));
        projList.add(Projections.property("domicilio"));
        projList.add(Projections.property("provincia"));
        projList.add(Projections.property("estado"));
//        criteria.setProjection(Projections.distinct(projList));
        List<Usuarios> us = criteria.list();
        return us;
    }

    @Override
    public List<Usuarios> getVendedores() {
        Criteria criteria = getSession().createCriteria(Usuarios.class);
        criteria.add(Restrictions.eq("estado", true));
        Criteria roles = criteria.createCriteria("roles");
        roles.add(Restrictions.eq("idRol", 2));
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
