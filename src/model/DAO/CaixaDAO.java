package model.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import model.vo.Caixa;

public abstract class CaixaDAO {

    public abstract void salvar(Caixa a) throws Exception;

    public abstract void delete(int id) throws Exception;

    public abstract List<Caixa> listarTodos() throws Exception;

    public abstract EntityManager getEm() throws Exception;
}
