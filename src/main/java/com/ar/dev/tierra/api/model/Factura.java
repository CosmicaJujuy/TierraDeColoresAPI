package com.ar.dev.tierra.api.model;
// Generated 17/02/2016 00:47:04 by Hibernate Tools 4.3.1

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Factura generated by hbm2java
 */
@Entity
@Table(name = "factura")
public class Factura implements Serializable {

    private int idFactura;
    private Cliente cliente;
    private String estado;
    private Integer idVendedor;
    private Date fechaCreacion;
    private Date fechaModificacion;
    private Integer usuarioCreacion;
    private Integer usuarioModificacion;
    private BigInteger total;

    public Factura() {
    }

    public Factura(int idFactura, Cliente cliente, Date fechaCreacion) {
        this.idFactura = idFactura;
        this.cliente = cliente;
        this.fechaCreacion = fechaCreacion;
    }

    public Factura(int idFactura, Cliente cliente, String estado, Integer idVendedor, Date fechaCreacion, Date fechaModificacion, Integer usuarioCreacion, Integer usuarioModificacion) {
        this.idFactura = idFactura;
        this.cliente = cliente;
        this.estado = estado;
        this.idVendedor = idVendedor;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioCreacion = usuarioCreacion;
        this.usuarioModificacion = usuarioModificacion;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_factura", unique = true, nullable = false)
    public int getIdFactura() {
        return this.idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente")
    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Column(name = "estado", length = 50)
    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Column(name = "id_vendedor")
    public Integer getIdVendedor() {
        return this.idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false, length = 35)
    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_modificacion", length = 35)
    public Date getFechaModificacion() {
        return this.fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    @Column(name = "usuario_creacion")
    public Integer getUsuarioCreacion() {
        return this.usuarioCreacion;
    }

    public void setUsuarioCreacion(Integer usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    @Column(name = "usuario_modificacion")
    public Integer getUsuarioModificacion() {
        return this.usuarioModificacion;
    }

    public void setUsuarioModificacion(Integer usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    @Column(name = "total")
    public BigInteger getTotal() {
        return this.total;
    }

    public void setTotal(BigInteger total) {
        this.total = total;
    }
}
