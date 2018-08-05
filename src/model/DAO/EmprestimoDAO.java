package model.DAO;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.vo.Emprestimo;

public class EmprestimoDAO {

    public void salvar(Emprestimo a) throws Exception {
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
            System.out.println("Erro ao Salvar: " + e.getMessage());
            em.getTransaction().rollback();
        }
    }


    public List<Emprestimo> listarTodos() throws Exception {
        EntityManager em = getEm();
        List<Emprestimo> emprestimos;

        try {
            em.getTransaction().begin();
            Query q = em.createNamedQuery("Emprestimo.getAll");
            emprestimos = q.getResultList();

            em.getTransaction().commit();

        } catch (Exception e) {
            emprestimos = new ArrayList<>();
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return emprestimos;
    }

    public List<Emprestimo> buscarEmprestimosPorIdUsuario(int id) throws Exception {
        EntityManager em = getEm();
        List<Emprestimo> emprestimo;

        try {
            em.getTransaction().begin();
            TypedQuery<Emprestimo> q = em.createQuery("select u from Emprestimo u where u.usuario.id = :identificador", Emprestimo.class);
            q.setParameter("identificador", id);
            emprestimo = q.getResultList();
            em.getTransaction().commit();
            return emprestimo;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return null;
    }

    public List <Emprestimo> buscarEmprestimosPorIdFuncionario(int id) throws Exception {
        EntityManager em = getEm();
        List<Emprestimo> emprestimo;

        try {
            em.getTransaction().begin();
            TypedQuery<Emprestimo> q = em.createQuery("select u from Emprestimo u where u.funcionario.id = :identificador", Emprestimo.class);
            q.setParameter("identificador", id);
            emprestimo = q.getResultList();
            em.getTransaction().commit();
            return emprestimo;

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return null;
    }


public Long quantidadeEmprestimoPorUsuario(int id) throws Exception {
        EntityManager em = getEm();
        Long qtd=1l;

        try {
            em.getTransaction().begin();
            Query q = em.createQuery("select count(u) from Emprestimo u where u.usuario.id = :identificador and u.status=true");
            q.setParameter("identificador", id);
            qtd = (Long) q.getSingleResult();
            em.getTransaction().commit();
            return qtd;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return qtd;
    }

  public EntityManager getEm() throws Exception {
        return ConnectionBD.getConnection().createEntityManager();
    }

}


