package model.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import model.vo.Exemplar;

public abstract class ExemplarDAO {

    public abstract void salvar(Exemplar a) throws Exception;

    public abstract List<Exemplar> listarTodos() throws Exception;

    public abstract List<Exemplar> busca(String str) throws Exception;

    public abstract void delete(int id) throws Exception;

    public abstract EntityManager getEm() throws Exception;

}
