package model.DAO.postgreSQL;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.DAO.ExemplarDAO;
import model.vo.Exemplar;

public class PostgreSQLExemplarDAO extends ExemplarDAO {

    public void salvar(Exemplar a) throws Exception {
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

    public List<Exemplar> listarTodos() throws Exception {
        EntityManager em = getEm();
        List<Exemplar> exemplares;

        em.getTransaction().begin();
        Query q = em.createNamedQuery("Exemplar.getAll");
        exemplares = q.getResultList();

        em.getTransaction().commit();
        em.close();

        return exemplares;

    }

    public List<Exemplar> busca(String str) throws Exception {
        EntityManager em = getEm();
        List<Exemplar> exemplares;
        String query = "select e from Exemplar e join e.autores a where LOWER(a.nome) like :string or LOWER(e.titulo) like :string OR LOWER(e.editora) like :string ";
        int cod = 0;
        boolean entra = false;
        cod = Integer.valueOf(str);
        query += "or e.codigo >= :cod or e.ano = :cod ";
        entra = true;

        em.getTransaction().begin();
        TypedQuery<Exemplar> q = em.createQuery(query + "order by e.titulo", Exemplar.class);
        q.setParameter("string", "%" + str.toLowerCase() + "%");
        if (entra) {
            q.setParameter("cod", cod);

        }
        exemplares = q.getResultList();
        em.getTransaction().commit();
        em.close();
        return exemplares;

    }

    public void delete(int id) throws Exception {
        EntityManager em = getEm();

        em.getTransaction().begin();
        Exemplar a = em.find(Exemplar.class, id);

        em.remove(a);
        em.getTransaction().commit();

        em.close();
    }

    public EntityManager getEm() throws Exception {
        return ConnectionBD.getConnection().createEntityManager();
    }

}
