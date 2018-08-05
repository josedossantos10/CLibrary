package model.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import model.vo.Funcionario;

public abstract class FuncionarioDAO {

    public abstract void salvar(Funcionario a) throws Exception;

    public abstract List<Funcionario> listarTodos() throws Exception;

    public abstract Funcionario buscarPorCpf(String cpf) throws Exception;

    public abstract EntityManager getEm() throws Exception;

}
