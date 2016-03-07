package com.ar.dev.tierra.api.model;
// Generated 17/02/2016 00:47:04 by Hibernate Tools 4.3.1

import java.io.Serializable;
import java.math.BigInteger;
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
 * MetodoPagoFactura generated by hbm2java
 */
@Entity
@Table(name = "metodo_pago_factura")
public class MetodoPagoFactura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_metodo_pago_factura", unique = true, nullable = false)
    private int idMetodoPagoFactura;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_plan_pago")
    private PlanPago planPago;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_factura", nullable = false)
    private Factura factura;

    @Column(name = "monto_pago", nullable = false)
    private BigInteger montoPago;

    @Column(name = "comprobante")
    private String comprobante;

    @Column(name = "estado", nullable = false)
    private boolean estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false, length = 35)
    private Date fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_modificacion", length = 35)
    private Date fechaModificacion;

    @Column(name = "usuario_creacion", nullable = false)
    private int usuarioCreacion;

    @Column(name = "usuario_modificacion")
    private Integer usuarioModificacion;

    public MetodoPagoFactura() {
    }

    public MetodoPagoFactura(int idMetodoPagoFactura, PlanPago planPago, Factura factura, BigInteger montoPago, String comprobante, boolean estado, Date fechaCreacion, Date fechaModificacion, int usuarioCreacion, Integer usuarioModificacion) {
        this.idMetodoPagoFactura = idMetodoPagoFactura;
        this.planPago = planPago;
        this.factura = factura;
        this.montoPago = montoPago;
        this.comprobante = comprobante;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.fechaModificacion = fechaModificacion;
        this.usuarioCreacion = usuarioCreacion;
        this.usuarioModificacion = usuarioModificacion;
    }

    /**
     * @return the idMetodoPagoFactura
     */
    public int getIdMetodoPagoFactura() {
        return idMetodoPagoFactura;
    }

    /**
     * @param idMetodoPagoFactura the idMetodoPagoFactura to set
     */
    public void setIdMetodoPagoFactura(int idMetodoPagoFactura) {
        this.idMetodoPagoFactura = idMetodoPagoFactura;
    }

    /**
     * @return the factura
     */
    public Factura getFactura() {
        return factura;
    }

    /**
     * @param factura the factura to set
     */
    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    /**
     * @return the planPago
     */
    public PlanPago getPlanPago() {
        return planPago;
    }

    /**
     * @param planPago the planPago to set
     */
    public void setPlanPago(PlanPago planPago) {
        this.planPago = planPago;
    }

    /**
     * @return the montoPago
     */
    public BigInteger getMontoPago() {
        return montoPago;
    }

    /**
     * @param montoPago the montoPago to set
     */
    public void setMontoPago(BigInteger montoPago) {
        this.montoPago = montoPago;
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
     * @return the comprobante
     */
    public String getComprobante() {
        return comprobante;
    }

    /**
     * @param comprobante the comprobante to set
     */
    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

}
