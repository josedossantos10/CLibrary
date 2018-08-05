package model.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import model.vo.Departamento;

public abstract class DepartamentoDAO {

    public abstract void salvar(Departamento a) throws Exception;

    public abstract void delete(int id) throws Exception;

    public abstract List<Departamento> listarTodos() throws Exception;

    public abstract EntityManager getEm() throws Exception;

}
