package model.DAO.postgreSQL;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.DAO.CaixaDAO;
import model.vo.Caixa;

public class PostgreSQLCaixaDAO extends CaixaDAO {

    public void salvar(Caixa a) throws Exception {
        EntityManager em = getEm();
        em.getTransaction().begin();
        if (a.getId() == 0) {
            em.persist(a);
        } else {
            em.merge(a);
        }
        em.getTransaction().commit();

        em.getTransaction().rollback();
    }

    public void delete(int id) throws Exception {
        EntityManager em = getEm();

        em.getTransaction().begin();
        Caixa a = em.find(Caixa.class, id);
        em.remove(a);
        em.getTransaction().commit();

        em.close();
    }

    public List<Caixa> listarTodos() throws Exception {
        EntityManager em = getEm();
        List<Caixa> curso;

        em.getTransaction().begin();
        Query q = em.createQuery("select e from Caixa e");
        curso = q.getResultList();

        em.getTransaction().commit();

        curso = new ArrayList<>();
        em.close();
        return curso;
    }

    public EntityManager getEm() throws Exception {
        return ConnectionBD.getConnection().createEntityManager();
    }

}
