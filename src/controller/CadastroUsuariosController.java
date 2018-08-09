package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import model.DAO.AlunoDAO;
import model.DAO.DAOFactory;
import model.DAO.DepartamentoDAO;
import model.DAO.ProfessorDAO;
import model.vo.Aluno;
import model.vo.Departamento;
import model.vo.Endereco;
import model.vo.Professor;
import model.vo.Usuario;
import view.Fachada;

public class CadastroUsuariosController implements Initializable {
    
    private int id = 0;
    boolean isAtivo = true;
    DAOFactory factory = DAOFactory.getInstace();
    
    Fachada box = new Fachada();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            DepartamentoDAO o = factory.getDepartamentoDAO();
            List<Departamento> dptos = o.listarTodos();
            ObservableList<Departamento> uOL = FXCollections.observableArrayList(dptos);
            selecDepartamentoUsuario.getItems().addAll(dptos);
            nomeUsuario.requestFocus();
        } catch (Exception ex) {
            Fachada.exibrirErro(ex.getMessage());
        }
        
    }
    
    public void abrirVisualizarUsuarios() throws Exception {
        AnchorPane visualizarUs = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/VisualizarUsuarios.fxml"));
        CadastrarUsuarioPane.getChildren().setAll(visualizarUs);
        
    }
    
    @FXML
    public void limparCampos() {
        matriculaUsuario.setText("");
        numeroCasaUsuario.setText("");
        telefoneUsuario.setText("");
        cpfUsuario.setText("");
        bairroUsuario.setText("");
        ruaUsuario.setText("");
        nomeUsuario.setText("");
        celularUsuario.setText("");
        senhaUsuario.setText("");
        emailUsuario.setText("");
        cidadeUsuario.setText("");
        selecDepartamentoUsuario.setValue(null);
        
    }
    
    public void editarUsuario(Usuario u) {
        try {
            matriculaUsuario.setText(u.getMatricula() + "");
            numeroCasaUsuario.setText(u.getEndereco(u.getId()).getNumero() + "");
            telefoneUsuario.setText(u.getTelefone());
            cpfUsuario.setText(u.getCpf());
            bairroUsuario.setText(u.getEndereco(u.getId()).getBairro());
            ruaUsuario.setText(u.getEndereco(u.getId()).getRua());
            nomeUsuario.setText(u.getNome());
            celularUsuario.setText(u.getCelular());
            senhaUsuario.setPromptText("Insira nova Senha");
            senhaUsuario.setText(u.getSenha());
            emailUsuario.setText(u.getEmail());
            cidadeUsuario.setText(u.getEndereco(u.getId()).getCidade());
            
            isAtivo = u.isStatus();
            id = (u.getId());
        } catch (Exception ex) {
            Fachada.exibrirErro(ex.getMessage());
        }
    }
    
    @FXML
    void handleSalvarUsuario() {
        Alert alerta;
        try {
            if (professorOrAlunoUsuario.isSelected()) {
                Professor p = new Professor();
                p.setId(id);
                if (id == 0) {
                    p.setStatus(true);
                } else {
                    p.setStatus(isAtivo);
                }
                p.setCpf(cpfUsuario.getText());
                p.setCelular(celularUsuario.getText());
                p.setEmail(emailUsuario.getText());
                p.setNome(nomeUsuario.getText());
                p.setProfessor(true);
                p.setTelefone(telefoneUsuario.getText());
                if (!senhaUsuario.equals("")) {
                    p.setSenha(senhaUsuario.getText());
                }
                p.setMatricula(Integer.parseInt(matriculaUsuario.getText()));
                p.setProfessor_departamento(selecDepartamentoUsuario.getValue());
                
                Endereco e = new Endereco(ruaUsuario.getText(), Integer.parseInt(numeroCasaUsuario.getText()), bairroUsuario.getText(),
                        cidadeUsuario.getText(), "estado");
                
                p.setEndereco(e);
                ProfessorDAO daoP = factory.getProfessorDAO();
                daoP.salvar(p);
            } else {
                Aluno a = new Aluno();
                a.setId(id);
                if (id == 0) {
                    a.setStatus(true);
                } else {
                    a.setStatus(isAtivo);
                }
                a.setCpf(cpfUsuario.getText());
                a.setCelular(celularUsuario.getText());
                a.setEmail(emailUsuario.getText());
                a.setNome(nomeUsuario.getText());
                a.setProfessor(false);
                a.setTelefone(telefoneUsuario.getText());
                if (!senhaUsuario.equals("")) {
                    a.setSenha(senhaUsuario.getText());
                }
                a.setMatricula(Integer.parseInt(matriculaUsuario.getText()));
                
                Endereco e = new Endereco(ruaUsuario.getText(), Integer.parseInt(numeroCasaUsuario.getText()), bairroUsuario.getText(),
                        cidadeUsuario.getText(), "estado");
                a.setEndereco(e);
                AlunoDAO dao = factory.getAlunoDAO();
                dao.salvar(a);
                
            }
            limparCampos();
            
            box.exibrirMensagemOk("Salvo", "Usuário salvo com sucesso!");
            abrirVisualizarUsuarios();
        } catch (Exception e) {
            alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(e.getMessage());
            alerta.show();
            alerta.setTitle("Erro!");
        }
        id = 0;
        
    }
    
    @FXML
    private void ativarProfessor() {
        if (professorOrAlunoUsuario.isSelected()) {
            selecDepartamentoUsuario.setDisable(false);
        } else {
            selecDepartamentoUsuario.setDisable(true);
        }
    }
    
    @FXML
    private Button SalvarUsuario;
    
    @FXML
    private ToggleButton professorOrAlunoUsuario;
    
    @FXML
    private ComboBox<Departamento> selecDepartamentoUsuario;
    
    @FXML
    private TextField matriculaUsuario;
    
    @FXML
    private TextField numeroCasaUsuario;
    
    @FXML
    private TextField telefoneUsuario;
    
    @FXML
    private TextField cpfUsuario;
    
    @FXML
    private TextField bairroUsuario;
    
    @FXML
    private TextField ruaUsuario;
    
    @FXML
    private TextField nomeUsuario;
    
    @FXML
    private TextField celularUsuario;
    
    @FXML
    private PasswordField senhaUsuario;
    
    @FXML
    private TextField emailUsuario;
    
    @FXML
    private TextField cidadeUsuario;
    
    @FXML
    private Button visualizarUsuarios;
    
    @FXML
    private AnchorPane CadastrarUsuarioPane;
    
    @FXML
    private Button limparUsuario;
    
}
