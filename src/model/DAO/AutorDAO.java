package model.DAO;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Autor;

class AutorDAO {

    public void salvar(Autor a) {
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
            Autor a = em.find(Autor.class, id);
            em.remove(a);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }


    }
        
         public List<Autor> listarTodos() {
        EntityManager em = getEm();
        List<Autor> autor;

        try {
            em.getTransaction().begin();
            Query q = em.createNamedQuery("Departamento.getAll");
            autor = q.getResultList();

            em.getTransaction().commit();

        } catch (Exception e) {
            autor = new ArrayList<>();
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return autor;
    }
         
  public EntityManager getEm() {
        return ConnectionBD.getConnection().createEntityManager();
    }

}
