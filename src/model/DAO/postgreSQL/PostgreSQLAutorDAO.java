package model.DAO.postgreSQL;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.DAO.AutorDAO;
import model.vo.Autor;

public class PostgreSQLAutorDAO extends AutorDAO {

    public void salvar(Autor a) throws Exception {
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
        Autor a = em.find(Autor.class, id);
        em.remove(a);
        em.getTransaction().commit();
        em.close();

    }

    public List<Autor> listarTodos() throws Exception {
        EntityManager em = getEm();
        List<Autor> autor;
        em.getTransaction().begin();
        Query q = em.createNamedQuery("Departamento.getAll");
        autor = q.getResultList();

        em.getTransaction().commit();
        autor = new ArrayList<>();
        em.close();
        return autor;
    }

    public EntityManager getEm() throws Exception {
        return ConnectionBD.getConnection().createEntityManager();
    }

}
