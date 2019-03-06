/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ufpr.lpoo2.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.ufpr.lpoo2.model.Conta;
import org.ufpr.lpoo2.model.ContaCorrente;
import org.ufpr.lpoo2.model.ContaInvestimento;

/**
 *
 * @author Marcos
 */
public class ContaDAO {
    
    private final String SELECT_CORRENTE = "SELECT (numero_conta, saldo, limite) FROM conta WHERE cpf = ?";
    private final String SELECT_INVESTIMENTO = "SELECT (saldo, deposito_minimo, montante_minimo) FROM conta WHERE cpf = ?";
    private final String UPDATE_SALDO = "UPDATE conta SET saldo = ? WHERE cpf = ?";
    private final String INSERT_CORRENTE = "INSERT INTO conta (cpf, saldo, limite) VALUES (?, ?, ?);";
    private final String INSERT_INVESTIMENTO = "INSERT INTO conta (cpf, saldo, deposito_minimo, montante_minimo) VALUES (?, ?, ?, ?);";

    Connection con = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    public ContaCorrente buscaContaC(String cpf) {
        ContaCorrente c = null;
        try {
            con = new ConnectionFactory().getConnection();
            stmt = con.prepareStatement(SELECT_CORRENTE);
            stmt.setString(1, cpf);
            rs = stmt.executeQuery();
            if (rs.next()) {
                int num = rs.getInt(1);
                double saldo = rs.getDouble(2);
                double limite = rs.getDouble(3);
                c = new ContaCorrente(saldo, limite, cpf);
                c.setNumeroConta(num);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
            }
        }
        return c;
    }
    
    public ContaInvestimento buscaContaI(String cpf) {
        ContaInvestimento i = null;
        try {
            con = new ConnectionFactory().getConnection();
            stmt = con.prepareStatement(SELECT_INVESTIMENTO);
            stmt.setString(1, cpf);
            rs = stmt.executeQuery();
            if (rs.next()) {
                double saldo = rs.getDouble(1);
                double depMin = rs.getDouble(2);
                double monMin = rs.getDouble(3);
                i = new ContaInvestimento(saldo, depMin, monMin, cpf);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
            }
        }
        return i;
    }
    
    public void alteraSaldo(double saldo, String cpf) {
        try {
            con = new ConnectionFactory().getConnection();
            stmt = con.prepareStatement(UPDATE_SALDO);
            stmt.setDouble(1, saldo);
            stmt.setString(2, cpf);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
            }
        }
    }
    
    public void insertCorrente(ContaCorrente conta) {
        try {
            con = new ConnectionFactory().getConnection();
            stmt = con.prepareStatement(INSERT_CORRENTE);
            stmt.setString(1, conta.getCpf());
            stmt.setDouble(2, conta.getSaldo());
            stmt.setDouble(3, conta.getLimite());
            stmt.execute();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }
    }
    
    public void insertInvestimento(ContaInvestimento conta) {
        try {
            con = new ConnectionFactory().getConnection();
            stmt = con.prepareStatement(INSERT_INVESTIMENTO);
            stmt.setString(1, conta.getCpf());
            stmt.setDouble(2, conta.getSaldo());
            stmt.setDouble(3, conta.getDepositoMinimo());
            stmt.setDouble(4, conta.getMontanteMinimo());
            stmt.execute();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                con.close();
            } catch (Exception e) {
            }
        }
    }
}
