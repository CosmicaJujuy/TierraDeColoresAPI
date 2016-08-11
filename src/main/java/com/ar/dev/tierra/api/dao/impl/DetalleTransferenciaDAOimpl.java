/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.DetalleTransferenciaDAO;
import com.ar.dev.tierra.api.model.DetalleTransferencia;
import com.ar.dev.tierra.api.model.StockBebelandia;
import com.ar.dev.tierra.api.model.StockLibertador;
import com.ar.dev.tierra.api.model.StockTierra;
import com.ar.dev.tierra.api.model.WrapperStock;
import java.util.ArrayList;
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

    @Override
    @SuppressWarnings("null")
    public List<WrapperStock> findByParams(String descripcion, String marca, String talla, String codigo, String categoria, int sucursal) {
        Criteria criteria = null;
        switch (sucursal) {
            case 1:
                criteria = getSession().createCriteria(StockTierra.class);
                break;
            case 2:
                criteria = getSession().createCriteria(StockBebelandia.class);
                break;
            case 3:
                criteria = getSession().createCriteria(StockLibertador.class);
                break;
        }
        criteria.add(Restrictions.eq("estado", true));
        criteria.addOrder(Order.asc("idStock"));
        Criteria producto = criteria.createCriteria("idProducto");
        if (!descripcion.equals("")) {
            producto.add(Restrictions.ilike("descripcion", descripcion, MatchMode.START));
        }
        if (!marca.equals("")) {
            Criteria marcas = producto.createCriteria("marcas");
            marcas.add(Restrictions.ilike("nombreMarca", marca, MatchMode.START));
        }
        if (!talla.equals("")) {
            producto.add(Restrictions.ilike("talla", talla, MatchMode.START));
        }
        if (!codigo.equals("")) {
            producto.add(Restrictions.ilike("codigoProducto", codigo, MatchMode.START));
        }
        if (!categoria.equals("")) {
            Criteria categorias = producto.createCriteria("categoria");
            categorias.add(Restrictions.ilike("nombreCategoria", categoria, MatchMode.START));
        }
        List<WrapperStock> list = new ArrayList<>();
        switch (sucursal) {
            case 1:
                List<StockTierra> tierraList = criteria.list();
                for (StockTierra stockTierra : tierraList) {
                    WrapperStock wrapperTierra = new WrapperStock();
                    wrapperTierra.setStockTierra(stockTierra);
                    list.add(wrapperTierra);
                }
                break;
            case 2:
                List<StockBebelandia> bebeList = criteria.list();
                for (StockBebelandia stockBebelandia : bebeList) {
                    WrapperStock wrapperBebelandia = new WrapperStock();
                    wrapperBebelandia.setStockBebelandia(stockBebelandia);
                    list.add(wrapperBebelandia);
                }
                break;
            case 3:
                List<StockLibertador> libertadorList = criteria.list();
                for (StockLibertador stockLibertador : libertadorList) {
                    WrapperStock wrapperLibertador = new WrapperStock();
                    wrapperLibertador.setStockLibertador(stockLibertador);
                    list.add(wrapperLibertador);
                }
                break;
        }
        return list;
    }

}
