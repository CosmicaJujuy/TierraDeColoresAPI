/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.model.stock;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.stockTierra);
        hash = 97 * hash + Objects.hashCode(this.stockBebelandia);
        hash = 97 * hash + Objects.hashCode(this.stockLibertador);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WrapperStock other = (WrapperStock) obj;
        if (!Objects.equals(this.stockTierra, other.stockTierra)) {
            return false;
        }
        if (!Objects.equals(this.stockBebelandia, other.stockBebelandia)) {
            return false;
        }
        if (!Objects.equals(this.stockLibertador, other.stockLibertador)) {
            return false;
        }
        return true;
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
