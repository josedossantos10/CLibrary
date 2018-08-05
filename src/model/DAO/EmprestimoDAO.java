package model.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import model.vo.Emprestimo;

public abstract class EmprestimoDAO {

    public abstract void salvar(Emprestimo a) throws Exception;

    public abstract List<Emprestimo> listarTodos() throws Exception;

    public abstract List<Emprestimo> buscarEmprestimosPorIdUsuario(int id) throws Exception;

    public abstract List<Emprestimo> buscarEmprestimosPorIdFuncionario(int id) throws Exception;

    public abstract Long quantidadeEmprestimoPorUsuario(int id) throws Exception;

    public abstract EntityManager getEm() throws Exception;
}
