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
public class Conta implements ContaI {

    private double saldo;
    private String cpf;

    public Conta(double depositoInicial, String cpf) {
        this.saldo = depositoInicial;
        this.cpf = cpf;
    }

    @Override
    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean deposita(double valor) {
        if (valor > 0) {
            this.setSaldo(this.getSaldo() + valor);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean saca(double valor) {
        if (valor > 0) {
            this.setSaldo(this.getSaldo() - valor);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Cliente getDono() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumero() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remunera() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String getCpf() {
        return this.cpf;
    }
}
