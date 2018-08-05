package model.DAO.postgreSQL;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.DAO.AlunoDAO;
import model.vo.Aluno;

public class PostgreSQLAlunoDAO extends AlunoDAO {

    public EntityManager getEm() throws Exception {
        return ConnectionBD.getConnection().createEntityManager();
    }

    public void salvar(Aluno a) throws Exception {
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

    public void delete(int id) throws Exception {
        EntityManager em = getEm();
        em.getTransaction().begin();
        Aluno a = em.find(Aluno.class, id);
        em.remove(a);
        em.getTransaction().commit();
        em.close();

    }

    public List<Aluno> listarTodos() throws Exception {
        EntityManager em = getEm();
        List<Aluno> usuarios = new ArrayList<Aluno>();
        em.getTransaction().begin();
        TypedQuery<Aluno> q = em.createNamedQuery("Aluno.getAll", Aluno.class);
        usuarios = q.getResultList();
        em.getTransaction().commit();
        em.close();
        return usuarios;
    }

    public Aluno buscarPorCpf(String cpf) throws Exception {
        EntityManager em = getEm();
        Aluno f;
        em.getTransaction().begin();
        TypedQuery<Aluno> q = em.createQuery("select u from Aluno u where u.cpf = :cpf", Aluno.class);
        q.setParameter("cpf", cpf);
        f = q.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return f;
    }

    public Aluno buscarPorID(int id) throws Exception {
        EntityManager em = getEm();
        Aluno f;

        em.getTransaction().begin();
        Query q = em.createNativeQuery("SELECT * FROM public.aluno a where a.id=" + id + ";", Aluno.class);
        f = (Aluno) q.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return f;
    }
}
