package controller;

import static controller.MainPageController.atualU;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.DAO.AlunoDAO;
import model.DAO.DAOFactory;
import model.DAO.ProfessorDAO;
import model.vo.Aluno;
import model.vo.Professor;
import view.Fachada;

/**
 * FXML Controller class
 *
 * @author josed
 */
public class PerfilUsuarioController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            carregarDados();
        } catch (Exception ex) {
            Fachada.exibrirErro(ex.getMessage());
        }
    }

    void carregarDados() throws Exception {
        lblNome1.setText(atualU.getNome());
        lblCpf.setText(atualU.getCpf());
        lblMatricula.setText(atualU.getMatricula() + "");
        //lblEmail.setText(atualU.getEmail());
        lblTelefone.setText(atualU.getTelefone());
        lblCelular.setText(atualU.getCelular());

        lblRuaNumeroBairro.setText(atualU.getEndereco(atualU.getId()).getRua() + ", Nº " + atualU.getEndereco(atualU.getId()).getNumero() + "- " + atualU.getEndereco(atualU.getId()).getBairro());
        lblCidade.setText(atualU.getEndereco(atualU.getId()).getCidade());
        lblEstado.setText(atualU.getEndereco(atualU.getId()).getEstado());

        lblValorDebitos.setText(atualU.getDebitos() + "");
        if (atualU.isStatus()) {
            lblSituacao.setText("OK");
        } else {
            lblSituacao.setText("Suspenso");

        }
        atualizarTabPerfil();

    }

    @FXML
    private AnchorPane ancorTabPerfil;

    @FXML
    private Label lblEstado;

    @FXML
    private Label lblCpf;

    @FXML
    private Label lblCidade;

    @FXML
    private Label lblTelefone;

    @FXML
    private Label lblRuaNumeroBairro;

    @FXML
    private Label lblSituacao;

    @FXML
    private Label lblMatricula;

    @FXML
    private Label lblValorDebitos;

    @FXML
    private Label lblCelular;

    @FXML
    private Label lblNome1;

    @FXML
    void atualizarTabPerfil() throws Exception {
        DAOFactory factory = DAOFactory.getInstace();
        ProfessorDAO pdao = factory.getProfessorDAO();
        AlunoDAO adao = factory.getAlunoDAO();
        if (MainPageController.atualU.isProfessor()) {

            Professor p = pdao.buscarPorID(MainPageController.atualU.getId());
            if (p.isStatus()) {
                lblSituacao.setText("OK");
            } else {
                lblSituacao.setText("Suspenso");
            }
        } else {
            Aluno a = adao.buscarPorID(MainPageController.atualU.getId());
            if (a.isStatus()) {
                lblSituacao.setText("OK");
            } else {
                lblSituacao.setText("Suspenso");
            }
        }
        lblValorDebitos.setText(MainPageController.atualU.getDebitos() + "");

    }

}
