/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author PauloGaldo
 */
public class Chart implements Serializable {

    private int value;
    private Date date;

    public Chart() {
    }

    public Chart(int value, Date date) {
        this.value = value;
        this.date = date;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the rowCount
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the rowCount to set
     */
    public void setValue(int value) {
        this.value = value;
    }

}
