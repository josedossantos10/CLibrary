package model.DAO;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.vo.Departamento;

public class DepartamentoDAO {
     public void salvar(Departamento a) throws Exception {
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
     
      public void delete(int id) throws Exception {
        EntityManager em = getEm();

        try {
            em.getTransaction().begin();
            Departamento a = em.find(Departamento.class, id);
            em.remove(a);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
      }

       public List<Departamento> listarTodos() throws Exception {
        EntityManager em = getEm();
        List<Departamento> dpto;

        try {
            em.getTransaction().begin();
            Query q = em.createNamedQuery("Departamento.getAll");
            dpto = q.getResultList();

            em.getTransaction().commit();

        } catch (Exception e) {
            dpto = new ArrayList<>();
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return dpto;
    }
      
  public EntityManager getEm() throws Exception {
        return ConnectionBD.getConnection().createEntityManager();
    }

    
}
