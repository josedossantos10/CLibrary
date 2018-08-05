package model.DAO;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.vo.Aluno;

public class AlunoDAO {
    

    public EntityManager getEm() throws Exception {
        return ConnectionBD.getConnection().createEntityManager();
    }

    public void salvar(Aluno a) throws Exception {
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
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

    }
    
    public void delete(int id) throws Exception {
        EntityManager em = getEm();

        try {
            em.getTransaction().begin();
            Aluno a = em.find(Aluno.class, id);
            em.remove(a);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }

        
        
    }
    
    
     public List<Aluno> listarTodos() throws Exception {
        EntityManager em = getEm();
        List<Aluno> usuarios;

        try {
            em.getTransaction().begin();
            TypedQuery<Aluno> q =  em.createNamedQuery("Aluno.getAll", Aluno.class);
            usuarios = q.getResultList();
            em.getTransaction().commit();

        } catch (Exception e) {
            usuarios = new ArrayList<>();
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return usuarios;
    }
     
       public Aluno buscarPorCpf(String cpf) throws Exception {
        EntityManager em = getEm();
        Aluno f;        
      
        try {
            em.getTransaction().begin();
            TypedQuery<Aluno> q = em.createQuery("select u from Aluno u where u.cpf = :cpf", Aluno.class);
            q.setParameter("cpf", cpf);
            f = q.getSingleResult();
            em.getTransaction().commit();
            //System.out.println("\n\n"+f.getCpf()+ "  "+ f.getNome()+"\n\n");
            return f;

        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return null;
    }

       
       
       
        public Aluno buscarPorID(int id) throws Exception {
        EntityManager em = getEm();
        Aluno f;        
      
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("SELECT * FROM public.aluno a where a.id="+id+";", Aluno.class);
            f = (Aluno) q.getSingleResult();
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
}
