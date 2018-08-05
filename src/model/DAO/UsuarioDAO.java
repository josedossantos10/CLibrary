package model.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import model.vo.Usuario;

public abstract class UsuarioDAO {

    public abstract void salvar(Usuario a) throws Exception;

    public abstract List<Usuario> listarTodos() throws Exception;

    public abstract Usuario buscarPorID(int id) throws Exception;

    public abstract Usuario buscarPorCpf(String cpf) throws Exception;

    public abstract void delete(int id) throws Exception;

    public abstract List<Usuario> busca(String str) throws Exception;

    public abstract EntityManager getEm() throws Exception;
}
