package model.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import model.vo.Aluno;

public abstract class AlunoDAO {

    public abstract EntityManager getEm() throws Exception;

    public abstract void salvar(Aluno a) throws Exception;

    public abstract List<Aluno> listarTodos() throws Exception;

    public abstract Aluno buscarPorCpf(String cpf) throws Exception;

    public abstract Aluno buscarPorID(int id) throws Exception;
}
