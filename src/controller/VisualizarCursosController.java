package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Curso;
import model.DAO.CursoDAO;

public class VisualizarCursosController implements Initializable {

     List<Curso> cursos;
    ObservableList<Curso> uOL;
    CursoDAO DAO = new CursoDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        atualizarCurso();
    }

    @FXML
    void excluirCurso() {
         Curso u = tabelaCurso.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Excluir?");
        alert.setHeaderText("Deseja Realmente Excluir");
        alert.setContentText("Deseja apagar permanentemente o curso: " + u.getNome());
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            CursoDAO DAO = new CursoDAO();
            DAO.delete(u.getId());
            atualizarCurso();

        }

    }

    @FXML
    void abrirCadastrarCurso() throws IOException {

        AnchorPane cadastrarCurso = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/CadastroCursos.fxml"));
        AncorCurso.getChildren().setAll(cadastrarCurso);

    }

    @FXML
    void editarCurso() throws IOException {
 Curso u = tabelaCurso.getSelectionModel().getSelectedItem();
        FXMLLoader cadastroLoader = new FXMLLoader(getClass().getResource("/view/CadastroCursos.fxml"));
        AnchorPane editarCurso = (AnchorPane) cadastroLoader.load();
        this.AncorCurso.getChildren().setAll(editarCurso);
        CadastroCursosController controller = cadastroLoader.getController();
        controller.abrirEditarCurso(u);
    }

    @FXML
    void atualizarCurso() {
        
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        cursos = DAO.listarTodos();
        uOL = FXCollections.observableArrayList(cursos);
        tabelaCurso.setItems(uOL);


    }

    @FXML
    private Button editarCurso;

    @FXML
    private Button atualizarCurso;

    @FXML
    private AnchorPane AncorCurso;

    @FXML
    private Button novoCurso;

    @FXML
    private TableView<Curso> tabelaCurso;

    @FXML
    private Button excluirCurso;
    
    @FXML
    private TableColumn<Curso, String> columnNome;

}
