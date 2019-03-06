/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ufpr.lpoo2.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Marcos
 */
public class BancoTableModel extends AbstractTableModel {

    private String[] colunas = new String[]{"Nome", "Sobrenome", "RG", "CPF", "Endereço", "Renda"};

    public BancoTableModel() {
    }

    private List<Cliente> lista = new ArrayList();

    @Override
    public int getRowCount() {
        return this.lista.size();
    }

    @Override
    public int getColumnCount() {
        return this.colunas.length;
    }

    @Override
    public String getColumnName(int index) {
        return this.colunas[index];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
        //if(column==0)
        //    return false;
        //return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente cli = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return cli.getNome();//if column 2 (birthday)
            case 1:
                return cli.getSobrenome();
            case 2:
                return cli.getRg();
            case 3:
                return cli.getCpf();
            case 4:
                return cli.getEndereco();
            case 5:
                return cli.getRenda();
            default:
                return null;
        }
    }

    public void adicionaContato(Cliente cli) {
        this.lista.add(cli);
        this.fireTableRowsInserted(lista.size() - 1, lista.size() - 1);//update JTable
        System.out.println(this.lista.size());
    }

    public boolean removeCliente(Cliente cli) {
        int linha = this.lista.indexOf(cli);
        boolean result = this.lista.remove(cli);
        this.fireTableRowsDeleted(linha, linha);//update JTable
        return result;
    }

    public void atualizarTabela(List<Cliente> lista) {
        this.lista = lista;
        this.fireTableDataChanged();
    }

    public void limpaTabela() {
        int indice = lista.size() - 1;
        if (indice < 0) {
            indice = 0;
        }
        this.lista = new ArrayList();
        this.fireTableRowsDeleted(0, indice);//update JTable
    }

    public Cliente getCliente(int linha) {
        return lista.get(linha);
    }

}
