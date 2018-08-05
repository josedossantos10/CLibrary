package model.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import model.vo.Autor;

public abstract class AutorDAO {

    public abstract void salvar(Autor a) throws Exception;

    public abstract void delete(int id) throws Exception;

    public abstract List<Autor> listarTodos() throws Exception;

    public abstract EntityManager getEm() throws Exception;

}
