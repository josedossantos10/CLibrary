package controller;

import config.Data;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import model.Caixa;
import model.DAO.CaixaDAO;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import view.MessageBox;

public class VerCaixaController implements Initializable {

    CaixaDAO caixaDAO = new CaixaDAO();
    DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter formatadorArq = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    List<Caixa> caixas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private DatePicker data;

    @FXML
    private Label valor;

    @FXML
    double realizarConsulta() {
        double total = 0;
        caixas = caixaDAO.listarTodos();

        LocalDate dataConsulta = data.getValue();
        Date Dcaixa;

        for (Caixa caixa : caixas) {
            Dcaixa = caixa.getData_pagamento();
            if (Dcaixa != null) {
                String dataCaixa = Data.data.format(Dcaixa);
                if (dataCaixa.equals(dataConsulta.format(formatador))) {
                    total += caixa.getValor();
                }
            } else {
                MessageBox.exibrirMensagemErroS("Erro!", "Selecione uma data!");
            }
        }

        valor.setText(total + "");
        return total;

    }

    @FXML
    public void expExcel() throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet firstSheet = workbook.createSheet("Caixa1");

        LocalDate dataConsulta = data.getValue();
        FileOutputStream fos;
        String arquivoName = "caixa_"+dataConsulta.format(formatadorArq) + ".xls";
        try {
            fos = new FileOutputStream(new File(arquivoName));

            List<Caixa> lista = caixaDAO.listarTodos();
            int i = 2;
            Date Dcaixa;

            HSSFRow cabecalho = firstSheet.createRow(0);
            cabecalho.createCell(0).setCellValue("Caixa dos Recebimentos em " + dataConsulta.format(formatador));

            HSSFRow colunas = firstSheet.createRow(1);
            colunas.createCell(0).setCellValue("ID");
            colunas.createCell(1).setCellValue("Data");
            colunas.createCell(2).setCellValue("Valor");

            for (Caixa cd : lista) {
                Dcaixa = cd.getData_pagamento();
                String dataCaixa = Data.data.format(Dcaixa);

                if (dataCaixa.equals(dataConsulta.format(formatador))) {

                    HSSFRow linha = firstSheet.createRow(i);
                    linha.createCell(0).setCellValue(cd.getId());
                    linha.createCell(1).setCellValue(Data.data.format(cd.getData_pagamento()));
                    linha.createCell(2).setCellValue(cd.getValor());

                    i++;
                }
            }
                    HSSFRow linha = firstSheet.createRow(i);
                    linha.createCell(0).setCellValue("Valor Total: R$ ");
                    linha.createCell(2).setCellValue(realizarConsulta());
            

            workbook.write(fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            System.out.println("Erro ao exportar arquivo: " + e.getMessage());
        }

        Desktop.getDesktop().open(new File(arquivoName));

    } 
}
