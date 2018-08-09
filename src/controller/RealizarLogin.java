/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.LoginPageController.stage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import model.DAO.DAOFactory;
import model.vo.Usuario;
import view.Fachada;

/**
 *
 * @author josed
 */
public abstract class RealizarLogin {

    DAOFactory factory = DAOFactory.getInstace();
    Fachada box = new Fachada();

    public abstract boolean ValidarDados(Usuario u);

    public abstract void PrepararSistema();

    public void Logar(Usuario u) {

        try {
            if(ValidarDados(u)){
            FXMLLoader FXLogin = new FXMLLoader(getClass().getResource("/view/MainPage.fxml"));
            PrepararSistema();
            Parent root;
            root = FXLogin.load();
            Scene janela = new Scene(root);
            stage.close();
            stage.setScene(janela);
            stage.setTitle("Sistema de Gerenciamento da Biblioteca da Califonia University");
            stage.setResizable(false);

            stage.show();
            }
        } catch (IOException ex) {
            Fachada.exibrirErro(ex.getMessage());
        }

    }

}
