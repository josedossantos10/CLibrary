package model.DAO.postgreSQL;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.DAO.DepartamentoDAO;
import model.vo.Departamento;

public class PostgreSQLDepartamentoDAO extends DepartamentoDAO {

    public void salvar(Departamento a) throws Exception {
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
        Departamento a = em.find(Departamento.class, id);
        em.remove(a);
        em.getTransaction().commit();
        em.close();

    }

    public List<Departamento> listarTodos() throws Exception {
        EntityManager em = getEm();
        List<Departamento> dpto;

        em.getTransaction().begin();
        Query q = em.createNamedQuery("Departamento.getAll");
        dpto = q.getResultList();

        em.getTransaction().commit();
        em.close();
        return dpto;
    }

    public EntityManager getEm() throws Exception {
        return ConnectionBD.getConnection().createEntityManager();
    }

}
