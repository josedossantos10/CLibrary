package model.DAO.postgreSQL;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.DAO.EmprestimoDAO;
import model.vo.Emprestimo;

public class PostgreSQLEmprestimoDAO extends EmprestimoDAO {

    public void salvar(Emprestimo a) throws Exception {
        EntityManager em = getEm();
        em.getTransaction().begin();
        if (a.getId() == 0) {
            em.persist(a);
        } else {
            em.merge(a);
        }
        em.getTransaction().commit();
    }

    public List<Emprestimo> listarTodos() throws Exception {
        EntityManager em = getEm();
        List<Emprestimo> emprestimos;
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Emprestimo.getAll");
        emprestimos = q.getResultList();

        em.getTransaction().commit();
        em.close();
        return emprestimos;
    }

    public List<Emprestimo> buscarEmprestimosPorIdUsuario(int id) throws Exception {
        EntityManager em = getEm();
        List<Emprestimo> emprestimo;

        em.getTransaction().begin();
        TypedQuery<Emprestimo> q = em.createQuery("select u from Emprestimo u where u.usuario.id = :identificador", Emprestimo.class);
        q.setParameter("identificador", id);
        emprestimo = q.getResultList();
        em.getTransaction().commit();
        em.close();
        return emprestimo;
    }

    public List<Emprestimo> buscarEmprestimosPorIdFuncionario(int id) throws Exception {
        EntityManager em = getEm();
        List<Emprestimo> emprestimo;

        em.getTransaction().begin();
        TypedQuery<Emprestimo> q = em.createQuery("select u from Emprestimo u where u.funcionario.id = :identificador", Emprestimo.class);
        q.setParameter("identificador", id);
        emprestimo = q.getResultList();
        em.getTransaction().commit();
        em.close();
        return emprestimo;
    }

    public Long quantidadeEmprestimoPorUsuario(int id) throws Exception {
        EntityManager em = getEm();
        Long qtd = 1l;
        em.getTransaction().begin();
        Query q = em.createQuery("select count(u) from Emprestimo u where u.usuario.id = :identificador and u.status=true");
        q.setParameter("identificador", id);
        qtd = (Long) q.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return qtd;
    }

    public EntityManager getEm() throws Exception {
        return ConnectionBD.getConnection().createEntityManager();
    }

}
