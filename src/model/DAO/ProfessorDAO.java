package model.DAO;

import Sigleton.ConnectionBD;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Aluno;
import model.Professor;

public class ProfessorDAO {

    public void salvar(Professor a) {
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
            System.out.println("Erro ao salvar " + e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

    }

    public Professor buscarPorCpf(String cpf) {
        EntityManager em = getEm();
        Professor f;

        try {
            em.getTransaction().begin();
            Query q = em.createQuery("select u from Professor u where u.cpf = :cpf", Professor.class);
            q.setParameter("cpf", cpf);
            f = (Professor) q.getSingleResult();
            em.getTransaction().commit();
            return f;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return null;

    }
    
    public EntityManager getEm() {
        return ConnectionBD.getConnection().createEntityManager();
    }
    
     public Professor buscarPorID(int id) {
        EntityManager em = getEm();
        Professor f;        
      
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("SELECT * FROM public.professor a where a.id="+id+";", Professor.class);
            f = (Professor) q.getSingleResult();
            em.getTransaction().commit();
            //System.out.println("\n\n"+f.getCpf()+ "  "+ f.getNome()+"\n\n");
            return f;

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return null;
    }

}
