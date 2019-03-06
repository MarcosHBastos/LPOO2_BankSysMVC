/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ufpr.lpoo2.controller;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.ufpr.lpoo2.model.BancoTableModel;
import org.ufpr.lpoo2.view.*;
import org.ufpr.lpoo2.model.*;
import org.ufpr.lpoo2.model.dao.*;

/**
 *
 * @author Marcos
 */
public class BancoController {

    private final BancoTableModel btm;
    private final ManterCliente manterV;
    private int linhaClicada = -1;
    private final BancoFacade fac;

    public BancoController(BancoTableModel btm, ManterCliente manterV, BancoFacade fac) {
        this.btm = btm;
        this.manterV = manterV;
        this.fac = fac;
    }

    public void initController() {
        manterV.getViewTable().setModel(btm);
        manterV.getListButton().addActionListener((ActionEvent evt) -> listAll());
        manterV.getViewTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                marcaContatosSelecionados(evt);
            }
        });
        manterV.getCleanButton().addActionListener((ActionEvent evt) -> limparViewTabela());
        manterV.getDeleteButton().addActionListener((ActionEvent evt) -> excluirContatos());
        manterV.getNewButton().addActionListener((ActionEvent evt) -> incluirContato());
        manterV.getUpdateButton().addActionListener((ActionEvent evt) -> atualizarContato());
        manterV.getOperationButton().addActionListener((ActionEvent evt) -> realizarOperacao());
        manterV.getLinkAccountButton().addActionListener((ActionEvent evt) -> vincularConta());
        initView();
    }

    public void initView() {
         java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                manterV.setVisible(true);
            }
        });
    }

    
    
    private boolean checkCampos() {
        Cliente ck = manterV.getCliente();
        if (ck.getNome().isEmpty() || ck.getSobrenome().isEmpty() || ck.getRg().isEmpty()
                || ck.getCpf().isEmpty() || ck.getEndereco().isEmpty() || ck.getRenda() == 0) {
            manterV.showError("Preencha todos os campos! (renda não pode ser 0 (zero))");
            return false;
        } else {
            return true;
        }
    }
    
    private void realizarOperacao() {
         try {
            OperacoesView opV = new OperacoesView();
            OperationController controller = new OperationController(opV, fac);
            controller.initController();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao iniciar a aplicação. \n" + ex.getLocalizedMessage(), "", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void vincularConta() {
        try {
            VincularCC vincV = new VincularCC();
            LinkAccountController controller = new LinkAccountController(vincV, fac);
            controller.initController();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao iniciar a aplicação. \n" + ex.getLocalizedMessage(), "", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void listAll() {
        try {
            List<Cliente> list = fac.selectAll();
            btm.atualizarTabela(list);
        } catch (Exception ex) {
            manterV.showError("Erro ao listar clientes. Ex.:" + ex);
        }
    }

    private void marcaContatosSelecionados(MouseEvent evt) {
        //Pega a linha clicada
        linhaClicada = manterV.getSelectedLine(evt);
        //Pega o contato da linha clicada
        Cliente c = btm.getCliente(linhaClicada);
        //Seta os dados nos componentes
        manterV.setCliente(c);
    }

    private void limparViewTabela() {
        btm.limpaTabela();
    }

    private void excluirContatos() {
        try {
            int[] linhasSelecionadas = manterV.getViewTable().getSelectedRows();
            List<Cliente> listaExcluir = new ArrayList();
            for (int i = 0; i < linhasSelecionadas.length; i++) {
                Cliente cli = btm.getCliente(linhasSelecionadas[i]);
                fac.delete(cli.getId());
                listaExcluir.add(cli);
            }
            listaExcluir.forEach((cliente) -> {
                btm.removeCliente(cliente);
            });

        } catch (Exception ex) {
            manterV.showError("Erro ao excluir contato. " + ex);
        }
    }

    private void incluirContato() {
        if (checkCampos()) {
            try {
                Cliente cli = manterV.getCliente();
                cli.setTipoConta('N');
                fac.insert(cli);
                btm.adicionaContato(cli);
            } catch (Exception ex) {
                manterV.showError("Erro ao incluir contato. " + ex);
            }
        }
    }

    private void atualizarContato() {
        if (checkCampos()) {
            try {
                if (linhaClicada != -1) {
                    Cliente cli = btm.getCliente(linhaClicada);
                    fac.update(cli);
                    Cliente cliView = manterV.getCliente();
                    cli.clone(cliView);
                    //Atualiza tabela
                    btm.fireTableRowsUpdated(linhaClicada, linhaClicada);
                }
            } catch (Exception ex) {
                manterV.showError("Erro ao atualizar contato. " + ex);
            }
        }
    }
    
    private void filtrarPeloTexto() {
        btm.
    }
}
