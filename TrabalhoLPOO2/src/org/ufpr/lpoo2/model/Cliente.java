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
public class Cliente {
    private int idCliente; 
    private String nome;
    private String sobrenome;
    private String rg;
    private String cpf;
    private String endereco;
    private double renda;
    private char tipoConta;
    
    public Cliente() {
    }

    public void clone(Cliente cliente){
        this.nome = cliente.nome;
        this.sobrenome = cliente.sobrenome;
        this.rg = cliente.rg;
        this.cpf = cliente.cpf;
        this.endereco = cliente.endereco;
        this.renda = cliente.renda;
    }
    
    public int getId(){
        return idCliente;
    }
 
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    
    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public double getRenda() {
        return renda;
    }
    
    public void setRenda(double renda) {
        this.renda = renda;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public char getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(char tipoConta) {
        this.tipoConta = tipoConta;
    }
    
}
