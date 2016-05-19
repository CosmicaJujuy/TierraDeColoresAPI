/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao;

import com.ar.dev.tierra.api.model.stock.StockBebelandia;
import com.ar.dev.tierra.api.model.stock.StockLibertador;
import com.ar.dev.tierra.api.model.stock.StockTierra;
import com.ar.dev.tierra.api.model.stock.WrapperStock;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PauloGaldo
 */
@Component
@Repository
public interface StockDAO {

    public List<WrapperStock> getAll(int sucursal);

    public void add(WrapperStock wrapper);

    public void update(WrapperStock wrapper);

    public void delete(WrapperStock wrapper);

    public List<StockTierra> searchByFacturaStockTierra(int idFactura);

    public List<StockBebelandia> searchByFacturaStockBebelandia(int idFactura);

    public List<StockLibertador> searchByFacturaStockLibertador(int idFactura);
    
    public List<WrapperStock> searchByBarcodeInStock(int sucursal, String barcode);
}
