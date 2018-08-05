package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.vo.Aluno;
import model.DAO.AlunoDAO;
import model.DAO.FuncionarioDAO;
import model.DAO.ProfessorDAO;
import model.vo.Funcionario;
import model.vo.Professor;
import model.vo.Usuario;
import model.nativeQueries.StoredProcedure;
import view.Fachada;

public class LoginPageController implements Initializable {

    public static Stage stage;
    Fachada box = new Fachada();
    StoredProcedure procedure = new StoredProcedure();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //  barra.setVisible(false);
    }

    @FXML
    public void realizarLoginFuncionario() throws Exception {
        // barra.setVisible(true);
        FuncionarioDAO o = new FuncionarioDAO();

        if (0 < procedure.validarFuncionario(login.getText(), senha.getText())) {
            Funcionario f = o.buscarPorCpf(login.getText());
            carregarSistemaFuncionario(f);
            //box.exibrirMensagemErro("Erro ao logar como Funcionário", "Usuario ou senha Inválido", "Tente Novamente!");
            // } else if (f.getSenha().equals(senha.getText())) {
        } else {
            box.exibrirMensagemErro("Erro ao logar Funcionário", "Usuário ou senha incorretos!", "Nenhum Funcionário encontrado! Tente logar como Aluno/Professor.");
        }

    }

    @FXML
    void realizarLoginUsuario() throws Exception {
        AlunoDAO ao = new AlunoDAO();
        Aluno a = ao.buscarPorCpf(login.getText());
        if (!(a != null)) {
            box.exibrirMensagemErro("Erro ao logar Usuário", "Usuário ou senha incorretos!", "Nenhum Professor ou aluno encontrado! Favor entrar em contato com Administrador do sistema.");

        } else if (a.getSenha().equals(senha.getText())) {
            carregarSistemaUsuario(a);

        } else {
            ProfessorDAO pDao = new ProfessorDAO();
            Professor p = pDao.buscarPorCpf(login.getText());
            if (!(p != null)) {
                              box.exibrirMensagemErro("Erro ao logar Usuário", "Usuário ou senha incorretos!", "Nenhum Professor ou aluno encontrado! Favor entrar em contato com Administrador do sistema!");
            } else if (p.getSenha().equals(senha.getText())) {
                carregarSistemaUsuario(p);
            } else {
            box.exibrirMensagemErro("Erro ao logar como Usuário", "Senha Inválida", "Tente Novamente!");
  }

        }

    }

    @FXML
    void SairLogin() {
        stage.close();

    }

    public void carregarSistemaFuncionario(Funcionario f) {

        try {
            FXMLLoader FXLogin = new FXMLLoader();
            MainPageController.atualF = f;
            FXLogin.setLocation(getClass().getResource("/view/MainPage.fxml"));
            Parent root = FXLogin.load();
            // MainPageController LC = FXLogin.getController();

            // LC.definirUser(f);
            // Parent root = FXMLLoader.load(getClass().getResource("/view/MainPage.fxml"));
            Scene janela = new Scene(root);
            stage.close();
            stage.setScene(janela);
            stage.setTitle("Sistema de Gerenciamento da Biblioteca da Califonia University");
            stage.setResizable(false);
            // LC.definirAdm();

            stage.show();
        } catch (IOException ex) {
        }

    }

    public void carregarSistemaUsuario(Usuario u) {

        try {
            FXMLLoader FXLogin = new FXMLLoader(getClass().getResource("/view/MainPage.fxml"));
            MainPageController.atualU = u;
            MainPageController.atualF = null;

            Parent root = FXLogin.load();

            Scene janela = new Scene(root);
            stage.close();
            stage.setScene(janela);
            stage.setTitle("Sistema de Gerenciamento da Biblioteca da Califonia University");
            stage.setResizable(false);

            stage.show();

        } catch (IOException ex) {
        }

    }

    @FXML
    private ProgressBar barra;

    @FXML
    private PasswordField senha;

    @FXML
    private Button btnEntrarUsuario;

    @FXML
    private Button btnEntrarF;

    @FXML
    private Button btnsair;

    @FXML
    private TextField login;
    /*
    public void exibrirMensagemErroS(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(titulo);
        a.setHeaderText(msg);
        a.show();

    }

    public void exibrirMensagemErroS(String titulo, String msg, String adicionais) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(titulo);
        a.setHeaderText(msg);
        a.setContentText(adicionais);
        a.show();
    }

    public void exibrirMensagemAviso(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(titulo);
        a.setHeaderText(msg);
        a.show();

    }

    public void exibrirMensagemOk(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo);
        a.setHeaderText(msg);
        a.show();
    }

    public boolean perguntar(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(msg);
        alert.showAndWait();
        return alert.getResult() == ButtonType.OK;
    }

    public boolean perguntar(String titulo, String msg, String adicionais) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(msg);
        alert.setContentText(adicionais);
        alert.showAndWait();
        return alert.getResult() == ButtonType.OK;
    }
     */
}
