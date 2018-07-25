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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.DAO.ExemplarDAO;
import model.Exemplar;
import view.MessageBox;

public class VisualizarLivrosController implements Initializable {

    List<Exemplar> exeplares;
    ExemplarDAO dAO = new ExemplarDAO();
    ObservableList<Exemplar> oExempplares;
    MessageBox box = new MessageBox();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarDados();
    }

    @FXML
    void excluirLivro() {
        Exemplar ex = tabelaLivro.getSelectionModel().getSelectedItem();
      
        if (box.perguntar("Excluir", "Deseja realmente excluir todos exemplares de "+ex.getTitulo()+" edição "+ex.getEdicao(), "Há "+ex.getQuantidade()+ " quantidade(s) desse livro." )) {
           ExemplarDAO DAO = new ExemplarDAO();
            DAO.delete(ex.getId());
            carregarDados();
        }
        
    }

    public void abrirCadastrarLivro() throws IOException {
        AnchorPane cadastrarLivro = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/CadastroLivros.fxml"));
        AncorLivros.getChildren().setAll(cadastrarLivro);
    }

    @FXML
    void carregarDados() {
        columQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade_disponivel"));
        columTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        columAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        columCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));


        exeplares = dAO.listarTodos();
        oExempplares = FXCollections.observableArrayList(exeplares);
        tabelaLivro.setItems(oExempplares);

    }

    @FXML
    void abrirEditarLivro() throws IOException {
         Exemplar u = tabelaLivro.getSelectionModel().getSelectedItem();
        FXMLLoader cadastroLoader = new FXMLLoader(getClass().getResource("/view/CadastroLivros.fxml"));
        AnchorPane editarLi = (AnchorPane) cadastroLoader.load();
        this.AncorLivros.getChildren().setAll(editarLi);
        CadastroLivrosController controller = cadastroLoader.getController();
        controller.editarLivro(u);
     

    }

    @FXML
    void atualizarLivro() {
        carregarDados();
    }

    @FXML
    private TableColumn<Exemplar, Integer> columQuantidade;

    @FXML
    private TableColumn<Exemplar, String> columTitulo;

    @FXML
    private TableColumn<Exemplar, Integer> columAno;

    @FXML
    private Button btnEditarLivro;

    @FXML
    private Button btnAtualizarLivro;

    @FXML
    private TableView<Exemplar> tabelaLivro;

    @FXML
    private Button btnExcluirLivro;

    @FXML
    private Button btnNovoLivro;

    @FXML
    private AnchorPane AncorLivros;
    
      @FXML
    private TableColumn<Exemplar, Integer> columCodigo;

}
