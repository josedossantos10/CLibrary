/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static Sigleton.ConnectionBD.factory;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DAO.AlunoDAO;
import model.DAO.ProfessorDAO;
import model.vo.Aluno;
import model.vo.Professor;
import model.vo.Usuario;
import view.Fachada;

/**
 *
 * @author josed
 */
public class RealizarLoginUsuario extends RealizarLogin {

    Usuario ready = null;

    @Override
    public boolean ValidarDados(Usuario u) {
        try {
            AlunoDAO ao = factory.getAlunoDAO();
            Aluno a = ao.buscarPorCpf(u.getCpf());
            if (!(a != null)) {
                box.exibrirMensagemErro("Erro ao logar Usuário", "Usuário ou senha incorretos!", "Nenhum Professor ou aluno encontrado! Favor entrar em contato com Administrador do sistema.");

            } else if (a.getSenha().equals(u.getSenha())) {
                ready = a;
                return true;

            } else {
                ProfessorDAO pDao = factory.getProfessorDAO();
                Professor p = pDao.buscarPorCpf(u.getCpf());
                if (!(p != null)) {
                    box.exibrirMensagemErro("Erro ao logar Usuário", "Usuário ou senha incorretos!", "Nenhum Professor ou aluno encontrado! Favor entrar em contato com Administrador do sistema!");
                } else if (p.getSenha().equals(u.getSenha())) {
                    ready = p;
                    return true;

                } else {
                    box.exibrirMensagemErro("Erro ao logar como Usuário", "Senha Inválida", "Tente Novamente!");
                }

            }
        } catch (Exception ex) {
            Fachada.exibrirErro(ex.getMessage());
        }
        return false;
    }

    @Override
    public void PrepararSistema() {
        MainPageController.atualU = ready;
        MainPageController.atualF = null;
    }

}
