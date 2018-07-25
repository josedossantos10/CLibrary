package model.DAO;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Caixa;

public class CaixaDAO {

  public void salvar(Caixa a) {
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

    public void delete(int id) {
        EntityManager em = getEm();

        try {
            em.getTransaction().begin();
            Caixa a = em.find(Caixa.class, id);
            em.remove(a);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
    }

     public List<Caixa> listarTodos() {
        EntityManager em = getEm();
        List<Caixa> curso;

        try {
            em.getTransaction().begin();
            Query q = em.createQuery("select e from Caixa e");
            curso = q.getResultList();

            em.getTransaction().commit();

        } catch (Exception e) {
            curso = new ArrayList<>();
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return curso;
    }
     
  public EntityManager getEm() {
        return ConnectionBD.getConnection().createEntityManager();
    }
    
}
