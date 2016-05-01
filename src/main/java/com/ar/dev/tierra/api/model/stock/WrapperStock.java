/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.model.stock;

import java.io.Serializable;

/**
 *
 * @author PauloGaldo
 */
public class WrapperStock implements Serializable {

    private StockTierra stockTierra;
    private StockBebelandia stockBebelandia;
    private StockLibertador stockLibertador;

    public WrapperStock(StockTierra stockTierra, StockBebelandia stockBebelandia, StockLibertador stockLibertador) {
        this.stockTierra = stockTierra;
        this.stockBebelandia = stockBebelandia;
        this.stockLibertador = stockLibertador;
    }

    public WrapperStock() {
    }

    /**
     * @return the stockTierra
     */
    public StockTierra getStockTierra() {
        return stockTierra;
    }

    /**
     * @param stockTierra the stockTierra to set
     */
    public void setStockTierra(StockTierra stockTierra) {
        this.stockTierra = stockTierra;
    }

    /**
     * @return the stockBebelandia
     */
    public StockBebelandia getStockBebelandia() {
        return stockBebelandia;
    }

    /**
     * @param stockBebelandia the stockBebelandia to set
     */
    public void setStockBebelandia(StockBebelandia stockBebelandia) {
        this.stockBebelandia = stockBebelandia;
    }

    /**
     * @return the stockLibertador
     */
    public StockLibertador getStockLibertador() {
        return stockLibertador;
    }

    /**
     * @param stockLibertador the stockLibertador to set
     */
    public void setStockLibertador(StockLibertador stockLibertador) {
        this.stockLibertador = stockLibertador;
    }

}
