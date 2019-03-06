/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ufpr.lpoo2.controller;

import java.awt.event.ActionEvent;
import org.ufpr.lpoo2.model.Cliente;
import org.ufpr.lpoo2.model.Conta;
import org.ufpr.lpoo2.view.OperacoesView;

/**
 *
 * @author Marcos
 */
public class OperationController {

    private final OperacoesView opV;
    private final BancoFacade fac;
    private boolean isSet = false;
    private Cliente cli;
    private Conta conta;

    private final String INVALID_ACCOUNT = "Informe uma conta válida!";
    private final String OP_ERROR = "Erro ao realizar operação";

    public OperationController(OperacoesView opV, BancoFacade fac) {
        this.opV = opV;
        this.fac = fac;
    }

    public void initController() {
        opV.getSelectButton().addActionListener((ActionEvent evt) -> buscarCpf());
        opV.getDepositButton().addActionListener((ActionEvent evt) -> depositar());
        opV.getPayButton().addActionListener((ActionEvent evt) -> remunerar());
        opV.getWithdrawButton().addActionListener((ActionEvent evt) -> sacar());
        opV.getBalanceButton().addActionListener((ActionEvent evt) -> verSaldo());
        initView();
        opV.getBackButton().addActionListener((ActionEvent evt) -> voltar());
    }

    public void initView() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                opV.setVisible(true);
            }
        });
    }

    private void atualizaSaldo() {
        fac.updateBalance(conta.getSaldo(), cli.getCpf());
    }

    private void messageErroOperacao() {
        opV.showDialog(OP_ERROR);
        opV.resetCampos();
    }

    private void buscarCpf() {
        isSet = false;
        opV.resetCampos();
        cli = fac.selectByCpf(opV.getCpfField().getText());
        if (cli.getId() != 0) {
            if (cli.getTipoConta() == 'N') {
                opV.getSearchResult().setText("Esse CPF não possui conta vinculada!");
            } else {
                opV.getSearchResult().setText("Nome do titular da conta: " + cli.getNome() + " "
                        + cli.getSobrenome());
                conta = fac.getAccountByCpf(cli.getCpf(), String.valueOf(cli.getTipoConta()));
                isSet = true;
            }
        } else {
            opV.getSearchResult().setText("O CPF informado não foi encontrado na base de dados!");
        }
    }

    private void depositar() {
        boolean parseError = false;
        if (!isSet) {
            opV.showDialog(INVALID_ACCOUNT);
            opV.resetCampos();
        } else if ("".equals(opV.getDepositValue().getText())) {
            opV.showDialog("Informe um valor para depositar");
        } else {
            double valor = 0;
            try {
                valor = Double.parseDouble(opV.getDepositValue().getText());
            } catch (NumberFormatException e) {
                messageErroOperacao();
                parseError = true;
            }
            if (!parseError) {
                if (!conta.deposita(valor)) {
                    messageErroOperacao();
                } else {
                    atualizaSaldo();
                    opV.showDialog("Valor depositado em conta: R$" + opV.getDepositValue().getText());
                    opV.resetCampos();
                }
            }
        }
    }

    private void remunerar() {
        if (!isSet) {
            opV.showDialog(INVALID_ACCOUNT);
            opV.resetCampos();
        } else {
            conta.remunera();
            atualizaSaldo();
            opV.showDialog("Saldo da conta acrescido da taxa de remuneração");
        }
    }

    private void sacar() {
        boolean parseError = false;
        if (!isSet) {
            opV.showDialog(INVALID_ACCOUNT);
            opV.resetCampos();
        } else if ("".equals(opV.getWithdrawValue().getText())) {
            opV.showDialog("Informe um valor para sacar");
        } else {
            double valor = 0;
            try {
                valor = Double.parseDouble(opV.getWithdrawValue().getText());
            } catch (NumberFormatException e) {
                parseError = true;
            }
            if (!parseError) {
                if (!conta.saca(valor)) {
                    messageErroOperacao();
                } else {
                    atualizaSaldo();
                    opV.showDialog("Valor sacado da conta: R$" + opV.getWithdrawValue().getText());
                    opV.resetCampos();
                }
            }
        }
    }

    private void verSaldo() {
        if (!isSet) {
            opV.showDialog(INVALID_ACCOUNT);
        } else {
            opV.showDialog("Saldo em conta: R$" + conta.getSaldo());
        }
    }

    private void voltar() {
        opV.setVisible(false);
    }
}
