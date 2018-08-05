package model.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import model.vo.Curso;

public abstract class CursoDAO {

    public abstract void salvar(Curso a) throws Exception;

    public abstract void delete(int id) throws Exception;

    public abstract List<Curso> listarTodos() throws Exception;

    public abstract EntityManager getEm() throws Exception;

}
