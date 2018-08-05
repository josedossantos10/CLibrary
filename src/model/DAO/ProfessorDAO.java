package model.DAO;

import javax.persistence.EntityManager;
import model.vo.Professor;

public abstract class ProfessorDAO {

    public abstract void salvar(Professor a) throws Exception;

    public abstract Professor buscarPorCpf(String cpf) throws Exception;

    public abstract EntityManager getEm() throws Exception;

    public abstract Professor buscarPorID(int id) throws Exception;

}
