package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.vo.Curso;
import model.DAO.CursoDAO;
import model.DAO.DepartamentoDAO;
import model.vo.Departamento;
import view.Fachada;

public class CadastroCursosController implements Initializable {

    Curso c = new Curso();
    DepartamentoDAO o = new DepartamentoDAO();
    List<Departamento> dptos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            atualizarDepartamentos();
        } catch (Exception ex) {
            Fachada.exibrirErro(ex.getMessage());
        }

    }

    @FXML
    void limparCampos(ActionEvent event) {
        nomeCurso.clear();
        nomeDepartamento.clear();
        selecDepartamentoCurso.setValue(null);

    }

    void abrirEditarCurso(Curso u) {
        nomeCurso.setText(u.getNome());
        selecDepartamentoCurso.setValue(u.getCurso_departamento());
        c.setId(u.getId());

    }

    @FXML
    void salvarDepartamento() throws Exception {
        Departamento d = new Departamento();
        d.setNome(nomeDepartamento.getText());
        o.salvar(d);
        nomeDepartamento.clear();
        atualizarDepartamentos();
    }

    @FXML
    void salvarCurso(ActionEvent event) throws Exception {
        c.setNome(nomeCurso.getText());
        c.setCurso_departamento(selecDepartamentoCurso.getValue());
        selecDepartamentoCurso.getValue().getCursos().add(c);
        CursoDAO co = new CursoDAO();
        co.salvar(c);
        limparCampos(null);

    }

    @FXML
    void visualizarCursos(ActionEvent event) throws IOException {
        AnchorPane visualizarCursos = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/VisualizarCursos.fxml"));
        anchorCadastrarCurso.getChildren().setAll(visualizarCursos);

    }

    private void atualizarDepartamentos() throws Exception {
        selecDepartamentoCurso.getItems().clear();

        //ObservableList<Departamento> uOL = FXCollections.observableArrayList(dptos);
        // selecDepartamentoCurso.setItems(uOL);
        selecDepartamentoCurso.getItems().addAll(o.listarTodos());
    }

    @FXML
    private TextField nomeDepartamento;

    @FXML
    private AnchorPane anchorCadastrarCurso;

    @FXML
    private TextField nomeCurso;

    @FXML
    private ComboBox<Departamento> selecDepartamentoCurso;

    @FXML
    private Button limparCamposCurso;

    @FXML
    private Button salvarCurso;

    @FXML
    private Button voltarCurso;

    @FXML
    private Button salvarDepartamento;

}
