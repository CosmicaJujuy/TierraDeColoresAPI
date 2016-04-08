/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ar.dev.tierra.api.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author PauloGaldo
 */
@RestController
@RequestMapping("/test")
public class TestController implements Serializable {

    @RequestMapping(value = "/ticket", method = RequestMethod.POST)
    public ResponseEntity<?> test() {
        /*URL de conecion*/
        String urlServer = "http://localhost:8081/ticket/test";
        /*String de respuesta*/
        StringBuffer response = null;
        try {
            /*Preparacion de la url a conectar*/
            URL url = new URL(urlServer);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            /*Metodo solicitado por la URL*/
            connection.setRequestMethod("POST");
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
