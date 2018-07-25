package controller;

import java.io.IOException;
import javafx.application.Preloader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

//766x448 tamanho do ancorTable
public class StartPage extends Preloader {

   
    
      @FXML
    void abrirVisualizarUsuarios(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(VisualizarUsuariosController.class.getResource("/view/VizualizarUsuarios.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

          JOptionPane.showConfirmDialog(null, "Isso mesmo qye vc quer");

    }

    @FXML
    void abrirVisualizarLivros() {
      
    }

    @FXML
    void AbrirVisualizarCursos() {
        

    }


    @Override
    public void start(Stage stage) throws Exception {
        
               
    }

        

    public void handleBtnVisualizar() throws IOException {
        //showClientesDialog();

    }

    
    public void showClientesDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(VisualizarUsuariosController.class.getResource("/view/VizualizarUsuarios.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Vizualizar Usuarios");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o cliente no Controller.
        /*   FXMLAnchorPaneCadastrosClientesDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCliente(cliente);*/
        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();
    }
        @FXML
    private Button salvarDepartamento;

    @FXML
    private Button visualizarDepartamentos;

    @FXML
    private TextField nomeDepartamento;

    @FXML
    private AnchorPane AncorTabela;

    @FXML
    private TabPane TabelaFuncao;

    @FXML
    private Tab CursosTab;

    @FXML
    private Tab SetupTab;

    @FXML
    private Button limparDepartamento;

    @FXML
    private Tab UsuariosTab;

    @FXML
    private Tab LivrosTab;

    @FXML
    private Tab InicioTab;
  
    
    
    
     ProgressBar bar;
    
    Stage stage;

    @FXML
    private TextField cpfUsuario;

   
    @FXML
    private TextField ruaUsuario;

    @FXML
    private TextField tituloLivro;

    @FXML
    private TextField quntidadeLivro;

    @FXML
    private Button visualizarCurso;

    @FXML
    private ToggleButton professorOrAlunoUsuario;

    @FXML
    private SplitMenuButton selecDepartamentoUsuario;

    @FXML
    private Button adcionarLivro;

    @FXML
    private Button salvarLivro;

    @FXML
    private TextField nomeCurso;

    @FXML
    private ComboBox<?> selecDepartamentoCurso;

    @FXML
    private Button visualizarUsuarios;

    @FXML
    private TextField codigoOrLattesUsuario;

    @FXML
    private TextField celularUsuario;

    @FXML
    private TextField emailUsuario;

    @FXML
    private PasswordField senhaUsuario;

    @FXML
    private TextField anoLivro;

    @FXML
    private Button limparLivro;

    @FXML
    private Button SalvarUsuario;

    @FXML
    private TextField matriculaUsuario;

    @FXML
    private Button vizualizarLivros;

    @FXML
    private TextField numeroCasaUsuario;

    @FXML
    private Button limparCurso;

    @FXML
    private TextField telefoneUsuario;

    @FXML
    private ListView<?> listaAutoresLivro;

    @FXML
    private TextField bairroUsuario;

    @FXML
    private TextField autorLivro;

    @FXML
    private TextField nomeUsuario;

    @FXML
    private TextField codigoLivro;

    @FXML
    private Button salvarCurso;

    @FXML
    private TextField edicaoLivro;

    @FXML
    private TextField cidadeUsuario;

    @FXML
    private Button limparUsuario;

    @FXML
    private TextField editoraLivro;
    @FXML
    AnchorPane usuarioPane;

}
