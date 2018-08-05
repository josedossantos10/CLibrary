package model.DAO;

import Sigleton.ConnectionBD;
import javax.persistence.EntityManager;
import model.vo.Endereco;

public class EnderecoDAO {

    public void salvar(Endereco a) throws Exception {
        EntityManager em = getEm();
        try {
              em.getTransaction().begin();
            if (a.getId() == 0) {
                em.persist(a);
            } else {
                em.merge(a);
            }
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Erro al Salvar: " + e.getMessage());
        }
    }

       public Endereco find(int i) throws Exception {
        EntityManager em = getEm();
        Endereco a = new Endereco();
        
        try {
            em.getTransaction().begin();
            a = em.find(Endereco.class, i);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Erro ao buscar: " + e.getMessage());
        }
        return a;
       }
    
   public EntityManager getEm() throws Exception {
        return ConnectionBD.getConnection().createEntityManager();
    }

}
