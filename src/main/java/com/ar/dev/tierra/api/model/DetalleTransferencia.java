/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.model;

import java.io.Serializable;
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
@Table(name = "detalle_transferencia")
public class DetalleTransferencia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_transferencia", unique = true, nullable = false)
    private int idDetalleTransferencia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_transferencia", nullable = false)
    private Transferencia idTransferencia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto", nullable = false)
    private Producto producto;

    @Column(name = "id_stock", unique = true, nullable = false)
    private int idStock;

    @Column(name = "id_sucursal", unique = true, nullable = false)
    private int idSucursal;

    @Column(name = "cantidad", unique = true, nullable = false)
    private int cantidad;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false, length = 13)
    private Date fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_modificacion", length = 13)
    private Date fechaModificacion;

    @Column(name = "usuario_creacion", nullable = false)
    private int usuarioCreacion;

    @Column(name = "usuario_modificacion")
    private Integer usuarioModificacion;

    public DetalleTransferencia() {
    }

    public DetalleTransferencia(int idDetalleTransferencia, Transferencia idTransferencia, Producto producto, int idStock, int idSucursal, int cantidad, Date fechaCreacion, Date fechaModificacion, int usuarioCreacion, Integer usuarioModificacion) {
        this.idDetalleTransferencia = idDetalleTransferencia;
        this.idTransferencia = idTransferencia;
        this.producto = producto;
        this.idStock = idStock;
        this.idSucursal = idSucursal;
        this.cantidad = cantidad;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioCreacion = usuarioCreacion;
        this.usuarioModificacion = usuarioModificacion;
    }

    public DetalleTransferencia(Transferencia idTransferencia, Producto producto, int idStock, int idSucursal, int cantidad) {
        this.idTransferencia = idTransferencia;
        this.producto = producto;
        this.idStock = idStock;
        this.idSucursal = idSucursal;
        this.cantidad = cantidad;
    }

    /**
     * @return the idDetalleTransferencia
     */
    public int getIdDetalleTransferencia() {
        return idDetalleTransferencia;
    }

    /**
     * @param idDetalleTransferencia the idDetalleTransferencia to set
     */
    public void setIdDetalleTransferencia(int idDetalleTransferencia) {
        this.idDetalleTransferencia = idDetalleTransferencia;
    }

    /**
     * @return the idTransferencia
     */
    public Transferencia getIdTransferencia() {
        return idTransferencia;
    }

    /**
     * @param idTransferencia the idTransferencia to set
     */
    public void setIdTransferencia(Transferencia idTransferencia) {
        this.idTransferencia = idTransferencia;
    }

    /**
     * @return the idStock
     */
    public int getIdStock() {
        return idStock;
    }

    /**
     * @param idStock the idStock to set
     */
    public void setIdStock(int idStock) {
        this.idStock = idStock;
    }

    /**
     * @return the idSucursal
     */
    public int getIdSucursal() {
        return idSucursal;
    }

    /**
     * @param idSucursal the idSucursal to set
     */
    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
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
     * @return the producto
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

}
