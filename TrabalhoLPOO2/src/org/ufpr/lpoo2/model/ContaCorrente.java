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
public class ContaCorrente extends Conta {

    private int numeroConta;
    private double limite;

    public ContaCorrente(double limite, double depositoInicial, String cpf) {
        super(depositoInicial, cpf);
        this.limite = limite;
    }

    public void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }
    
    public int getNumeroConta() {
        return numeroConta;
    }
    
    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    public boolean saca(double valor) {
        if (valor < limite) {
            super.saca(valor);
            return true;
        } else {
            return false;
        }
    }

    public void remunera() {
        this.setSaldo((this.getSaldo() * 1.01));
    }

    @Override
    public boolean deposita(double valor){
        return (super.deposita(valor));
    }
}
