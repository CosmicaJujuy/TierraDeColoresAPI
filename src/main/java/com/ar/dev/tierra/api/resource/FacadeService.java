/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.resource;

import com.ar.dev.tierra.api.dao.CategoriaDAO;
import com.ar.dev.tierra.api.dao.ClienteDAO;
import com.ar.dev.tierra.api.dao.DetalleFacturaDAO;
import com.ar.dev.tierra.api.dao.DetalleNotaCreditoDAO;
import com.ar.dev.tierra.api.dao.DetalleTransferenciaDAO;
import com.ar.dev.tierra.api.dao.EntidadBancariaDAO;
import com.ar.dev.tierra.api.dao.FacturaDAO;
import com.ar.dev.tierra.api.dao.FacturaProductoDAO;
import com.ar.dev.tierra.api.dao.MarcasDAO;
import com.ar.dev.tierra.api.dao.MedioPagoDAO;
import com.ar.dev.tierra.api.dao.MetodoPagoFacturaDAO;
import com.ar.dev.tierra.api.dao.NotaCreditoDAO;
import com.ar.dev.tierra.api.dao.PlanPagoDAO;
import com.ar.dev.tierra.api.dao.ProductoDAO;
import com.ar.dev.tierra.api.dao.ProveedorDAO;
import com.ar.dev.tierra.api.dao.RolesDAO;
import com.ar.dev.tierra.api.dao.SexoDAO;
import com.ar.dev.tierra.api.dao.StockDAO;
import com.ar.dev.tierra.api.dao.SucursalDAO;
import com.ar.dev.tierra.api.dao.TarjetaDAO;
import com.ar.dev.tierra.api.dao.TemporadaDAO;
import com.ar.dev.tierra.api.dao.TipoProductoDAO;
import com.ar.dev.tierra.api.dao.TransferenciaDAO;
import com.ar.dev.tierra.api.dao.UsuariosDAO;
import com.ar.dev.tierra.api.service.FacturaProductoService;
import com.ar.dev.tierra.api.service.FacturaService;
import com.ar.dev.tierra.api.service.ProductoService;
import com.ar.dev.tierra.api.service.StockService;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author PauloGaldo
 */
@Service
public class FacadeService implements Serializable {

    @Autowired
    private CategoriaDAO categoriaDAO;

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private DetalleFacturaDAO detalleFacturaDAO;

    @Autowired
    private DetalleNotaCreditoDAO detalleNotaCreditoDAO;

    @Autowired
    private DetalleTransferenciaDAO detalleTransferenciaDAO;

    @Autowired
    private EntidadBancariaDAO entidadBancariaDAO;

    @Autowired
    private FacturaDAO facturaDAO;

    @Autowired
    private FacturaProductoDAO facturaProductoDAO;

    @Autowired
    private MarcasDAO marcasDAO;

    @Autowired
    private MedioPagoDAO medioPagoDAO;

    @Autowired
    private MetodoPagoFacturaDAO metodoPagoFacturaDAO;

    @Autowired
    private NotaCreditoDAO notaCreditoDAO;

    @Autowired
    private PlanPagoDAO planPagoDAO;

    @Autowired
    private ProductoDAO productoDAO;

    @Autowired
    private ProveedorDAO proveedorDAO;

    @Autowired
    private RolesDAO rolesDAO;

    @Autowired
    private SexoDAO sexoDAO;

    @Autowired
    private StockDAO stockDAO;

    @Autowired
    private SucursalDAO sucursalDAO;

    @Autowired
    private TarjetaDAO tarjetaDAO;

    @Autowired
    private TemporadaDAO temporadaDAO;

    @Autowired
    private TipoProductoDAO tipoProductoDAO;

    @Autowired
    private TransferenciaDAO transferenciaDAO;

    @Autowired
    private UsuariosDAO usuariosDAO;

    /*REPOSITORIO*/
    @Autowired
    private FacturaService facturaService;

    @Autowired
    private FacturaProductoService facturaProductoService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private StockService stockService;

    /**
     * @return the categoriaDAO
     */
    public CategoriaDAO getCategoriaDAO() {
        return categoriaDAO;
    }

    /**
     * @param categoriaDAO the categoriaDAO to set
     */
    public void setCategoriaDAO(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    /**
     * @return the clienteDAO
     */
    public ClienteDAO getClienteDAO() {
        return clienteDAO;
    }

    /**
     * @param clienteDAO the clienteDAO to set
     */
    public void setClienteDAO(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    /**
     * @return the detalleFacturaDAO
     */
    public DetalleFacturaDAO getDetalleFacturaDAO() {
        return detalleFacturaDAO;
    }

    /**
     * @param detalleFacturaDAO the detalleFacturaDAO to set
     */
    public void setDetalleFacturaDAO(DetalleFacturaDAO detalleFacturaDAO) {
        this.detalleFacturaDAO = detalleFacturaDAO;
    }

    /**
     * @return the detalleNotaCreditoDAO
     */
    public DetalleNotaCreditoDAO getDetalleNotaCreditoDAO() {
        return detalleNotaCreditoDAO;
    }

    /**
     * @param detalleNotaCreditoDAO the detalleNotaCreditoDAO to set
     */
    public void setDetalleNotaCreditoDAO(DetalleNotaCreditoDAO detalleNotaCreditoDAO) {
        this.detalleNotaCreditoDAO = detalleNotaCreditoDAO;
    }

    /**
     * @return the detalleTransferenciaDAO
     */
    public DetalleTransferenciaDAO getDetalleTransferenciaDAO() {
        return detalleTransferenciaDAO;
    }

    /**
     * @param detalleTransferenciaDAO the detalleTransferenciaDAO to set
     */
    public void setDetalleTransferenciaDAO(DetalleTransferenciaDAO detalleTransferenciaDAO) {
        this.detalleTransferenciaDAO = detalleTransferenciaDAO;
    }

    /**
     * @return the entidadBancariaDAO
     */
    public EntidadBancariaDAO getEntidadBancariaDAO() {
        return entidadBancariaDAO;
    }

    /**
     * @param entidadBancariaDAO the entidadBancariaDAO to set
     */
    public void setEntidadBancariaDAO(EntidadBancariaDAO entidadBancariaDAO) {
        this.entidadBancariaDAO = entidadBancariaDAO;
    }

    /**
     * @return the facturaDAO
     */
    public FacturaDAO getFacturaDAO() {
        return facturaDAO;
    }

    /**
     * @param facturaDAO the facturaDAO to set
     */
    public void setFacturaDAO(FacturaDAO facturaDAO) {
        this.facturaDAO = facturaDAO;
    }

    /**
     * @return the facturaProductoDAO
     */
    public FacturaProductoDAO getFacturaProductoDAO() {
        return facturaProductoDAO;
    }

    /**
     * @param facturaProductoDAO the facturaProductoDAO to set
     */
    public void setFacturaProductoDAO(FacturaProductoDAO facturaProductoDAO) {
        this.facturaProductoDAO = facturaProductoDAO;
    }

    /**
     * @return the marcasDAO
     */
    public MarcasDAO getMarcasDAO() {
        return marcasDAO;
    }

    /**
     * @param marcasDAO the marcasDAO to set
     */
    public void setMarcasDAO(MarcasDAO marcasDAO) {
        this.marcasDAO = marcasDAO;
    }

    /**
     * @return the medioPagoDAO
     */
    public MedioPagoDAO getMedioPagoDAO() {
        return medioPagoDAO;
    }

    /**
     * @param medioPagoDAO the medioPagoDAO to set
     */
    public void setMedioPagoDAO(MedioPagoDAO medioPagoDAO) {
        this.medioPagoDAO = medioPagoDAO;
    }

    /**
     * @return the metodoPagoFacturaDAO
     */
    public MetodoPagoFacturaDAO getMetodoPagoFacturaDAO() {
        return metodoPagoFacturaDAO;
    }

    /**
     * @param metodoPagoFacturaDAO the metodoPagoFacturaDAO to set
     */
    public void setMetodoPagoFacturaDAO(MetodoPagoFacturaDAO metodoPagoFacturaDAO) {
        this.metodoPagoFacturaDAO = metodoPagoFacturaDAO;
    }

    /**
     * @return the notaCreditoDAO
     */
    public NotaCreditoDAO getNotaCreditoDAO() {
        return notaCreditoDAO;
    }

    /**
     * @param notaCreditoDAO the notaCreditoDAO to set
     */
    public void setNotaCreditoDAO(NotaCreditoDAO notaCreditoDAO) {
        this.notaCreditoDAO = notaCreditoDAO;
    }

    /**
     * @return the planPagoDAO
     */
    public PlanPagoDAO getPlanPagoDAO() {
        return planPagoDAO;
    }

    /**
     * @param planPagoDAO the planPagoDAO to set
     */
    public void setPlanPagoDAO(PlanPagoDAO planPagoDAO) {
        this.planPagoDAO = planPagoDAO;
    }

    /**
     * @return the productoDAO
     */
    public ProductoDAO getProductoDAO() {
        return productoDAO;
    }

    /**
     * @param productoDAO the productoDAO to set
     */
    public void setProductoDAO(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    /**
     * @return the proveedorDAO
     */
    public ProveedorDAO getProveedorDAO() {
        return proveedorDAO;
    }

    /**
     * @param proveedorDAO the proveedorDAO to set
     */
    public void setProveedorDAO(ProveedorDAO proveedorDAO) {
        this.proveedorDAO = proveedorDAO;
    }

    /**
     * @return the rolesDAO
     */
    public RolesDAO getRolesDAO() {
        return rolesDAO;
    }

    /**
     * @param rolesDAO the rolesDAO to set
     */
    public void setRolesDAO(RolesDAO rolesDAO) {
        this.rolesDAO = rolesDAO;
    }

    /**
     * @return the sexoDAO
     */
    public SexoDAO getSexoDAO() {
        return sexoDAO;
    }

    /**
     * @param sexoDAO the sexoDAO to set
     */
    public void setSexoDAO(SexoDAO sexoDAO) {
        this.sexoDAO = sexoDAO;
    }

    /**
     * @return the stockDAO
     */
    public StockDAO getStockDAO() {
        return stockDAO;
    }

    /**
     * @param stockDAO the stockDAO to set
     */
    public void setStockDAO(StockDAO stockDAO) {
        this.stockDAO = stockDAO;
    }

    /**
     * @return the sucursalDAO
     */
    public SucursalDAO getSucursalDAO() {
        return sucursalDAO;
    }

    /**
     * @param sucursalDAO the sucursalDAO to set
     */
    public void setSucursalDAO(SucursalDAO sucursalDAO) {
        this.sucursalDAO = sucursalDAO;
    }

    /**
     * @return the tarjetaDAO
     */
    public TarjetaDAO getTarjetaDAO() {
        return tarjetaDAO;
    }

    /**
     * @param tarjetaDAO the tarjetaDAO to set
     */
    public void setTarjetaDAO(TarjetaDAO tarjetaDAO) {
        this.tarjetaDAO = tarjetaDAO;
    }

    /**
     * @return the temporadaDAO
     */
    public TemporadaDAO getTemporadaDAO() {
        return temporadaDAO;
    }

    /**
     * @param temporadaDAO the temporadaDAO to set
     */
    public void setTemporadaDAO(TemporadaDAO temporadaDAO) {
        this.temporadaDAO = temporadaDAO;
    }

    /**
     * @return the tipoProductoDAO
     */
    public TipoProductoDAO getTipoProductoDAO() {
        return tipoProductoDAO;
    }

    /**
     * @param tipoProductoDAO the tipoProductoDAO to set
     */
    public void setTipoProductoDAO(TipoProductoDAO tipoProductoDAO) {
        this.tipoProductoDAO = tipoProductoDAO;
    }

    /**
     * @return the transferenciaDAO
     */
    public TransferenciaDAO getTransferenciaDAO() {
        return transferenciaDAO;
    }

    /**
     * @param transferenciaDAO the transferenciaDAO to set
     */
    public void setTransferenciaDAO(TransferenciaDAO transferenciaDAO) {
        this.transferenciaDAO = transferenciaDAO;
    }

    /**
     * @return the usuariosDAO
     */
    public UsuariosDAO getUsuariosDAO() {
        return usuariosDAO;
    }

    /**
     * @param usuariosDAO the usuariosDAO to set
     */
    public void setUsuariosDAO(UsuariosDAO usuariosDAO) {
        this.usuariosDAO = usuariosDAO;
    }

    /**
     * @return the facturaService
     */
    public FacturaService getFacturaService() {
        return facturaService;
    }

    /**
     * @param facturaService the facturaService to set
     */
    public void setFacturaService(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    /**
     * @return the facturaProductoService
     */
    public FacturaProductoService getFacturaProductoService() {
        return facturaProductoService;
    }

    /**
     * @param facturaProductoService the facturaProductoService to set
     */
    public void setFacturaProductoService(FacturaProductoService facturaProductoService) {
        this.facturaProductoService = facturaProductoService;
    }

    /**
     * @return the productoService
     */
    public ProductoService getProductoService() {
        return productoService;
    }

    /**
     * @param productoService the productoService to set
     */
    public void setProductoService(ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * @return the stockService
     */
    public StockService getStockService() {
        return stockService;
    }

    /**
     * @param stockService the stockService to set
     */
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

}
