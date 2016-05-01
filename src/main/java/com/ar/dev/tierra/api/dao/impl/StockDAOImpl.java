/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.StockDAO;
import com.ar.dev.tierra.api.model.stock.StockBebelandia;
import com.ar.dev.tierra.api.model.stock.StockLibertador;
import com.ar.dev.tierra.api.model.stock.StockTierra;
import com.ar.dev.tierra.api.model.stock.WrapperStock;
import java.util.ArrayList;
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
public class StockDAOImpl implements StockDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<WrapperStock> getAll(int sucursal) {
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
        List<WrapperStock> list = new ArrayList<>();
        switch (sucursal) {
            case 1:
                List<StockTierra> tierraList = criteria.list();
                WrapperStock wrapperTierra = new WrapperStock();
                for (StockTierra stockTierra : tierraList) {                    
                    wrapperTierra.setStockTierra(stockTierra);
                    list.add(wrapperTierra);
                }
                break;
            case 2:
                List<StockBebelandia> bebeList = criteria.list();
                WrapperStock wrapperBebelandia = new WrapperStock();
                for (StockBebelandia stockBebelandia : bebeList) {                    
                    wrapperBebelandia.setStockBebelandia(stockBebelandia);
                    list.add(wrapperBebelandia);
                }
                break;
            case 3:
                List<StockLibertador> libertadorList = criteria.list();
                WrapperStock wrapperLibertador = new WrapperStock();
                for (StockLibertador stockLibertador : libertadorList) {
                    wrapperLibertador.setStockLibertador(stockLibertador);
                    list.add(wrapperLibertador);
                }
                break;
        }
        return list;
    }

    @Override
    public void add(WrapperStock wrapper, int sucursal) {
        switch (sucursal) {
            case 1:
                getSession().save(wrapper.getStockTierra());
                break;
            case 2:
                getSession().save(wrapper.getStockBebelandia());
                break;
            case 3:
                getSession().save(wrapper.getStockLibertador());
                break;
        }
        
    }

    @Override
    public void update(WrapperStock wrapper, int sucursal) {
        switch (sucursal) {
            case 1:
                getSession().update(wrapper.getStockTierra());
                break;
            case 2:
                getSession().update(wrapper.getStockBebelandia());
                break;
            case 3:
                getSession().update(wrapper.getStockLibertador());
                break;
        }
    }

    @Override
    public void delete(WrapperStock wrapper, int sucursal) {
        switch (sucursal) {
            case 1:
                getSession().delete(wrapper.getStockTierra());
                break;
            case 2:
                getSession().delete(wrapper.getStockBebelandia());
                break;
            case 3:
                getSession().delete(wrapper.getStockLibertador());
                break;
        }
    }   

    @Override
    public List<StockTierra> searchByFacturaStockTierra(int idFactura) {
        Criteria criteria = getSession().createCriteria(StockTierra.class);
        criteria.add(Restrictions.eq("estado", true));
        Criteria producto = criteria.createCriteria("idProducto");
        Criteria factura = producto.createCriteria("facturaProducto");
        factura.add(Restrictions.eq("idFacturaProducto", idFactura));
        List<StockTierra> list = criteria.list();
        return list;
    }

    @Override
    public List<StockBebelandia> searchByFacturaStockBebelandia(int idFactura) {
        Criteria criteria = getSession().createCriteria(StockBebelandia.class);
        criteria.add(Restrictions.eq("estado", true));
        Criteria producto = criteria.createCriteria("idProducto");
        Criteria factura = producto.createCriteria("facturaProducto");
        factura.add(Restrictions.eq("idFacturaProducto", idFactura));
        List<StockBebelandia> list = criteria.list();
        return list;
    }

    @Override
    public List<StockLibertador> searchByFacturaStockLibertador(int idFactura) {
        Criteria criteria = getSession().createCriteria(StockLibertador.class);
        criteria.add(Restrictions.eq("estado", true));
        Criteria producto = criteria.createCriteria("idProducto");
        Criteria factura = producto.createCriteria("facturaProducto");
        factura.add(Restrictions.eq("idFacturaProducto", idFactura));
        List<StockLibertador> list = criteria.list();
        return list;
    }

}
