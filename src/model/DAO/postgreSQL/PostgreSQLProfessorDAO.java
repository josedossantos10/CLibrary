package model.DAO.postgreSQL;

import Sigleton.ConnectionBD;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.DAO.ProfessorDAO;
import model.vo.Professor;

public class PostgreSQLProfessorDAO extends ProfessorDAO{

    public void salvar(Professor a) throws Exception {
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

    public Professor buscarPorCpf(String cpf) throws Exception {
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
    
    public EntityManager getEm() throws Exception {
        return ConnectionBD.getConnection().createEntityManager();
    }
    
     public Professor buscarPorID(int id) throws Exception {
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
