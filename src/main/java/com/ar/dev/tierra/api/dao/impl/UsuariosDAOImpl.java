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
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
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
        projList.add(Projections.property("idUsuario"), "idUsuario");
        projList.add(Projections.property("roles"), "roles");
        projList.add(Projections.property("nombre"), "nombre");
        projList.add(Projections.property("apellido"), "apellido");
        projList.add(Projections.property("dni"), "dni");
        projList.add(Projections.property("telefono"), "telefono");
        projList.add(Projections.property("email"), "email");
        projList.add(Projections.property("fechaNacimiento"), "fechaNacimiento");
        projList.add(Projections.property("domicilio"), "domicilio");
        projList.add(Projections.property("estado"), "estado");
        projList.add(Projections.property("ultimaConexion"), "ultimaConexion");
        projList.add(Projections.property("usuarioSucursal"), "usuarioSucursal");
        criteria.setProjection(Projections.distinct(projList));
        criteria.setResultTransformer(Transformers.aliasToBean(Usuarios.class));
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
        criteria.add(Restrictions.eq("estado", true));
        Usuarios us = (Usuarios) criteria.uniqueResult();
        return us;
    }
    
    @Override
    public Usuarios findUsuarioById(int idUsuario) {
        Criteria criteria = getSession().createCriteria(Usuarios.class);
        criteria.add(Restrictions.eq("idUsuario", idUsuario));
        Usuarios us = (Usuarios) criteria.uniqueResult();
        return us;
    }

}
