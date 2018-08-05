package model.DAO.postgreSQL;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.DAO.FuncionarioDAO;
import model.vo.Funcionario;

public class PostgreSQLFuncionarioDAO extends FuncionarioDAO{
    
     public void salvar(Funcionario a) throws Exception {
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
    
     public List<Funcionario> listarTodos() throws Exception {
        EntityManager em = getEm();
        List<Funcionario> funcionario;

        try {
            em.getTransaction().begin();
            Query q = em.createNamedQuery("Funcionario.getAll");
            funcionario = q.getResultList();

            em.getTransaction().commit();

        } catch (Exception e) {
            funcionario = new ArrayList<>();
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return funcionario;
    }
     
      public Funcionario buscarPorCpf(String cpf) throws Exception {
        EntityManager em = getEm();
        Funcionario f;        
      
        try {
            em.getTransaction().begin();
            Query q = em.createQuery("select u from Funcionario u where u.cpf = :cpf", Funcionario.class);
            q.setParameter("cpf", cpf);
            f = (Funcionario)q.getSingleResult();
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

    
}