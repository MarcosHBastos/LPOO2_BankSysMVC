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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.ufpr.lpoo2.model.Cliente;

/**
 *
 * @author Marcos
 */
public class ClienteDAO {

    private final String SELECT_ALL = "SELECT * FROM cliente;";
    private final String SELECT = "SELECT * FROM cliente WHERE id_cliente = ?;";
    private final String SELECT_BY_CPF = "SELECT * FROM cliente WHERE cpf = ?";
    private final String INSERT = "INSERT INTO cliente(nome, sobrenome, rg, cpf, endereco, renda, tipo_conta) values (?,?,?,?,?,?,?);";
    private final String UPDATE = "UPDATE cliente SET nome = ?, sobrenome = ?, rg = ?, cpf = ?, endereco = ?, "
            + " renda = ? WHERE id_cliente = ?;";
    private final String UPDATE_TIPO_CONTA = "UPDATE cliente SET tipo_conta = ? WHERE cpf = ?";
    private final String DELETE = "DELETE FROM cliente WHERE id_cliente=?;";

    Connection con = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    public ClienteDAO() {
    }

    public List<Cliente> listarClientes() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        List<Cliente> lista = new ArrayList<Cliente>();
        try {
            con = new ConnectionFactory().getConnection();
            stmt = con.prepareStatement(SELECT_ALL);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt(1));
                c.setNome(rs.getString(2));
                c.setSobrenome(rs.getString(3));
                c.setRg(rs.getString(4));
                c.setCpf(rs.getString(5));
                c.setEndereco(rs.getString(6));
                c.setRenda(rs.getDouble(7));
                c.setTipoConta(rs.getString(8).charAt(0));
                lista.add(c);
            }
            rs.close();
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            con.close();
        }
    }

    public Cliente buscarCliente(int id) {
        Cliente c = new Cliente();
        try {
            con = new ConnectionFactory().getConnection();
            stmt = con.prepareStatement(SELECT);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                c.setIdCliente(rs.getInt(1));
                c.setNome(rs.getString(2));
                c.setSobrenome(rs.getString(3));
                c.setRg(rs.getString(4));
                c.setCpf(rs.getString(5));
                c.setEndereco(rs.getString(6));
                c.setRenda(rs.getDouble(7));
                c.setTipoConta(rs.getString(8).charAt(0));
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

    public Cliente buscarPorCpf(String cpf) {
        Cliente c = new Cliente();
        try {
            con = new ConnectionFactory().getConnection();
            stmt = con.prepareStatement(SELECT_BY_CPF);
            stmt.setString(1, cpf);
            rs = stmt.executeQuery();
            if (rs.next()) {
                c.setIdCliente(rs.getInt(1));
                c.setNome(rs.getString(2));
                c.setSobrenome(rs.getString(3));
                c.setRg(rs.getString(4));
                c.setCpf(rs.getString(5));
                c.setEndereco(rs.getString(6));
                c.setRenda(rs.getDouble(7));
                c.setTipoConta(rs.getString(8).charAt(0));
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

    public void adicionarCliente(Cliente cliente) {
        try {
            con = new ConnectionFactory().getConnection();
            stmt = con.prepareStatement(INSERT);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getSobrenome());
            stmt.setString(3, cliente.getRg());
            stmt.setString(4, cliente.getCpf());
            stmt.setString(5, cliente.getEndereco());
            stmt.setDouble(6, cliente.getRenda());
            stmt.setString(7, String.valueOf(cliente.getTipoConta()));
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

    public void alterarCliente(Cliente cliente) {
        try {
            con = new ConnectionFactory().getConnection();
            stmt = con.prepareStatement(UPDATE);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getSobrenome());
            stmt.setString(3, cliente.getRg());
            stmt.setString(4, cliente.getCpf());
            stmt.setString(5, cliente.getEndereco());
            stmt.setDouble(6, cliente.getRenda());
            stmt.setInt(7, cliente.getId());
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

    public void alteraTipoConta(String cpf, char tipo) {
        try {
            con = new ConnectionFactory().getConnection();
            stmt = con.prepareStatement(UPDATE_TIPO_CONTA);
            stmt.setString(1, String.valueOf(tipo));
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

    public void deletarCliente(int idCliente) throws SQLException, InstantiationException, IllegalAccessException {
        con = new ConnectionFactory().getConnection();
        stmt = con.prepareStatement(DELETE);
        try {
            stmt.setInt(1, idCliente);
            stmt.executeUpdate();
        } finally {
            stmt.close();
        }
    }

}
