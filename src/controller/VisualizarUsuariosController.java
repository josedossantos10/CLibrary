package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import model.vo.Caixa;
import model.DAO.CaixaDAO;
import model.DAO.UsuarioDAO;
import model.vo.Usuario;
import model.nativeQueries.Views;
import view.Fachada;

public class VisualizarUsuariosController implements Initializable {

    List<Usuario> usuarios;
    ObservableList<Usuario> uOL;
    UsuarioDAO DAO = new UsuarioDAO();
    Fachada box = new Fachada();
    Views v = new Views();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            carregarDados();
        } catch (Exception ex) {
            Fachada.exibrirErro(ex.getMessage());
        }
    }

    public void carregarDados() throws Exception {
        checkSuspendidos.setSelected(false);
        stringBusca.setDisable(false);
        btnPesquisar.setDisable(false);
        btnX.setDisable(false);

        columnCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        columnNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        columnDebito.setCellValueFactory(new PropertyValueFactory<>("debitos"));

        columnRuaNun.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("rua"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        columnStatus.setCellFactory(new Callback<TableColumn<Usuario, Boolean>, TableCell<Usuario, Boolean>>() {
            @Override
            public TableCell<Usuario, Boolean> call(TableColumn<Usuario, Boolean> param) {
                return new TableCell<Usuario, Boolean>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            if (item) {
                                setText("Ativo");
                            } else {
                                setText("Suspenso");
                            }
                        } else {
                            setText(null);

                        }

                    }
                };
            }
        });
        columnTipo.setCellValueFactory(new PropertyValueFactory<>("professor"));
        columnTipo.setCellFactory(new Callback<TableColumn<Usuario, Boolean>, TableCell<Usuario, Boolean>>() {
            @Override
            public TableCell<Usuario, Boolean> call(TableColumn<Usuario, Boolean> param) {
                return new TableCell<Usuario, Boolean>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            if (item) {
                                setText("Professor");
                            } else {
                                setText("Aluno");
                            }
                        } else {
                            setText(null);

                        }

                    }
                };
            }
        });

        usuarios = DAO.listarTodos();
        uOL = FXCollections.observableArrayList(usuarios);
        tabelaUsuarios.setItems(uOL);

    }

    public void abrirCadastro() {
        try {

            AnchorPane cadastroUs = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/CadastroUsuarios.fxml"));
            this.AncorUsuarios.getChildren().setAll(cadastroUs);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void excluirUsuario() throws Exception {
        Usuario u = tabelaUsuarios.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Excluir?");
        alert.setHeaderText("Deseja Realmente Excluir");
        alert.setContentText("Deseja apagar permanentemente o usuário: " + u.getNome());
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.delete(u.getId());
            carregarDados();
        }
    }

    @FXML
    void editarUsuarios() throws Exception {
        //   Usuario u = tabelaUsuarios.getSelectionModel().getSelectedItem();
        //  u.setEndereco(u.g);
        Usuario u = DAO.buscarPorCpf(tabelaUsuarios.getSelectionModel().getSelectedItem().getCpf());

        System.out.println(u.getNome() + " :::  " + u.getEndereco());
        FXMLLoader cadastroLoader = new FXMLLoader(getClass().getResource("/view/CadastroUsuarios.fxml"));
        AnchorPane editarUs = (AnchorPane) cadastroLoader.load();
        this.AncorUsuarios.getChildren().setAll(editarUs);
        CadastroUsuariosController controller = cadastroLoader.getController();
        controller.editarUsuario(u);
        // System.out.println(u.getId() + ":" + u.getNome());
    }

    @FXML
    void suspenderUsuario() throws Exception {
//        Usuario u = tabelaUsuarios.getSelectionModel().getSelectedItem();
        Usuario u = DAO.buscarPorCpf(tabelaUsuarios.getSelectionModel().getSelectedItem().getCpf());

        if (u.isStatus()) {
            if (box.perguntar("Supender Usuário?", "Deseja Realmente Suspender o usuário " + u.getNome())) {
                u.setStatus(false);
                DAO.salvar(u);
            }
        } else {
            if (box.perguntar("Ativar Usuário?", "Deseja Realmente Ativar a conta do usuário " + u.getNome())) {
                u.setStatus(true);
                DAO.salvar(u);

            }

        }
        carregarDados();

    }

    @FXML
    void atualizarUsuarios() throws Exception {
        carregarDados();

    }

    @FXML
    void pagarDebitos() throws Exception {
        Caixa c = new Caixa();
        Usuario u = DAO.buscarPorID(tabelaUsuarios.getSelectionModel().getSelectedItem().getId());
        if (u != null) {
            if (box.perguntar("Pagar débito total?", "Deseja Realmente pagar o débito do usuário " + u.getNome() + ". no valor de: R$ " + u.getDebitos())) {
                CaixaDAO aO = new CaixaDAO();
                c.setData_pagamento(new Date());
                c.setValor(u.getDebitos());
                aO.salvar(c);
                u.setDebitos(0);
                DAO.salvar(u);
            }
        } else {
            box.exibrirMensagemErro("Erro ao encontrar usuário", "Selecione um usuario válido");
        }
        carregarDados();

    }

    @FXML
    void buscarUsuarios() throws Exception {
        usuarios = DAO.busca(stringBusca.getText());
        uOL = FXCollections.observableArrayList(usuarios);
        tabelaUsuarios.setItems(uOL);

    }

    @FXML
    void buscarUsuariosAutomatico() throws Exception {
        if (stringBusca.getText().length() > 2) {
            usuarios = DAO.busca(stringBusca.getText());
            uOL = FXCollections.observableArrayList(usuarios);
            tabelaUsuarios.setItems(uOL);

        }

    }

    @FXML
    void suspendiosFilter() throws Exception {
        if (checkSuspendidos.isSelected()) {
            usuarios = v.listarUsuariosSuspendidos();
            System.out.println("\nArray Usuarios " + usuarios.size());
            uOL = FXCollections.observableArrayList(usuarios);
            System.out.println("\n Observable List Usuarios " + uOL.size());
            tabelaUsuarios.setItems(uOL);

            stringBusca.setDisable(true);
            btnPesquisar.setDisable(true);
            btnX.setDisable(true);

        } else {
            stringBusca.setDisable(false);
            btnPesquisar.setDisable(false);
            btnX.setDisable(false);

            carregarDados();
        }
    }
    @FXML
    private Button btnPesquisar;

    @FXML
    private Button btnX;
    @FXML
    private TableView<Usuario> tabelaUsuarios;

    @FXML
    private Button btnVoltar;

    @FXML
    private Button atualizarUsuarios;

    @FXML
    private Button exckuirUsuarios;

    @FXML
    private AnchorPane AncorUsuarios;

    @FXML
    private Button editarUsuarios;

    @FXML
    private TableColumn<Usuario, String> columnNome;

    @FXML
    private TableColumn<Usuario, String> columnCpf;

    @FXML
    private TableColumn<Usuario, Boolean> columnTipo;

    @FXML
    private TableColumn<Usuario, String> columnRuaNun;

    @FXML
    private TableColumn<Usuario, Boolean> columnStatus;

    @FXML
    private TextField stringBusca;

    @FXML
    private TableColumn<Usuario, Double> columnDebito;

    @FXML
    private CheckBox checkSuspendidos;
}
