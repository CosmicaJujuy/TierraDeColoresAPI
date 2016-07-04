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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author PauloGaldo
 */
@Entity
@Table(name = "transferencia")
public class Transferencia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_transferencia", unique = true, nullable = false)
    private int idTransferencia;

    @Column(name = "sucursal_pedido")
    private int sucursalPedido;

    @Column(name = "sucursal_respuesta")
    private int sucursalRespuesta;

    @Column(name = "estado_pedido")
    private boolean estadoPedido;

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

    public Transferencia() {
    }

    public Transferencia(int idTransferencia, int sucursalPedido, int sucursalRespuesta, boolean estadoPedido, Date fechaCreacion, Date fechaModificacion, int usuarioCreacion, Integer usuarioModificacion) {
        this.idTransferencia = idTransferencia;
        this.sucursalPedido = sucursalPedido;
        this.sucursalRespuesta = sucursalRespuesta;
        this.estadoPedido = estadoPedido;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioCreacion = usuarioCreacion;
        this.usuarioModificacion = usuarioModificacion;
    }

    /**
     * @return the idTransferencia
     */
    public int getIdTransferencia() {
        return idTransferencia;
    }

    /**
     * @param idTransferencia the idTransferencia to set
     */
    public void setIdTransferencia(int idTransferencia) {
        this.idTransferencia = idTransferencia;
    }

    /**
     * @return the sucursalPedido
     */
    public int getSucursalPedido() {
        return sucursalPedido;
    }

    /**
     * @param sucursalPedido the sucursalPedido to set
     */
    public void setSucursalPedido(int sucursalPedido) {
        this.sucursalPedido = sucursalPedido;
    }

    /**
     * @return the sucursalRespuesta
     */
    public int getSucursalRespuesta() {
        return sucursalRespuesta;
    }

    /**
     * @param sucursalRespuesta the sucursalRespuesta to set
     */
    public void setSucursalRespuesta(int sucursalRespuesta) {
        this.sucursalRespuesta = sucursalRespuesta;
    }

    /**
     * @return the estadoPedido
     */
    public boolean isEstadoPedido() {
        return estadoPedido;
    }

    /**
     * @param estadoPedido the estadoPedido to set
     */
    public void setEstadoPedido(boolean estadoPedido) {
        this.estadoPedido = estadoPedido;
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

}
