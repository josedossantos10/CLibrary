package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.nativeQueries.StoredProcedure;
import view.MessageBox;

public class CriarFuncionarioController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void limparCamposF() {
        nomeUsuario.clear();
        matriculaUsuario.clear();
        senhaUsuario.clear();
        cpfUsuario.clear();

    }

    @FXML
    void handleSalvarFuncionario() {
        if((cpfUsuario.getLength()==0)||(matriculaUsuario.getLength()==0)||(nomeUsuario.getLength()==0)||(senhaUsuario.getLength()==0)){
            MessageBox.exibrirMensagemErroS("Erro!", "Todos os campos devem estar corretamente preenchidos.");
        }else{
        StoredProcedure sp = new StoredProcedure();
        sp.cadastrarFuncionario(cpfUsuario.getText(), Integer.parseInt(matriculaUsuario.getText()), nomeUsuario.getText(), senhaUsuario.getText());
        limparCamposF();
        nomeUsuario.setPromptText("Feche esta Janela e tente logar com seu CPF e Senha");
        }
    }

    @FXML
    private TextField nomeUsuario;

    @FXML
    private TextField matriculaUsuario;

    @FXML
    private AnchorPane CadastrarUsuarioPane;

    @FXML
    private TextField cpfUsuario;

    @FXML
    private PasswordField senhaUsuario;

}
