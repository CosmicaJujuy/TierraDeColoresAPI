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
@Table(name = "sucursal")
public class Sucursal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sucursal", unique = true, nullable = false)
    private int idSucursal;

    @Column(name = "nombre_sucursal", nullable = false, length = 100)
    private String nombreSucursal;

    @Column(name = "direccion_sucursal", nullable = false, length = 100)
    private String direccionSucursal;

    @Column(name = "telefono_sucursal", nullable = false, length = 100)
    private String telefonoSucursal;

    @Column(name = "cuit_sucursal", nullable = false, length = 100)
    private String cuitSucursal;

    @Column(name = "usuario_creacion", nullable = false)
    private int usuarioCreacion;

    @Column(name = "usuario_modificacion")
    private Integer usuarioModificacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_creacion", nullable = false, length = 13)
    private Date fechaCreacion;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_modificacion", length = 13)
    private Date fechaModificacion;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    public Sucursal() {
    }

    public Sucursal(int idSucursal, String nombreSucursal, String direccionSucursal, String telefonoSucursal, String cuitSucursal, int usuarioCreacion, Integer usuarioModificacion, Date fechaCreacion, Date fechaModificacion, boolean estado) {
        this.idSucursal = idSucursal;
        this.nombreSucursal = nombreSucursal;
        this.direccionSucursal = direccionSucursal;
        this.telefonoSucursal = telefonoSucursal;
        this.cuitSucursal = cuitSucursal;
        this.usuarioCreacion = usuarioCreacion;
        this.usuarioModificacion = usuarioModificacion;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.estado = estado;
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
     * @return the nombreSucursal
     */
    public String getNombreSucursal() {
        return nombreSucursal;
    }

    /**
     * @param nombreSucursal the nombreSucursal to set
     */
    public void setNombreSucursal(String nombreSucursal) {
        this.nombreSucursal = nombreSucursal;
    }

    /**
     * @return the direccionSucursal
     */
    public String getDireccionSucursal() {
        return direccionSucursal;
    }

    /**
     * @param direccionSucursal the direccionSucursal to set
     */
    public void setDireccionSucursal(String direccionSucursal) {
        this.direccionSucursal = direccionSucursal;
    }

    /**
     * @return the telefonoSucursal
     */
    public String getTelefonoSucursal() {
        return telefonoSucursal;
    }

    /**
     * @param telefonoSucursal the telefonoSucursal to set
     */
    public void setTelefonoSucursal(String telefonoSucursal) {
        this.telefonoSucursal = telefonoSucursal;
    }

    /**
     * @return the cuitSucursal
     */
    public String getCuitSucursal() {
        return cuitSucursal;
    }

    /**
     * @param cuitSucursal the cuitSucursal to set
     */
    public void setCuitSucursal(String cuitSucursal) {
        this.cuitSucursal = cuitSucursal;
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
    
    
}
