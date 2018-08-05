package controller;

import config.Data;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.DAO.DAOFactory;
import model.DAO.EmprestimoDAO;
import model.DAO.ExemplarDAO;
import model.DAO.UsuarioDAO;
import model.vo.Emprestimo;
import model.vo.Exemplar;
import model.vo.Usuario;
import model.nativeQueries.Views;
import view.Fachada;

public class EfetuarEmprestimoController implements Initializable {

    DAOFactory factory = DAOFactory.getInstace();
    ExemplarDAO edao = factory.getExemplarDAO();
    UsuarioDAO udao = factory.getUsuarioDAO();
    EmprestimoDAO dao = factory.getEmprestimoDAO();
    List<Emprestimo> emprestimos;
    Calendar data = Calendar.getInstance();
    DateFormat dfLong = DateFormat.getDateInstance(DateFormat.FULL);
    DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
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

    @FXML
    void EmAtrasoFilter() throws Exception {
        if (checkEmAtraso.isSelected()) {

            emprestimos = v.listarEmprestimosAtrasados();
            tabelaEmprestimos.setItems(FXCollections.observableArrayList(emprestimos));
        } else {
            carregarDados();
        }
    }

    @FXML
    void voltar() throws IOException {
        Stage janela = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/Reservas.fxml"));
        janela.getIcons().addAll(new Image(getClass().getResourceAsStream("/recursos/imagens/icone.png")));

        Scene scene = new Scene(root);
        janela.setScene(scene);
        janela.setResizable(false);
        janela.setTitle("Reservas Ativa");
        janela.centerOnScreen();
        janela.showAndWait();

    }

    @FXML
    void efetuarEmprestimo() throws ParseException, Exception {

        if (MainPageController.atualF != null) {
            Usuario u = boxUsuario.getValue();
            Exemplar e = boxLivro.getValue();
            if ((u != null) && (e != null)) {
                if ((e.getQuantidade_disponivel() >= 1) && (u.isStatus())) {
                    if (dao.quantidadeEmprestimoPorUsuario(u.getId()) < verificarLimiteEmprestimo(u)) {
                        if (verificarEmprestimosAbertos(u.getId(), e)) {

                            e.setQuantidade_disponivel(e.getQuantidade_disponivel() - 1);
                            Date d = new Date();
                            edao.salvar(e);
                            Emprestimo emprestimo = new Emprestimo();
                            emprestimo.setExemplar(e);
                            emprestimo.setFuncionario(MainPageController.atualF);
                            emprestimo.setUsuario(u);
                            emprestimo.setStatus(true);
                            emprestimo.setData_entrega(null);
                            emprestimo.setData_realizacao(d);
                            emprestimo.setData_estimada(dataEntrega(d, u).getTime());

                            dao.salvar(emprestimo);
                            box.exibrirMensagemOk("Operação Concluída", "Emprestimo realizado com sucesso. Data limite da entrega: " + Data.dataFull.format(emprestimo.getData_estimada()));
                        } else {
                            box.exibrirMensagemErro("Operação não realizada", "Você já possui esse livro em um Empréstimo.", "Para realizar um novo empréstimo desse livro, primeiro realize a devolução do mesmo e refaça o emprétimo.");
                        }
                    } else {
                        Fachada.exibrirMensagemErroS("Operação não realizada", "Limite de empréstimos atingido.");
                    }
                } else {
                    Fachada.exibrirMensagemErroS("Operação impossível", "Ocorreram um ou mais erros que não permitiram realizar esse empréstimo.\n Verifique se esse livro ainda esta disponível ou se a sua conta de usuário não está suspensa e tente novamente.");
                }
            } else {
                Fachada.exibrirMensagemErroS("Erro!", "Selecione pelo menos um usuário e um exemplar disponível para realizar um empréstimo.");

            }
            carregarDados();
        } else {
            Fachada.exibrirMensagemErroS("Operação impossível", "Nenhum funcionário autorizado para esta função está logado. Tente novamente mais tarde.");
        }

    }

    @FXML
    void devolverEmprestimo() throws Exception {

        Emprestimo devolver = tabelaEmprestimos.getSelectionModel().getSelectedItem();

        if ((devolver != null) && (devolver.isStatus())) {
            Exemplar exemplar = devolver.getExemplar();
            exemplar.setQuantidade_disponivel(exemplar.getQuantidade_disponivel() + 1);
            devolver.setData_entrega(data.getTime());
            devolver.setStatus(false);

            if (box.perguntar("Devolver o Livro?", "Deseja realmente registrar a devolução do Livro " + devolver.getExemplar() + ", do Usuário " + devolver.getUsuario())) {
                edao.salvar(exemplar);
                dao.salvar(devolver);
                carregarDados();
                box.exibrirMensagemOk("Devolver Livro!", "Pronto");
            }
        } else {
            box.exibrirMensagemErro("Erro", "Selecione um Empréstimo válido!");
        }
    }

    @FXML
    void carregarDados() throws Exception {
        boxLivro.getItems().clear();
        boxUsuario.getItems().clear();

        boxLivro.getItems().addAll(edao.listarTodos());
        boxUsuario.getItems().addAll(udao.listarTodos());

        colunaStatus.setCellValueFactory(new PropertyValueFactory<Emprestimo, Date>("data_entrega"));
        colunaUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaDFim.setCellValueFactory(new PropertyValueFactory<>("data_estimada"));
        colunaDataInicio.setCellValueFactory(new PropertyValueFactory<>("data_realizacao"));
        colunaLivros.setCellValueFactory(new PropertyValueFactory<>("exemplar"));

        colunaDFim.setCellFactory(new Callback<TableColumn<Emprestimo, Date>, TableCell<Emprestimo, Date>>() {
            @Override
            public TableCell<Emprestimo, Date> call(TableColumn<Emprestimo, Date> param) {
                return new TableCell<Emprestimo, Date>() {
                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!empty) {
                            setText(df.format(item));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        colunaDataInicio.setCellFactory(new Callback<TableColumn<Emprestimo, Date>, TableCell<Emprestimo, Date>>() {
            @Override
            public TableCell<Emprestimo, Date> call(TableColumn<Emprestimo, Date> param) {
                return new TableCell<Emprestimo, Date>() {
                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);

                        if (!empty) {
                            setText(Data.data.format(item));
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        colunaStatus.setCellFactory(new Callback<TableColumn<Emprestimo, Date>, TableCell<Emprestimo, Date>>() {
            @Override
            public TableCell<Emprestimo, Date> call(TableColumn<Emprestimo, Date> param) {
                return new TableCell<Emprestimo, Date>() {
                    @Override
                    protected void updateItem(Date item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            if (item != null) {
                                setText("Entregue");

                            } else {
                                setText("Aberto");

                            }
                        } else {
                            setText(null);

                        }

                    }
                };
            }
        });

        emprestimos = dao.listarTodos();

        tabelaEmprestimos.setItems(FXCollections.observableArrayList(emprestimos));
    }

    @FXML
    private ComboBox<Usuario> boxUsuario;

    @FXML
    private ComboBox<Exemplar> boxLivro;

    @FXML
    private AnchorPane AncorEfetuaEmprestimo;

    @FXML
    private Button btnEfetuar;

    @FXML
    private Button voltarLivro;

    @FXML
    private TableColumn<?, ?> colunaUsuario;

    @FXML
    private TableView<Emprestimo> tabelaEmprestimos;

    @FXML
    private Label titulo;

    @FXML
    private Button btnConcluido;

    @FXML
    private Button btnEfetuar1;

    @FXML
    private TableColumn<Emprestimo, String> colunaLivros;

    @FXML
    private TableColumn<Emprestimo, Integer> colunaId;

    @FXML
    private TableColumn<Emprestimo, Date> colunaDFim;

    @FXML
    private Button voltarLivro1;

    @FXML
    private TableColumn<Emprestimo, Date> colunaStatus;

    @FXML
    private TableColumn<Emprestimo, Date> colunaDataInicio;

    @FXML
    private CheckBox checkEmAtraso;

    private Calendar dataEntrega(Date d, Usuario u) {

        Calendar c = Calendar.getInstance();
        c.setTime(d);
        if (u.isProfessor()) {
            c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + MainPageController.rules.getDiasEmprestimoProfessor());
        } else {
            c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + MainPageController.rules.getDiasEmprestimoAluno());
        }

        return c;

    }

    private boolean verificarEmprestimosAbertos(int id, Exemplar e) throws Exception {

        List<Emprestimo> ec = dao.buscarEmprestimosPorIdUsuario(id);

        for (Emprestimo emprestimo : ec) {
            if (emprestimo.getExemplar().getId() == e.getId()) {
                if (emprestimo.isStatus()) {
                    return false;
                }
            }
        }

        return true;
    }

    private Long verificarLimiteEmprestimo(Usuario u) {
        if (u.isProfessor()) {
            return MainPageController.rules.getLimiteEmprestimoProfessor();

        } else {
            return MainPageController.rules.getLimiteEmprestimoAluno();
        }
    }
}
