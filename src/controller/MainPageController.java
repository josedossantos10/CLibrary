package controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import config.Rules;
import static controller.MainPageController.atualU;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.DAO.AlunoDAO;
import model.DAO.DAOFactory;
import model.DAO.EmprestimoDAO;
import model.DAO.ExemplarDAO;
import model.DAO.ProfessorDAO;
import model.DAO.ReservaDAO;
import model.vo.Emprestimo;
import model.vo.Exemplar;
import model.vo.Funcionario;
import model.vo.Reserva;
import model.vo.Usuario;
import model.nativeQueries.StoredProcedure;
import view.Fachada;

public class MainPageController implements Initializable {

    public static Rules rules = new Rules();
    DAOFactory factory = DAOFactory.getInstace();
    StoredProcedure sp = new StoredProcedure();
    EmprestimoDAO edao = factory.getEmprestimoDAO();
    ReservaDAO rdao = factory.getReservaDAO();

    public static Usuario atualU;
    public static Funcionario atualF;
    Fachada box = new Fachada();
    Calendar data = Calendar.getInstance();
    DateFormat dfLong = DateFormat.getDateInstance(DateFormat.FULL);
    DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //System.out.println(sp.validarFuncionario("1111","123"));
        //sp.cadastrarFuncionario("1111", 1, "Onze11", "111");
        lblData.setText(dfLong.format(data.getTime()));
        if (atualF != null) {
            definirAdm();
        } else if (atualU != null) {
            definirUser();
        } else {
            definirGest();
        }

    }

    @FXML
    void abrirTabInicio() throws IOException {
        AnchorPane visualizarInicio = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/ConsultarAcervo2.fxml"));
        getAncorTabInicio().getChildren().setAll(visualizarInicio);
    }

    @FXML
    void abrirTabUsuarios() throws IOException {
        AnchorPane visualizarUs = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/VisualizarUsuarios.fxml"));
        getAncorTabUsuario().getChildren().setAll(visualizarUs);
    }

    @FXML
    void abrirTabLivros() {
        try {
            AnchorPane visualizarLi = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/VisualizarLivros.fxml"));
            getAncorTabLivro().getChildren().setAll(visualizarLi);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void abrirTabCursos() throws IOException {
        AnchorPane visualizarCursos = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/VisualizarCursos.fxml"));
        getAncorTabCurso().getChildren().setAll(visualizarCursos);
        visualizarCursos.toFront();

    }

    @FXML
    void abrirTabEmprestimos() throws IOException {
        AnchorPane visualizarEmprestimos = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/EfetuarEmprestimo.fxml"));
        ancorTabEmprestimos.getChildren().setAll(visualizarEmprestimos);
        visualizarEmprestimos.toFront();

    }

    @FXML
    void atualizarTabPerfil() throws Exception {
        ProfessorDAO pdao = factory.getProfessorDAO();
        AlunoDAO adao = factory.getAlunoDAO();
        if (atualU.isProfessor()) {

            atualU = pdao.buscarPorID(atualU.getId());
        } else {
            atualU = adao.buscarPorID(atualU.getId());
        }

        if (atualU != null) {
            lblValorDebitos.setText(atualU.getDebitos() + "");
            if (atualU.isStatus()) {
                lblSituacao.setText("OK");
            } else {
                lblSituacao.setText("Suspenso");
            }
        } else {
            desconectar();
        }
    }

    @FXML
    void abrirTabPerfil() throws IOException {
        AnchorPane meuPerfil = (AnchorPane) FXMLLoader.load(getClass().getResource("/view/PerfilUsuario.fxml"));
        ancorTabPerfil.getChildren().setAll(meuPerfil);
        meuPerfil.toFront();

    }

    @FXML
    void abrirTabPedidos() throws Exception {

        colunaDataReserva.setCellValueFactory(new PropertyValueFactory<>("data_realizacao"));
        colunaIdReserva.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaInicioReserva.setCellValueFactory(new PropertyValueFactory<>("data_emprestimo"));
        colunaLivroReserva.setCellValueFactory(new PropertyValueFactory<>("exemplar"));
        //Configuração da data na tableview
        colunaStatusReserva.setCellValueFactory(new PropertyValueFactory<>("status"));
        colunaStatusReserva.setCellFactory((TableColumn<Reserva, Boolean> param) -> new TableCell<Reserva, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                    if (item) {
                        setText("Ativo");
                    } else {
                        setText("Expirado");
                    }
                } else {
                    setText(null);

                }

            }
        });

        colunaDataReserva.setCellFactory(new Callback<TableColumn<Reserva, Date>, TableCell<Reserva, Date>>() {
            @Override
            public TableCell<Reserva, Date> call(TableColumn<Reserva, Date> param) {
                return new TableCell<Reserva, Date>() {
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

        colunaInicioReserva.setCellFactory(new Callback<TableColumn<Reserva, Date>, TableCell<Reserva, Date>>() {
            @Override
            public TableCell<Reserva, Date> call(TableColumn<Reserva, Date> param) {
                return new TableCell<Reserva, Date>() {
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

        colunaFimEmprestimo.setCellValueFactory(new PropertyValueFactory<>("data_estimada"));
        colunaIdEmprestimo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaInicioEmprestimo.setCellValueFactory(new PropertyValueFactory<>("data_realizacao"));
        colunaLivrosEmprestimo.setCellValueFactory(new PropertyValueFactory<>("exemplar"));
        colunaStatusEmprestimo.setCellValueFactory(new PropertyValueFactory<>("status"));
        colunaStatusEmprestimo.setCellFactory(new Callback<TableColumn<Emprestimo, Boolean>, TableCell<Emprestimo, Boolean>>() {
            @Override
            public TableCell<Emprestimo, Boolean> call(TableColumn<Emprestimo, Boolean> param) {
                return new TableCell<Emprestimo, Boolean>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            if (item) {
                                setText("Aberto");
                            } else {
                                setText("Entregue");
                            }
                        } else {
                            setText(null);

                        }

                    }
                };
            }
        });

        colunaInicioEmprestimo.setCellFactory(new Callback<TableColumn<Emprestimo, Date>, TableCell<Emprestimo, Date>>() {
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

        colunaFimEmprestimo.setCellFactory(new Callback<TableColumn<Emprestimo, Date>, TableCell<Emprestimo, Date>>() {
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

        carregarDados();
    }

    void carregarDados() throws Exception {

        if (atualU != null) {
            tabelaReservasUsuario.setItems(FXCollections.observableArrayList(rdao.buscarReservasPorId(atualU.getId())));
            tabelaEmprestimosUsuario.setItems(FXCollections.observableArrayList(edao.buscarEmprestimosPorIdUsuario(atualU.getId())));

        } else {
            box.exibrirMensagemErro("Desculpe", "Não foi possivel encontrar seu perfil!n Tente logar novamente!");
        }
    }

    @FXML
    void abrirTabSetup() {
        // box.exibrirFuncionalidadeEmImplamentacao();

        quantidadeEmprestimoAluno.setText(rules.getLimiteEmprestimoAluno() + "");
        quantidadeEmprestimoProfessor.setText(rules.getLimiteEmprestimoProfessor() + "");
        professorDiasEmprestimo.setText(rules.getDiasEmprestimoProfessor() + "");
        alunoDiasEmprestimo.setText(rules.getDiasEmprestimoAluno() + "");
        diasSuspenderAtraso.setText(rules.getDiasParaSuspencao() + "");
        valorMulta.setText(rules.getValorMulta() + "");

    }

    public void definirUser() {
        tabCursos.setDisable(true);
        tabSetup.setDisable(true);
        tabUsuarios.setDisable(true);
        tabEmprestimos.setDisable(true);
        btnSair.setVisible(true);
        tabLivros.setDisable(true);
        lblEmail.setText(atualU.getEmail());
        lblNome.setText(atualU.getNome());
//        new AtualizarPropriedades().start();

    }

    public void definirGest() {
//        tabCursos.setDisable(true);
//        tabLivros.setDisable(true);
//        tabSetup.setDisable(true);
//        tabUsuarios.setDisable(true);
//        tabPerfilUsuario.setDisable(true);
//        tabPedidosUsuario.setDisable(true);
//        tabEmprestimos.setDisable(true);
//        btnSair.setVisible(false);

        tabCursos.setDisable(true);
        tabLivros.setDisable(true);
        tabSetup.setDisable(true);
        tabUsuarios.setDisable(true);
        tabPerfilUsuario.setDisable(true);
        tabPedidosUsuario.setDisable(true);
        tabEmprestimos.setDisable(true);
        btnSair.setVisible(false);

        lblEmail.setText("");
        lblNome.setText("Convidado");

    }

    public void definirAdm() {
        tabCursos.setDisable(false);
        tabLivros.setDisable(false);
        tabSetup.setDisable(false);
        tabEmprestimos.setDisable(false);
        tabUsuarios.setDisable(false);
        tabPerfilUsuario.setDisable(true);
        tabPedidosUsuario.setDisable(true);
        btnSair.setVisible(true);

        lblEmail.setText(atualF.getCpf());
        lblNome.setText(atualF.getNome());

    }

    @FXML
    void abrirLogar() throws IOException {
        Stage janela = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
        janela.getIcons().addAll(new Image(getClass().getResourceAsStream("/recursos/imagens/icone.png")));

        Scene scene = new Scene(root);
        janela.setScene(scene);
        janela.setResizable(false);
        janela.setTitle("Entrar");
        LoginPageController.stage = janela;
        janela.centerOnScreen();
        janela.showAndWait();

    }

    @FXML
    void salvarConfiguracoes() {
        PrintWriter escrever;

        //aplica os novos valores na classe estatica Rule
        rules.setDiasEmprestimoAluno(Integer.parseInt(alunoDiasEmprestimo.getText()));
        rules.setDiasEmprestimoProfessor(Integer.parseInt(professorDiasEmprestimo.getText()));
        rules.setLimiteEmprestimoAluno((Long) Long.parseLong(quantidadeEmprestimoAluno.getText()));
        rules.setLimiteEmprestimoProfessor((Long) Long.parseLong(quantidadeEmprestimoProfessor.getText()));
        rules.setDiasParaSuspencao(Integer.parseInt(diasSuspenderAtraso.getText()));
        rules.setValorMulta(Double.parseDouble(valorMulta.getText()));

        try {
            escrever = new PrintWriter(new File("rules.xml"));
            XStream stream = new XStream(new DomDriver());
            stream.alias("Rules", Rules.class);
            escrever.println(stream.toXML(rules));
            escrever.flush();
            escrever.close();
        } catch (FileNotFoundException e) {
            box.exibrirMensagemErro("Erro ao Salvar o Arquivo!", "Não foi possível salvar o arquivo. " + e.getMessage());

        }

        box.exibrirMensagemAviso("Salvo!", "Configurações salvas com sucessos.");
    }

    @FXML
    void exibirTexto() {
        lblEntrar.setText("Entrar");
    }

    @FXML
    void sumirTexto() {
        lblEntrar.setText("");
    }

    @FXML
    void cancelarReserva() throws Exception {
        Reserva cReserva = tabelaReservasUsuario.getSelectionModel().getSelectedItem();
        Exemplar rExemplar;
        ExemplarDAO rExemplarDAO = factory.getExemplarDAO();

        if (cReserva != null) {

            if (box.perguntar("Cancelar Reserva?", "Deseja Realmente cancelar sua reserva?")) {
                //   ReservaDAO rdao = new ReservaDAO();
                cReserva.setStatus(false);
                rExemplar = cReserva.getExemplar();
                rExemplar.setQuantidade_disponivel(rExemplar.getQuantidade_disponivel() + 1);

                rExemplarDAO.salvar(rExemplar);
                rdao.salvar(cReserva);
                carregarDados();
            }

        } else {
            box.exibrirMensagemErro("Erro!", "Não foi possível encontrar e reserva.\nTente novamente.");

        }
    }

    @FXML
    void imprimirComprovante() throws FileNotFoundException, IOException {
        Emprestimo emprestimo = tabelaEmprestimosUsuario.getSelectionModel().getSelectedItem();

        if (emprestimo != null) {
            Document document = new Document();

            try {
                PdfWriter.getInstance(document, new FileOutputStream("Emprestimo " + MainPageController.atualU.getNome() + ".pdf"));
                document.open();
                document.addAuthor("Califonia Univesity Libary - System");
                document.addCreationDate();
                document.add(new Paragraph("Nome: " + MainPageController.atualU.getNome() + "\n" + emprestimo.toString(), new Font(Font.FontFamily.COURIER, 12)));
                Desktop.getDesktop().open(new File("Emprestimo " + MainPageController.atualU.getNome() + ".pdf"));

            } catch (FileNotFoundException | DocumentException ex) {
                System.out.println("Erro " + ex.getMessage());
            } finally {
                document.close();
            }

        } else {
            box.exibrirMensagemErro("Erro!", "Não foi possível encontrar o empréstimo.\nTente novamente.");

        }
    }

    @FXML
    void abrirCriarFuncionario() throws IOException {
        Stage janela = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/criarFuncionario.fxml"));
        janela.getIcons().addAll(new Image(getClass().getResourceAsStream("/recursos/imagens/icone.png")));

        Scene scene = new Scene(root);
        janela.setScene(scene);
        janela.setResizable(false);
        janela.setTitle("Criar novo Funcionário");
        janela.centerOnScreen();
        janela.showAndWait();

    }

    @FXML
    void abrirVerCaixa() throws IOException {
        Stage janela = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/VerCaixa.fxml"));
        janela.getIcons().addAll(new Image(getClass().getResourceAsStream("/recursos/imagens/icone.png")));

        Scene scene = new Scene(root);
        janela.setScene(scene);
        janela.setResizable(false);
        janela.setTitle("Consultar Caixa");
        janela.centerOnScreen();
        janela.showAndWait();
    }

    @FXML
    void desconectar() {
        MainPageController.atualF = null;
        MainPageController.atualU = null;
        definirGest();

    }

    //Variaveis
    public AnchorPane getAncorTabUsuario() {
        return ancorTabUsuario;
    }

    public AnchorPane getAncorTabInicio() {
        return ancorTabInicio;
    }

    public AnchorPane getAncorTabLivro() {
        return ancorTabLivro;
    }

    public AnchorPane getAncorTabCurso() {
        return ancorTabCurso;
    }

    public Tab getTabCursos() {
        return tabCursos;
    }

    public Tab getTabUsuarios() {
        return tabUsuarios;
    }

    public Tab getTabInicio() {
        return tabInicio;
    }

    public Tab getTabLivros() {
        return tabLivros;
    }

    public Tab getTabSetup() {
        return tabSetup;
    }

    public Tab getTabWellcome() {
        return tabWellcome;
    }

    public void setTabWellcome(Tab tabWellcome) {
        this.tabWellcome = tabWellcome;
    }

    public void setTabCursos(Tab tabCursos) {
        this.tabCursos = tabCursos;
    }

    public Label getLblEmail() {
        return lblEmail;
    }

    @FXML
    private AnchorPane ancorTabInicio;

    @FXML
    private Label lblEstado;

    @FXML
    private Label lblCidade;

    @FXML
    private TextField quantidadeEmprestimoProfessor;

    @FXML
    private Tab tabPedidosUsuario;

    @FXML
    private TextField alunoDiasEmprestimo;

    @FXML
    private Label lblCelular;

    @FXML
    private Tab tabPerfilUsuario;

    @FXML
    private Label lblNome1;

    @FXML
    private AnchorPane ancorTabPerfil;

    @FXML
    private TextField diasSuspenderAtraso;

    @FXML
    private Tab tabInicio;

    @FXML
    private AnchorPane ancorTabSetup;

    @FXML
    private TextField valorMulta;

    @FXML
    private Label lblMatricula;

    @FXML
    private Label lblEntrar;

    @FXML
    private TextField quantidadeEmprestimoAluno;

    @FXML
    private Label lblNome;

    @FXML
    private AnchorPane ancorTabCurso;

    @FXML
    private AnchorPane ancorTabPedidos;

    @FXML
    private Label lblCpf;

    @FXML
    private Tab tabEmprestimos;

    @FXML
    private Tab tabSetup;

    @FXML
    private Tab tabWellcome;

    @FXML
    private Label lblEmail;

    @FXML
    private AnchorPane ancorTabLivro;

    @FXML
    private Label lblData;

    @FXML
    private Tab tabUsuarios;

    @FXML
    private AnchorPane ancorTabUsuario;

    @FXML
    private Tab tabLivros;

    @FXML
    private TextField professorDiasEmprestimo;

    @FXML
    private ImageView btnlogar;

    @FXML
    private Label lblTelefone;

    @FXML
    private Label lblRuaNumeroBairro;

    @FXML
    private Label lblValorDebitos;

    @FXML
    private Label lblSituacao;

    @FXML
    private Tab tabCursos;

    @FXML
    private AnchorPane ancorTabEmprestimos;

    @FXML
    private TableColumn<Emprestimo, Integer> colunaIdEmprestimo;

    @FXML
    private TableColumn<Reserva, Date> colunaInicioReserva;

    @FXML
    private TableColumn<Emprestimo, String> colunaLivrosEmprestimo;

    @FXML
    private TableView<Emprestimo> tabelaEmprestimosUsuario;

    @FXML
    private TableColumn<Reserva, String> colunaLivroReserva;

    @FXML
    private TableColumn<Reserva, Integer> colunaIdReserva;

    @FXML
    private TableColumn<Reserva, Date> colunaDataReserva;

    @FXML
    private TableColumn<Emprestimo, Date> colunaInicioEmprestimo;

    @FXML
    private TableColumn<Emprestimo, Date> colunaFimEmprestimo;

    @FXML
    private TableView<Reserva> tabelaReservasUsuario;

    @FXML
    private TableColumn<Emprestimo, Boolean> colunaStatusEmprestimo;

    @FXML
    private TableColumn<Reserva, Boolean> colunaStatusReserva;

    @FXML
    private Button btnSair;

//    class AtualizarPropriedades extends Thread {
//
//        ProfessorDAO pdao = new ProfessorDAO();
//        AlunoDAO adao = new AlunoDAO();
//
//        @Override
//        public void run() {
//            while (true) {
//                try {
//                    if (atualU.isProfessor()) {
//                        atualU = pdao.buscarPorID(atualU.getId());
//                    } else {
//                        atualU = adao.buscarPorID(atualU.getId());
//                    }
//
//                    if (atualU != null) {
//                        lblValorDebitos.setText(atualU.getDebitos() + "");
//                        if (atualU.isStatus()) {
//                            lblSituacao.setText("OK");
//                        } else {
//                            lblSituacao.setText("Suspenso");
//                        }
//                    } else {
//                        desconectar();
//                    }
//                    System.out.println("valor " + atualU.isStatus());
//
//                    Thread.sleep(5000);
//                } catch (InterruptedException ex) {
//                    Logger.getLogger(AtualizarPropriedades.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//
//    }
}
