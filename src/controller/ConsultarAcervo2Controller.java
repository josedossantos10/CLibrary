package controller;

import config.Data;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DAO.ExemplarDAO;
import model.DAO.ReservaDAO;
import model.vo.Exemplar;
import model.vo.Reserva;
import model.nativeQueries.Views;
import view.Fachada;

public class ConsultarAcervo2Controller implements Initializable {

    List<Exemplar> exeplares;
    ExemplarDAO dAO = new ExemplarDAO();
    Views views = new Views();
    ObservableList<Exemplar> oExempplares;
    Fachada box = new Fachada();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            carregarDados();
        } catch (Exception ex) {
            Fachada.exibrirErro(ex.getMessage());
        }

    }

    @FXML
    void reservarLivro() throws Exception {
        ReservaDAO reservaDAO = new ReservaDAO();
        Exemplar rExemplar;
        ExemplarDAO rExemplarDAO = new ExemplarDAO();
        Reserva r = new Reserva();
        boolean temReserva = false;
        if ((MainPageController.atualU != null) && (tabelaLivro.getSelectionModel().getSelectedItem() != null)) {
            for (Reserva reserva : reservaDAO.buscarReservasPorId(MainPageController.atualU.getId())) {
                if (reserva.isStatus()) {
                    temReserva = true;
                    break;
                }

            }
            rExemplar = tabelaLivro.getSelectionModel().getSelectedItem();
            if (rExemplar.getQuantidade_disponivel() >= 1) {
                if ((MainPageController.atualU.isStatus())) {
                    if (!temReserva) {

                        r.setData_emprestimo(new Date());
                        r.setData_realizacao(new Date());

                        rExemplar.setQuantidade_disponivel(rExemplar.getQuantidade_disponivel() - 1);
                        r.setExemplar(rExemplar);

                        r.setStatus(true);
                        r.setUsuario(MainPageController.atualU);

                        rExemplarDAO.salvar(rExemplar);
                        reservaDAO.salvar(r);
                        box.exibrirMensagemAviso("Pronto", "Pronto sua reserva foi realizada com sucesso para o dia " + Data.dataMedio.format(r.getData_emprestimo()));
                    } else {
                        box.exibrirMensagemErro("Limite de reservas atingido!", "Você já pussui uma reserva em andamento.");

                    }
                } else {
                    box.exibrirMensagemErro("Conta suspensa!", "Conta suspensa pela admininstração ou usuário com empréstios em atraso.");

                }
            } else {
                box.exibrirMensagemErro("Livro esgotado!", "Não desista, tente reservar outro!\nNão é possível reservar este livro, pois não há quantidade dísponivel.");
            }

        } else {
            Fachada.exibrirMensagemErroS("Desculpe", "Não foi possivel encontrar seu perfil!\n Tente logar novamente!");
        }

    }

    @FXML
    void buscarExemplares() throws Exception {
        if (stringBusca.getText().length() > 2) {
            exeplares = dAO.busca(stringBusca.getText());
            oExempplares = FXCollections.observableArrayList(exeplares);
            tabelaLivro.setItems(oExempplares);
        } else {
            box.exibrirMensagemErro("Caractere insuficiente", "Insira pelo menos três letras para realizar a busca");
        }
    }

    @FXML
    void carregarDados() throws Exception {
        stringBusca.clear();
        columQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade_disponivel"));
        columTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        columAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        columCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
//        exeplares = dAO.listarTodos();
// Será substituida por essa
        exeplares = views.listarExemplares();
        oExempplares = FXCollections.observableArrayList(exeplares);
        tabelaLivro.setItems(oExempplares);

    }

    @FXML
    private TableView<Exemplar> tabelaLivro;

    @FXML
    private TableColumn<Exemplar, Integer> columCodigo;

    @FXML
    private TableColumn<Exemplar, Integer> columQuantidade;

    @FXML
    private TableColumn<Exemplar, String> columTitulo;

    @FXML
    private TableColumn<Exemplar, Integer> columAno;

    @FXML
    private TextField stringBusca;

}
