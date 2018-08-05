
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import config.Rules;
import controller.MainPageController;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.nativeQueries.StoredProcedure;
import view.Fachada;

public class Main extends Application {

    @Override
    public void start(Stage Stage) {
        FileReader ler;

        //  UsuarioDAO dAO = new UsuarioDAO();
        // dAO.salvar(new Usuario("José", "1234", "123456", true, 1234, 4332, "emsdw@trgfbr.com", 123, 21, null));
        //  FXMLLoader FXLogin = new FXMLLoader();
        ///   FXLogin.setLocation(getClass().getResource("view/LoginPage.fxml"));
        //  LoginPageController LC =FXLogin.getController();
        //LC.stage=Stage;
        try {
            FXMLLoader FXLogin = new FXMLLoader(getClass().getResource("/view/MainPage.fxml"));
            Parent root = FXLogin.load();
            MainPageController LC = FXLogin.getController();

            Scene scene = new Scene(root);
            Stage.getIcons().addAll(new Image(getClass().getResourceAsStream("recursos/imagens/icone.png")));
            Stage.setScene(scene);
            Stage.setResizable(false);
            Stage.setTitle("Sistema de Gerenciamento da Biblioteca da Califonia University");
            //LC.definirGest();

            //LoginPageController.stage = Stage;
            Stage.centerOnScreen();

            Stage.show();

        } catch (IOException ex) {
            System.out.println("\n " + ex.getMessage());
        }

        XStream stream = new XStream(new DomDriver());
        try {
            stream.alias("Rules", Rules.class);
            ler = new FileReader("rules.xml");
            Rules ee = (Rules) stream.fromXML(ler);

            MainPageController.rules.setDiasEmprestimoAluno(ee.getDiasEmprestimoAluno());
            MainPageController.rules.setDiasEmprestimoProfessor(ee.getDiasEmprestimoProfessor());
            MainPageController.rules.setLimiteEmprestimoAluno(ee.getLimiteEmprestimoAluno());
            MainPageController.rules.setLimiteEmprestimoProfessor(ee.getLimiteEmprestimoProfessor());
            MainPageController.rules.setDiasParaSuspencao(ee.getDiasParaSuspencao());
            MainPageController.rules.setValorMulta(ee.getValorMulta());

        } catch (FileNotFoundException e) {
            Fachada.exibrirMensagemErroS("Erro ao carregar", "Arquivo de configuração não encontrado, carregando configurações padrão.");
            System.out.println("Arquivo de configuração não encontrado, carregando configurações padrão. " + e.getMessage());

        }

    }

    public static void main(String[] args) throws Exception {
        StoredProcedure storedProcedure = new StoredProcedure();
        
        storedProcedure.suspenderAluno();
        storedProcedure.suspenderProfessor();
        storedProcedure.expirarReservas();

        // FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        // funcionarioDAO.salvar(new Funcionario("1010", "123", "José", 32132));
        // new  ExemplarDAO().salvar(new Exemplar(111, 2001, "Cinquenta Tons de Cinza", "Grey", 1, 2));
        // new UsuarioDAO().salvar( new Usuario("José Antônio", "123", "10104785403", true, "87981383528", 101, "josedossantos@outlook.com", "39351040", 0, new Endereco("Rua do Açude", 3750, "Maravilha", "Custódia", "Pernambuco")));
        launch(args);
    }

}
