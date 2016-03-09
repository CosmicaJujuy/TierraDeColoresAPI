/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao.impl;

import com.ar.dev.tierra.api.dao.ChartDAO;
import com.ar.dev.tierra.api.model.DetalleFactura;
import com.ar.dev.tierra.api.model.Usuarios;
import com.ar.dev.tierra.api.model.chart.ChartVentaVendedores;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
public class ChartDAOImpl implements ChartDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ChartVentaVendedores> getVentaVendedores() {
        // primero traer lista de usuarios de rol vendedor
        // luego buscar con un bucle for con cada vendedor los detalles factura
        // entrando a la factura y luego al vendedor por medio de los criteria
        // una vez con todos los resultados, filtrarlos por dia
        // luego sumar cantidades
        // integrar resultados filtrados a la lista de la clase chart venta vendedores
        Criteria usuarios = getSession().createCriteria(Usuarios.class);
        usuarios.add(Restrictions.eq("estado", true));
        Criteria roles = usuarios.createCriteria("roles");
        roles.add(Restrictions.eq("idRol", 2));
        List<Usuarios> listaVendedores = usuarios.list();
        
        Criteria detallesFactura = getSession().createCriteria(DetalleFactura.class);
        detallesFactura.add(Restrictions.eq("estadoDetalle", true));
        Criteria facturas = detallesFactura.createCriteria("factura");
        facturas.add(Restrictions.like("estado", "CONFIRMADO"));
        List<DetalleFactura> listaDetalles = detallesFactura.list();
        int key = 0;
        for (DetalleFactura value : listaDetalles) {
            if(value.getFactura().getIdVendedor().getIdUsuario() == listaVendedores.get(key).getIdUsuario()){
                
            }
        }

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
