/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ufpr.lpoo2.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ufpr.lpoo2.model.Cliente;
import org.ufpr.lpoo2.model.Conta;
import org.ufpr.lpoo2.model.ContaInvestimento;
import org.ufpr.lpoo2.model.ContaCorrente;
import org.ufpr.lpoo2.model.dao.ClienteDAO;
import org.ufpr.lpoo2.model.dao.ContaDAO;

/**
 *
 * @author Marcos
 */
public class BancoFacade {

    public List<Cliente> selectAll() throws Exception {
        //Busca lista de clientes no banco de dados e retorna um List<Cliente>
        ClienteDAO dao = new ClienteDAO();
        List<Cliente> lista = new ArrayList<Cliente>();
        try {
            lista = dao.listarClientes();
        } catch (ClassNotFoundException | SQLException ex) {
            throw new Exception(ex);
        }
        return lista;
    }

    public Cliente select(int id) {
        //Busca id do cliente a ser visualizado no parametro da página
        ClienteDAO dao = new ClienteDAO();
        Cliente c = dao.buscarCliente(id);
        return c;
    }

    public Cliente selectByCpf(String cpf) {
        ClienteDAO dao = new ClienteDAO();
        Cliente c = dao.buscarPorCpf(cpf);
        return c;
    }

    public void insert(Cliente c) {
        ClienteDAO dao = new ClienteDAO();
        dao.adicionarCliente(c);
    }

    public void update(Cliente c) {
        ClienteDAO dao = new ClienteDAO();
        dao.alterarCliente(c);
    }

    public void delete(int id) throws SQLException, InstantiationException, IllegalAccessException {
        try {
            //Busca cliente no banco de dados e deleta do banco de dados
            ClienteDAO dao = new ClienteDAO();
            dao.deletarCliente(id);
        } catch (SQLException ex) {
            throw new SQLException(ex);
        }
    }
    
    public void updateAccountType(String cpf, char tipo) {
        ClienteDAO dao = new ClienteDAO();
        dao.alteraTipoConta(cpf, tipo);
    }

    public Conta getAccountByCpf(String cpf, String tipo) {
        ContaDAO dao = new ContaDAO();
        Conta c;
        if ("C".equals(tipo)) {
            c = dao.buscaContaC(cpf);
        } else {
            c = dao.buscaContaI(cpf);
        }
        return c;
    }
    
    public void updateBalance(double newSaldo, String cpf) {
        ContaDAO dao = new ContaDAO();
        dao.alteraSaldo(newSaldo, cpf);
    }
    
    public void insertCorrente(ContaCorrente conta){
        ContaDAO dao = new ContaDAO();
        dao.insertCorrente(conta);
    }
    
    public void insertInvestimento(ContaInvestimento conta){
        ContaDAO dao = new ContaDAO();
        dao.insertInvestimento(conta);
    }
}
