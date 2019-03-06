package org.ufpr.lpoo2.model;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Marcos
 */
public interface ContaI {
   public boolean deposita(double valor);
   public boolean saca(double valor);
   public Cliente getDono();
   public int getNumero();
   public double getSaldo();
   public void remunera();
}
