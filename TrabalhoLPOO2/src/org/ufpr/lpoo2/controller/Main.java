/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ufpr.lpoo2.controller;

import javax.swing.JOptionPane;
import org.ufpr.lpoo2.model.*;
import org.ufpr.lpoo2.model.dao.*;
import org.ufpr.lpoo2.view.ManterCliente;

/**
 *
 * @author Marcos
 */
public class Main {

    public static void main(String[] args) {
        try {
            BancoFacade fac = new BancoFacade();
            BancoTableModel model = new BancoTableModel();
            ManterCliente view = new ManterCliente();
            BancoController controller = new BancoController(model, view, fac);
            controller.initController();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Erro ao iniciar a aplicação. \n" + ex.getLocalizedMessage(), "", JOptionPane.ERROR_MESSAGE);
        }
    }
}
