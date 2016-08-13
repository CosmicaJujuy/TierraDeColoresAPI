/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author PauloGaldo
 */
public class Metric implements Serializable {

    private BigDecimal efectivoHoy;
    private BigDecimal efectivoMensual;
    private BigDecimal totalFacturasHoy;
    private BigDecimal totalFacturasMensual;
    private BigDecimal totalReservasHoy;
    private BigDecimal totalReservasMensual;
    private int impresasHoy;
    private int impresasMensual;

    public Metric() {
    }

    /**
     * @return the efectivoHoy
     */
    public BigDecimal getEfectivoHoy() {
        return efectivoHoy;
    }

    /**
     * @param efectivoHoy the efectivoHoy to set
     */
    public void setEfectivoHoy(BigDecimal efectivoHoy) {
        this.efectivoHoy = efectivoHoy;
    }

    /**
     * @return the efectivoMensual
     */
    public BigDecimal getEfectivoMensual() {
        return efectivoMensual;
    }

    /**
     * @param efectivoMensual the efectivoMensual to set
     */
    public void setEfectivoMensual(BigDecimal efectivoMensual) {
        this.efectivoMensual = efectivoMensual;
    }

    /**
     * @return the totalFacturasHoy
     */
    public BigDecimal getTotalFacturasHoy() {
        return totalFacturasHoy;
    }

    /**
     * @param totalFacturasHoy the totalFacturasHoy to set
     */
    public void setTotalFacturasHoy(BigDecimal totalFacturasHoy) {
        this.totalFacturasHoy = totalFacturasHoy;
    }

    /**
     * @return the totalFacturasMensual
     */
    public BigDecimal getTotalFacturasMensual() {
        return totalFacturasMensual;
    }

    /**
     * @param totalFacturasMensual the totalFacturasMensual to set
     */
    public void setTotalFacturasMensual(BigDecimal totalFacturasMensual) {
        this.totalFacturasMensual = totalFacturasMensual;
    }

    /**
     * @return the totalReservasHoy
     */
    public BigDecimal getTotalReservasHoy() {
        return totalReservasHoy;
    }

    /**
     * @param totalReservasHoy the totalReservasHoy to set
     */
    public void setTotalReservasHoy(BigDecimal totalReservasHoy) {
        this.totalReservasHoy = totalReservasHoy;
    }

    /**
     * @return the totalReservasMensual
     */
    public BigDecimal getTotalReservasMensual() {
        return totalReservasMensual;
    }

    /**
     * @param totalReservasMensual the totalReservasMensual to set
     */
    public void setTotalReservasMensual(BigDecimal totalReservasMensual) {
        this.totalReservasMensual = totalReservasMensual;
    }

    /**
     * @return the impresasHoy
     */
    public int getImpresasHoy() {
        return impresasHoy;
    }

    /**
     * @param impresasHoy the impresasHoy to set
     */
    public void setImpresasHoy(int impresasHoy) {
        this.impresasHoy = impresasHoy;
    }

    /**
     * @return the impresasMensual
     */
    public int getImpresasMensual() {
        return impresasMensual;
    }

    /**
     * @param impresasMensual the impresasMensual to set
     */
    public void setImpresasMensual(int impresasMensual) {
        this.impresasMensual = impresasMensual;
    }

}
