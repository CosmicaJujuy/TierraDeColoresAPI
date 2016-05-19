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
public class StockDAOImpl implements StockDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings({"unchecked", "null"})
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

    @Override
    public void add(WrapperStock wrapper) {
        if (wrapper.getStockTierra() != null) {
            getSession().save(wrapper.getStockTierra());
        }
        if (wrapper.getStockBebelandia() != null) {
            getSession().save(wrapper.getStockBebelandia());
        }
        if (wrapper.getStockLibertador() != null) {
            getSession().save(wrapper.getStockLibertador());
        }
    }

    @Override
    public void update(WrapperStock wrapper) {
        if (wrapper.getStockTierra() != null) {
            getSession().update(wrapper.getStockTierra());
        }
        if (wrapper.getStockBebelandia() != null) {
            getSession().update(wrapper.getStockBebelandia());
        }
        if (wrapper.getStockLibertador() != null) {
            getSession().update(wrapper.getStockLibertador());
        }
    }

    @Override
    public void delete(WrapperStock wrapper) {
        if (wrapper.getStockTierra() != null) {
            getSession().delete(wrapper.getStockTierra());
        }
        if (wrapper.getStockBebelandia() != null) {
            getSession().delete(wrapper.getStockBebelandia());
        }
        if (wrapper.getStockLibertador() != null) {
            getSession().delete(wrapper.getStockLibertador());
        }
    }

    @Override
    public List<StockTierra> searchByFacturaStockTierra(int idFactura) {
        Criteria criteria = getSession().createCriteria(StockTierra.class);
        criteria.add(Restrictions.eq("estado", true));
        Criteria producto = criteria.createCriteria("idProducto");
        Criteria factura = producto.createCriteria("facturaProducto");
        factura.add(Restrictions.eq("idFacturaProducto", idFactura));
        criteria.addOrder(Order.asc("idStock"));
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
        criteria.addOrder(Order.asc("idStock"));
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
        criteria.addOrder(Order.asc("idStock"));
        List<StockLibertador> list = criteria.list();
        return list;
    }

    @Override
    @SuppressWarnings("null")
    public List<WrapperStock> searchByBarcodeInStock(int sucursal, String barcode) {
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
        Criteria producto = criteria.createCriteria("idProducto");
        producto.add(Restrictions.ilike("codigoProducto", barcode, MatchMode.START));
        criteria.addOrder(Order.desc(barcode));
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
