/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.DetalleNotaCreditoDAO;
import com.ar.dev.tierra.api.model.DetalleFactura;
import com.ar.dev.tierra.api.model.DetalleNotaCredito;
import java.util.Calendar;
import java.util.Date;
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
public class DetalleNotaCreditoDAOImpl implements DetalleNotaCreditoDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DetalleNotaCredito> getByNotaCredito(int idNotaCredito) {
        Criteria criteria = getSession().createCriteria(DetalleNotaCredito.class);
        Criteria notaCredito = criteria.createCriteria("notaCredito");
        notaCredito.add(Restrictions.eq("idNotaCredito", idNotaCredito));
        criteria.addOrder(Order.desc("idDetalleNotaCredito"));
        List<DetalleNotaCredito> list = criteria.list();
        return list;
    }

    @Override
    public void add(DetalleNotaCredito detalleNotaCredito) {
        getSession().save(detalleNotaCredito);
    }

    @Override
    public void update(DetalleNotaCredito detalleNotaCredito) {
        getSession().update(detalleNotaCredito);
    }

    @Override
    public void delete(DetalleNotaCredito detalleNotaCredito) {
        getSession().delete(detalleNotaCredito);
    }

    @Override
    public List<DetalleFactura> getByBarcodeOnFactura(String barcode) {
        Criteria criteria = getSession().createCriteria(DetalleFactura.class);
        Calendar calendar = Calendar.getInstance();
        Date toDate = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);
        Date fromDate = calendar.getTime();
        criteria.add(Restrictions.between("fechaCreacion", fromDate, toDate));
        criteria.add(Restrictions.eq("estadoDetalle", true));
        Criteria factura = criteria.createCriteria("factura");
        factura.add(Restrictions.eq("estado", "CONFIRMADO"));
        Criteria producto = criteria.createCriteria("producto");
        producto.add(Restrictions.ilike("codigoProducto", barcode, MatchMode.START));
        List<DetalleFactura> list = criteria.list();
        return list;
    }

}
