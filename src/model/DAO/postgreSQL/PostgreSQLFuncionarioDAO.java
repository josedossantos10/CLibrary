package model.DAO.postgreSQL;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.DAO.FuncionarioDAO;
import model.vo.Funcionario;

public class PostgreSQLFuncionarioDAO extends FuncionarioDAO {

    public void salvar(Funcionario a) throws Exception {
        EntityManager em = getEm();
        em.getTransaction().begin();
        if (a.getId() == 0) {
            em.persist(a);
        } else {
            em.merge(a);
        }
        em.getTransaction().commit();
        em.close();

    }

    public List<Funcionario> listarTodos() throws Exception {
        EntityManager em = getEm();
        List<Funcionario> funcionario;

        em.getTransaction().begin();
        Query q = em.createNamedQuery("Funcionario.getAll");
        funcionario = q.getResultList();

        em.getTransaction().commit();
        em.close();
        return funcionario;
    }

    public Funcionario buscarPorCpf(String cpf) throws Exception {
        EntityManager em = getEm();
        Funcionario f;
        em.getTransaction().begin();
        Query q = em.createQuery("select u from Funcionario u where u.cpf = :cpf", Funcionario.class);
        q.setParameter("cpf", cpf);
        f = (Funcionario) q.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return f;
    }

    public EntityManager getEm() throws Exception {
        return ConnectionBD.getConnection().createEntityManager();
    }

}
