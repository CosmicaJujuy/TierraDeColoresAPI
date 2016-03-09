/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.model.chart;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author PauloGaldo
 */
public class CantidadFecha implements Serializable {

    private int cantidad;
    private Date fecha;

    public CantidadFecha() {
    }

    public CantidadFecha(int cantidad, Date fecha) {
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
