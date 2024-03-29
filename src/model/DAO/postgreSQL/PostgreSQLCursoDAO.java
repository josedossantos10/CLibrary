package model.DAO.postgreSQL;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.DAO.CursoDAO;
import model.vo.Curso;

public class PostgreSQLCursoDAO extends CursoDAO {

    public void salvar(Curso a) throws Exception {
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
        Curso a = em.find(Curso.class, id);
        em.remove(a);
        em.getTransaction().commit();
        em.close();
    }

    public List<Curso> listarTodos() throws Exception {
        EntityManager em = getEm();
        List<Curso> curso;
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Curso.getAll");
        curso = q.getResultList();

        em.getTransaction().commit();
        em.close();
        return curso;
    }

    public EntityManager getEm() throws Exception {
        return ConnectionBD.getConnection().createEntityManager();
    }

}
