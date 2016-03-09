/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.model.chart;

import com.ar.dev.tierra.api.model.Usuarios;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author PauloGaldo
 */
public class ChartVentaVendedores implements Serializable {

    private Usuarios usuarios;
    private List<CantidadFecha> cantidadFecha;

    public ChartVentaVendedores() {
    }

    public ChartVentaVendedores(Usuarios usuarios, List<CantidadFecha> cantidadFecha) {
        this.usuarios = usuarios;
        this.cantidadFecha = cantidadFecha;
    }

    /**
     * @return the usuarios
     */
    public Usuarios getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the cantidadFecha
     */
    public List<CantidadFecha> getCantidadFecha() {
        return cantidadFecha;
    }

    /**
     * @param cantidadFecha the cantidadFecha to set
     */
    public void setCantidadFecha(List<CantidadFecha> cantidadFecha) {
        this.cantidadFecha = cantidadFecha;
    }

}
