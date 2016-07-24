/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.dao;

import com.ar.dev.tierra.api.model.PlanPago;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PauloGaldo
 */
@Component
@Repository
public interface PlanPagoDAO {

    public List<PlanPago> getAll();

    public void update(PlanPago planPago);

    public void add(PlanPago planPago);

    public void delete(PlanPago planPago);

    public List<PlanPago> searchPlanByTarjeta(int idTarjeta);
    
    public PlanPago searchById(int idPlanPago);

}
