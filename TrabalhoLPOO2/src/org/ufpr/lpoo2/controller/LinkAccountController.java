/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ufpr.lpoo2.controller;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import org.ufpr.lpoo2.model.Cliente;
import org.ufpr.lpoo2.model.Conta;
import org.ufpr.lpoo2.model.ContaCorrente;
import org.ufpr.lpoo2.model.ContaInvestimento;
import org.ufpr.lpoo2.view.VincularCC;

/**
 *
 * @author Marcos
 */
public class LinkAccountController {

    private final VincularCC vincV;
    private final BancoFacade fac;
    private Cliente cli;
    private boolean isSet = false;
    private char tipoConta;
    private int opt = 0;

    public LinkAccountController(VincularCC vView, BancoFacade fac) {
        this.vincV = vView;
        this.fac = fac;
    }

    public void initController() {
        vincV.getSelectButton().addActionListener((ActionEvent evt) -> buscarCpf());
        vincV.getAccountType().addActionListener((ActionEvent evt) -> tipoConta());
        vincV.getLinkButton().addActionListener((ActionEvent evt) -> vincularConta());
        vincV.getBackButton().addActionListener((ActionEvent evt) -> voltar());
        initView();
    }

    public void initView() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                vincV.setVisible(true);
                vincV.setCamposInvisible();
            }
        });
    }

    private String showCli() {
        String result = "";
        if(isSet){
            result += "Cliente: " + cli.getNome() + " " + cli.getSobrenome() +"     -     ";
        }
        result += "Selecionado: " + vincV.getAccountType().getSelectedItem();
        return result;
    }
    
    private void buscarCpf() {
        isSet = false;
        vincV.setCamposInvisible();
        cli = fac.selectByCpf(vincV.getCpfField().getText());
        if(cli.getId() != 0){            
            if ( cli.getTipoConta() != 'N') {
                vincV.getSearchResult().setText("Já existe uma conta vinculada a esse CPF!");
            } else {
                isSet = true;
                vincV.getSearchResult().setText(showCli());
                vincV.setCamposVisible(opt);
            }
        } else {
            vincV.getSearchResult().setText("O CPF informado não foi encontrado na base de dados!");
        }
    }

    private void tipoConta() {
        opt = vincV.getAccountType().getSelectedIndex();
        vincV.getSearchResult().setText(showCli());
        if(isSet) {
            vincV.setCamposVisible(opt);
        }
    }
    
    private void vincularConta() {
        boolean parseError = false;
        tipoConta = 'N';
        if (opt == 0) {
            try {
                double limite = Double.parseDouble(vincV.getValueField2().getText());
                double depIni = Double.parseDouble(vincV.getValueField1().getText());

                ContaCorrente contac = new ContaCorrente(limite, depIni, cli.getCpf());
                tipoConta = 'C';
                fac.insertCorrente(contac);
            } catch (NumberFormatException e) {
                parseError = true;
                vincV.showError("Valor informado inválido!");
            }
        } else if (opt == 1) {
            try {
                double depIni = Double.parseDouble(vincV.getValueField1().getText());
                double depMin = Double.parseDouble(vincV.getValueField3().getText());
                double montanteMin = Double.parseDouble(vincV.getValueField2().getText());
                ContaInvestimento contai = new ContaInvestimento(depMin, montanteMin, depIni, cli.getCpf());
                tipoConta = 'I';
                fac.insertInvestimento(contai);
            } catch (NumberFormatException e) {
                parseError = true;
                vincV.showError("Valor informado inválido!");
            }
        }
        if (!parseError) {
            fac.updateAccountType(cli.getCpf(), tipoConta);
            vincV.showDialog("Cliente vinculado a conta com sucesso!");
            vincV.setCamposInvisible();
            isSet = false;
        }
    }

    private void voltar() {
        vincV.setVisible(false);
    }

}
