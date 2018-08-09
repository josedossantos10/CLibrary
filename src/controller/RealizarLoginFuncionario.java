/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.DAO.FuncionarioDAO;
import model.nativeQueries.StoredProcedure;
import model.vo.Funcionario;
import model.vo.Usuario;
import view.Fachada;

/**
 *
 * @author josed
 */
public class RealizarLoginFuncionario extends RealizarLogin {

    StoredProcedure procedure = new StoredProcedure();

    Funcionario ready = null;

    @Override
    public boolean ValidarDados(Usuario u) {
        try {
            FuncionarioDAO o = factory.getFuncionarioDAO();

            if (0 < procedure.validarFuncionario(u.getCpf(), u.getSenha())) {
                Funcionario f = o.buscarPorCpf(u.getCpf());
                ready=f;
                return true;
            } else {
                box.exibrirMensagemErro("Erro ao logar Funcionário", "Usuário ou senha incorretos!", "Nenhum Funcionário encontrado! Tente logar como Aluno/Professor.");
            }
        } catch (Exception ex) {
            Fachada.exibrirErro(ex.getMessage());
        }
        return false;
    }

    @Override
    public void PrepararSistema() {
        MainPageController.atualU = null;
        MainPageController.atualF = ready;

    }

}
