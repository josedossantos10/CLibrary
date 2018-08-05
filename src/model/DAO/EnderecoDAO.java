package model.DAO;

import javax.persistence.EntityManager;
import model.vo.Endereco;

public abstract class EnderecoDAO {

    public abstract void salvar(Endereco a) throws Exception;

    public abstract Endereco find(int i) throws Exception;

    public abstract EntityManager getEm() throws Exception;
}
