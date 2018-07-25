package controller;

import config.Data;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.DAO.EmprestimoDAO;
import model.DAO.ReservaDAO;
import model.DAO.UsuarioDAO;
import model.Emprestimo;
import model.Exemplar;
import model.Reserva;
import model.Usuario;
import view.MessageBox;

public class ReservasController implements Initializable {

    ReservaDAO reservaDAO = new ReservaDAO();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    EmprestimoDAO emprestimoDAO = new EmprestimoDAO();
    MessageBox box = new MessageBox();
    EmprestimoDAO edao = new EmprestimoDAO();
    List<Reserva> reservas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarDados();

    }

    void carregarDados() {
        colunaDataReserva.setCellValueFactory(new PropertyValueFactory<>("data_realizacao"));
        colunaIdReserva.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaInicioReserva.setCellValueFactory(new PropertyValueFactory<>("data_emprestimo"));
        colunaLivroReserva.setCellValueFactory(new PropertyValueFactory<>("exemplar"));
        //Configuração da data na tableview
        colunaNomeReserva.setCellValueFactory(new PropertyValueFactory<>("usuario"));

        colunaDataReserva.setCellFactory(new Callback<TableColumn<Reserva, Date>, TableCell<Reserva, Date>>() {
            @Override
            public TableCell<Reserva, Date> call(TableColumn<Reserva, Date> param) {
                return new TableCell<Reserva, Date>() {
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

        colunaInicioReserva.setCellFactory(new Callback<TableColumn<Reserva, Date>, TableCell<Reserva, Date>>() {
            @Override
            public TableCell<Reserva, Date> call(TableColumn<Reserva, Date> param) {
                return new TableCell<Reserva, Date>() {
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

        tabelaReservasUsuario.setItems(FXCollections.observableArrayList(reservaDAO.listarReservasAtivas()));

    }

    @FXML
    void efetuarReserva() {
        if (MainPageController.atualF != null) {
            Reserva cReserva = tabelaReservasUsuario.getSelectionModel().getSelectedItem();
            Exemplar e;
            Usuario u;
            if (cReserva != null) {
                if (box.perguntar("Concretizar Empréstimo?", "Deseja Realmente realizar o empréstimo para essa reserva?")) {

                    carregarDados();
                    u = cReserva.getUsuario();
                    e = cReserva.getExemplar();

                    if (emprestimoDAO.quantidadeEmprestimoPorUsuario(u.getId()) < verificarLimiteEmprestimo(u)) {
                        if (verificarEmprestimosAbertos(u.getId(), e)) {

                            Emprestimo emprestimo = new Emprestimo();
                            Date d = new Date();

                            emprestimo.setExemplar(e);
                            emprestimo.setFuncionario(MainPageController.atualF);
                            emprestimo.setUsuario(u);
                            emprestimo.setStatus(true);
                            emprestimo.setData_entrega(null);
                            emprestimo.setData_realizacao(d);
                            emprestimo.setData_estimada(dataEntrega(d, u).getTime());

                            cReserva.setStatus(false);
                            emprestimoDAO.salvar(emprestimo);
                            reservaDAO.salvar(cReserva);
                            box.exibrirMensagemOk("Operação Concluída", "Emprestimo realizado com sucesso. Data limite da entrega: " + Data.dataFull.format(emprestimo.getData_estimada()));
                        } else {
                            box.exibrirMensagemErro("Operação não realizada", "Você já possui esse livro em um Empréstimo.", "Para realizar um novo empréstimo desse livro, primeiro realize a devolução do mesmo e refaça o emprétimo.");
                        }
                    } else {
                        MessageBox.exibrirMensagemErroS("Operação não realizada", "Limite de empréstimos atingido.");
                    }
                    carregarDados();
                }
            } else {
                box.exibrirMensagemErro("Erro!", "Não foi possível encontrar e reserva.\nTente novamente.");
            }

            //box.exibrirMensagemAviso("Concluído", "Empréstimo realizado com sucesso!");
        } else {
            MessageBox.exibrirMensagemErroS("Operação impossível", "Nenhum funcionário autorizado para esta função está logado. Tente novamente mais tarde.");

        }
    }

    @FXML
    private TableColumn<Reserva, String> colunaNomeReserva;

    @FXML
    private TableColumn<Reserva, Date> colunaDataReserva;

    @FXML
    private TableColumn<Reserva, String> colunaLivroReserva;

    @FXML
    private TableView<Reserva> tabelaReservasUsuario;

    @FXML
    private TableColumn<Reserva, Date> colunaInicioReserva;

    @FXML
    private TableColumn<Reserva, Integer> colunaIdReserva;

    private boolean verificarEmprestimosAbertos(int id, Exemplar e) {

        List<Emprestimo> ec = edao.buscarEmprestimosPorIdUsuario(id);

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
}
