package model.DAO.postgreSQL;

import Sigleton.ConnectionBD;
import javax.persistence.EntityManager;
import model.DAO.EnderecoDAO;
import model.vo.Endereco;

public class PostgreSQLEnderecoDAO extends EnderecoDAO{

    public void salvar(Endereco a) throws Exception {
        EntityManager em = getEm();
              em.getTransaction().begin();
            if (a.getId() == 0) {
                em.persist(a);
            } else {
                em.merge(a);
            }
            em.getTransaction().commit();

    }

       public Endereco find(int i) throws Exception {
        EntityManager em = getEm();
        Endereco a = new Endereco();
            em.getTransaction().begin();
            a = em.find(Endereco.class, i);
            em.getTransaction().commit();
            em.getTransaction().rollback();
        return a;
       }
    
   public EntityManager getEm() throws Exception {
        return ConnectionBD.getConnection().createEntityManager();
    }

}
