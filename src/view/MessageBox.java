
package view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MessageBox {
    
  
    public static void exibrirMensagemErroS(String titulo, String msg) {
        
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(titulo);
        a.setHeaderText(msg);
        a.showAndWait();

    }
    
       public void exibrirMensagemErro(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(titulo);
        a.setHeaderText(msg);
        a.show();

    }

    public void exibrirMensagemErro(String titulo, String msg, String adicionais) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(titulo);
        a.setHeaderText(msg);
        a.setContentText(adicionais);
        a.show();
    }

    public void exibrirMensagemAviso(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(titulo);
        a.setHeaderText(msg);
        a.show();

    }

    public void exibrirMensagemOk(String titulo, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titulo);
        a.setHeaderText(msg);
        a.show();
    }

    public boolean perguntar(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(msg);
        alert.showAndWait();
        return alert.getResult() == ButtonType.OK;
    }

    public boolean perguntar(String titulo, String msg, String adicionais) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(msg);
        alert.setContentText(adicionais);
        alert.showAndWait();
        return alert.getResult() == ButtonType.OK;
    }

        public  void exibrirFuncionalidadeEmImplamentacao() {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Função Não disponível");
        a.setHeaderText("Funcionalidade em implementação");
        a.show();

    }
    
    
    
    
}
