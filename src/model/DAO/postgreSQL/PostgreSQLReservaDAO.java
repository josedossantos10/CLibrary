package model.DAO.postgreSQL;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.DAO.ReservaDAO;
import model.vo.Reserva;

public class PostgreSQLReservaDAO extends ReservaDAO{

    public void salvar(Reserva a) throws Exception {
        EntityManager em = getEm();

        em.getTransaction().begin();
            if (a.getId() == 0) {
                em.persist(a);
            } else {
                em.merge(a);
            }
            em.getTransaction().commit();

    }

    public List<Reserva> listarTodos() throws Exception {
        EntityManager em = getEm();
        List<Reserva> reserva;

            em.getTransaction().begin();
            Query q = em.createNamedQuery("Reserva.getAll");
            reserva = q.getResultList();

            em.getTransaction().commit();
            reserva = new ArrayList<>();
            em.getTransaction().rollback();
            em.close();
        return reserva;
    }

    public List<Reserva> listarReservasAtivas() throws Exception {
        EntityManager em = getEm();
        List<Reserva> reserva;

            em.getTransaction().begin();
            Query q = em.createQuery("select e from Reserva e where e.status=true");
            reserva = q.getResultList();

            em.getTransaction().commit();
            em.close();
        return reserva;
    }

    
    public List<Reserva> buscarReservasPorId(int id) throws Exception {
        EntityManager em = getEm();
        List<Reserva> f;

 
            em.getTransaction().begin();
            TypedQuery<Reserva> q = em.createQuery("select u from Reserva u where u.usuario.id = :identificador", Reserva.class);
            q.setParameter("identificador", id);
            f = q.getResultList();
            em.getTransaction().commit();
            em.close();
            return f;
    }

   public EntityManager getEm() throws Exception {
        return ConnectionBD.getConnection().createEntityManager();
    }
}
