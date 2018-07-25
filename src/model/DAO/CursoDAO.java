package model.DAO;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Curso;

public class CursoDAO {

    public void salvar(Curso a) {
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
            Curso a = em.find(Curso.class, id);
            em.remove(a);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
    }

     public List<Curso> listarTodos() {
        EntityManager em = getEm();
        List<Curso> curso;

        try {
            em.getTransaction().begin();
            Query q = em.createNamedQuery("Curso.getAll");
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
