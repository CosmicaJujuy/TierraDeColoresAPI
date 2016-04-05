/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author PauloGaldo
 */
@Entity
@Table(name = "factura_producto")
public class FacturaProducto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_factura_producto", unique = true, nullable = false)
    private int idFacturaProducto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    @Column(name = "monto", scale = 2, precision = 6)
    private BigDecimal monto;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_factura", length = 13)
    private Date fechaFactura;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_creacion", nullable = false, length = 13)
    private Date fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_modificacion", length = 13)
    private Date fechaModificacion;

    @Column(name = "usuario_creacion", nullable = false)
    private int usuarioCreacion;

    @Column(name = "usuario_modificacion")
    private Integer usuarioModificacion;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    @Column(name = "observaciones", length = 200)
    private String observaciones;

    @Column(name = "estado_local", nullable = false)
    private String estadoLocal;

    @Column(name = "numero_factura", length = 25, nullable = false)
    private String numeroFactura;

    public FacturaProducto() {
    }

    public FacturaProducto(int idFacturaProducto, Proveedor proveedor, BigDecimal monto, Date fechaFactura, Date fechaCreacion, Date fechaModificacion, int usuarioCreacion, Integer usuarioModificacion, boolean estado, String observaciones, String estadoLocal, String numeroFactura) {
        this.idFacturaProducto = idFacturaProducto;
        this.proveedor = proveedor;
        this.monto = monto;
        this.fechaFactura = fechaFactura;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioCreacion = usuarioCreacion;
        this.usuarioModificacion = usuarioModificacion;
        this.estado = estado;
        this.observaciones = observaciones;
        this.estadoLocal = estadoLocal;
        this.numeroFactura = numeroFactura;
    }

    /**
     * @return the proveedor
     */
    public Proveedor getProveedor() {
        return proveedor;
    }

    /**
     * @param proveedor the proveedor to set
     */
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    /**
     * @return the monto
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    /**
     * @return the fechaFactura
     */
    public Date getFechaFactura() {
        return fechaFactura;
    }

    /**
     * @param fechaFactura the fechaFactura to set
     */
    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the fechaModificacion
     */
    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    /**
     * @param fechaModificacion the fechaModificacion to set
     */
    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    /**
     * @return the usuarioCreacion
     */
    public int getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     * @param usuarioCreacion the usuarioCreacion to set
     */
    public void setUsuarioCreacion(int usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     * @return the usuarioModificacion
     */
    public Integer getUsuarioModificacion() {
        return usuarioModificacion;
    }

    /**
     * @param usuarioModificacion the usuarioModificacion to set
     */
    public void setUsuarioModificacion(Integer usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    /**
     * @return the estado
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /**
     * @return the observaciones
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     * @param observaciones the observaciones to set
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * @return the estadoLocal
     */
    public String getEstadoLocal() {
        return estadoLocal;
    }

    /**
     * @param estadoLocal the estadoLocal to set
     */
    public void setEstadoLocal(String estadoLocal) {
        this.estadoLocal = estadoLocal;
    }

    /**
     * @return the numeroFactura
     */
    public String getNumeroFactura() {
        return numeroFactura;
    }

    /**
     * @param numeroFactura the numeroFactura to set
     */
    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    /**
     * @return the idFacturaProducto
     */
    public int getIdFacturaProducto() {
        return idFacturaProducto;
    }

    /**
     * @param idFacturaProducto the idFacturaProducto to set
     */
    public void setIdFacturaProducto(int idFacturaProducto) {
        this.idFacturaProducto = idFacturaProducto;
    }

}
