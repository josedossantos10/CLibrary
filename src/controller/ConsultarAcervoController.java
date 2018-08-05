package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.DAO.ExemplarDAO;
import model.vo.Exemplar;
import view.Fachada;

public class ConsultarAcervoController implements Initializable {

    ExemplarDAO dao = new ExemplarDAO();
    private int posicaoLivro = 0;
    private int limit = 16;
    private int max = 16;

    List<Exemplar> exemplares;
    List<Label> labels = new ArrayList<>();

    public ConsultarAcervoController() {
        try {
            this.exemplares = dao.listarTodos();
        } catch (Exception ex) {
            Fachada.exibrirErro(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        labels.add(posicao1);
        labels.add(posicao2);
        labels.add(posicao3);
        labels.add(posicao4);
        labels.add(posicao5);
        labels.add(posicao6);
        labels.add(posicao7);
        labels.add(posicao8);
        labels.add(posicao9);
        labels.add(posicao10);
        labels.add(posicao11);
        labels.add(posicao12);
        labels.add(posicao13);
        labels.add(posicao14);
        labels.add(posicao15);
        labels.add(posicao16);
        carregarExemplares();

    }

    void carregarExemplares() {
        limparLabels();

        if (exemplares.size() <= max) {
            for (int i = 0; i < exemplares.size(); i++) {
                labels.get(i).setText(exemplares.get(i).getTitulo() + " Ano: " + exemplares.get(i).getAno());
            }
            btnAnterior.setDisable(true);
            btnProximo.setDisable(true);
        } else {
            int labeli = 0;
            for (int i = posicaoLivro; i < limit; i++) {
                labels.get(labeli).setText(exemplares.get(i).getTitulo() + " Ano: " + exemplares.get(i).getAno());
                labeli++;
            }
            btnAnterior.setDisable(false);
        }
    }

    @FXML
    void carregarPaginaAnterior() {
        limparLabels();
        if ((exemplares.size() - 16) <= 0) {
            posicaoLivro = 0;
            limit = exemplares.size();
            btnAnterior.setDisable(true);

        } else {
            posicaoLivro = exemplares.size() - 16;
            limit = exemplares.size();
            btnAnterior.setDisable(false);

        }
        carregarExemplares();

    }

    @FXML
    void carregarProximaPagina() {
        limparLabels();

        if (exemplares.size() < max) {
            posicaoLivro = exemplares.size()-16;
            limit = exemplares.size();
            btnAnterior.setDisable(false);
            btnProximo.setDisable(true);

        } else {
            posicaoLivro = limit;
            limit = limit * 2;
            btnAnterior.setDisable(false);

        }

        carregarExemplares();

    }

    void limparLabels() {
        for (int i = 0; i < labels.size(); i++) {
            labels.get(i).setText("");

        }

    }

    @FXML
    private Label posicao1;

    @FXML
    private Label posicao2;

    @FXML
    private Label posicao3;

    @FXML
    private Label posicao4;

    @FXML
    private Label posicao5;

    @FXML
    private Label posicao6;

    @FXML
    private Label posicao7;

    @FXML
    private Label posicao8;

    @FXML
    private Label posicao9;

    @FXML
    private Label posicao10;

    @FXML
    private Label posicao11;

    @FXML
    private Label posicao12;

    @FXML
    private Label posicao13;

    @FXML
    private Label posicao14;

    @FXML
    private Label posicao15;

    @FXML
    private Label posicao16;

    @FXML
    private Button btnProximo;

    @FXML
    private Button btnAnterior;

}
