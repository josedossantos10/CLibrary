package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.vo.Autor;
import model.DAO.ExemplarDAO;
import model.vo.Exemplar;
import view.Fachada;

public class CadastroLivrosController implements Initializable {

    Exemplar e = new Exemplar();
    List<Autor> autores = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // listaAutoresLivro.setStyle("Insira os nomes do autores acima");

    }

    @FXML
    void salvarLivro() throws IOException, Exception {
        e.setTitulo(tituloLivro.getText());
        e.setCodigo(Integer.parseInt(codigoLivro.getText()));
        e.setEditora(editoraLivro.getText());
        e.setEdicao(Integer.parseInt(edicaoLivro.getText()));
        if (e.getId() == 0) {
            e.setQuantidade_disponivel(Integer.parseInt(quntidadeLivro.getText()));
        }else{
            e.setQuantidade_disponivel(e.getQuantidade_disponivel()+(Integer.parseInt(quntidadeLivro.getText())-e.getQuantidade()));
        if(e.getQuantidade_disponivel()<0){
            Fachada.exibrirMensagemErroS("Alerta!", "A QUANTIDADE DISPONÍVEL É UM VALOR NEGATIVO.\nPODE OCORRER ANOMALIAS NO SISTEMA ENQUANTO ESSE VALOR NÃO SE TORNAR POSITIVO.");
        
        }
        }
        e.setQuantidade(Integer.parseInt(quntidadeLivro.getText()));
        e.setAno(Integer.parseInt(anoLivro.getText()));
        e.setAutores(autores);

//        for (Autor autor : autores) {
//            System.out.println("\n" + autor.getNome());
//            List<Exemplar> exes = new ArrayList<>();
//            exes.add(e);
//            autor.setExemplares(exes);
//            //e.getAutores().add(au);
//        }
        ExemplarDAO dAO = new ExemplarDAO();
        dAO.salvar(e);
        limparCamposLivro();
        visualizarLivros();

    }

    void editarLivro(Exemplar u) {
        e.setId(u.getId());
        anoLivro.setText(u.getAno() + "");
        codigoLivro.setText(u.getCodigo() + "");
        edicaoLivro.setText(u.getEdicao() + "");
        autorLivro.setText("");
        anoLivro.setText(u.getAno() + "");
        editoraLivro.setText(u.getEditora());
        tituloLivro.setText(u.getTitulo());
        quntidadeLivro.setText(u.getQuantidade() + "");
        listaAutoresLivro.setItems(FXCollections.observableArrayList(u.getAutores()));

    }

    @FXML
    void visualizarLivros() throws IOException {
        AnchorPane visualizarLi = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/VisualizarLivros.fxml"));
        achorCadastrarLivrosTab.getChildren().setAll(visualizarLi);
    }

    @FXML
    void limparCamposLivro() {
        anoLivro.setText("");
        codigoLivro.setText("");
        edicaoLivro.setText("");
        autorLivro.setText("");
        anoLivro.setText("");
        editoraLivro.setText("");
        tituloLivro.setText("");
        quntidadeLivro.setText("");
        autores.clear();
        listaAutoresLivro.setItems(FXCollections.observableArrayList(autores));

    }

    @FXML
    void adicionarAutor(ActionEvent event) {
        autores.add(new Autor(autorLivro.getText()));
        columNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        listaAutoresLivro.setItems(FXCollections.observableArrayList(autores));

        autorLivro.clear();

    }

    @FXML
    private TableView<Autor> listaAutoresLivro;

    @FXML
    private TableColumn<Autor, String> columNome;

    @FXML
    private TextField codigoLivro;

    @FXML
    private Button salvarLivro;

    @FXML
    private Button limparLivro;

    @FXML
    private Button adicionarLivro;

    @FXML
    private TextField edicaoLivro;

    @FXML
    private TextField tituloLivro;

    @FXML
    private TextField quntidadeLivro;

    @FXML
    private Button voltarLivro;

    @FXML
    private TextField autorLivro;

    @FXML
    private TextField anoLivro;

    @FXML
    private TextField editoraLivro;

    @FXML
    private AnchorPane achorCadastrarLivrosTab;

}
