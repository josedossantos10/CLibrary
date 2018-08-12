/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import config.Data;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.Fachada;

/**
 *
 * @author josed
 */
public class SalvarLog {
    
    FileWriter fw; 

    public SalvarLog() {
        try {
            File arquivo = new File("log.txt");
            fw = new FileWriter(arquivo, true);
        } catch (IOException ex) {
            Fachada.exibrirErro(ex.getMessage());
        }
      
    }
    
    public void salvar(String s) {
        try {
            fw.write("\n"+new Date()+" : ");
            fw.write(s);
            fw.flush();
            fw.close();
        } catch (IOException ex) {
            Fachada.exibrirErro(ex.getMessage());
        }
    
    }
    
    
}
