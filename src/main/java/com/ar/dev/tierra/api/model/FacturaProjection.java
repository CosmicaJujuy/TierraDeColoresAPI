package com.ar.dev.tierra.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Paulo
 */
public class FacturaProjection implements Serializable {

    private Integer idFactura;
    private String estado;
    private String nombreCliente;
    private BigDecimal total;
    private Date fechaCreacion;
    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String numeracion;
    private String regalo;

    public FacturaProjection(Integer idFactura, String estado, String nombreCliente, BigDecimal total, Date fechaCreacion, Integer idUsuario, String nombre, String apellido, String numeracion, String regalo) {
        this.idFactura = idFactura;
        this.estado = estado;
        this.nombreCliente = nombreCliente;
        this.total = total;
        this.fechaCreacion = fechaCreacion;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeracion = numeracion;
        this.regalo = regalo;
    }

    /**
     * @return the idFactura
     */
    public Integer getIdFactura() {
        return idFactura;
    }

    /**
     * @param idFactura the idFactura to set
     */
    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the nombreCliente
     */
    public String getNombreCliente() {
        return nombreCliente;
    }

    /**
     * @param nombreCliente the nombreCliente to set
     */
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
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
     * @return the idUsuario
     */
    public Integer getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the apellido
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * @param apellido the apellido to set
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * @return the numeracion
     */
    public String getNumeracion() {
        return numeracion;
    }

    /**
     * @param numeracion the numeracion to set
     */
    public void setNumeracion(String numeracion) {
        this.numeracion = numeracion;
    }

    /**
     * @return the regalo
     */
    public String getRegalo() {
        return regalo;
    }

    /**
     * @param regalo the regalo to set
     */
    public void setRegalo(String regalo) {
        this.regalo = regalo;
    }

}
