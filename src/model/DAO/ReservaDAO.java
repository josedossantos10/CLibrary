package model.DAO;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Reserva;

public class ReservaDAO {

    public void salvar(Reserva a) {
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
            em.getTransaction().rollback();
            System.out.println("Erro ao Salvar: " + e.getMessage());
        }
    }

    public List<Reserva> listarTodos() {
        EntityManager em = getEm();
        List<Reserva> reserva;

        try {
            em.getTransaction().begin();
            Query q = em.createNamedQuery("Reserva.getAll");
            reserva = q.getResultList();

            em.getTransaction().commit();

        } catch (Exception e) {
            reserva = new ArrayList<>();
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return reserva;
    }

    public List<Reserva> listarReservasAtivas() {
        EntityManager em = getEm();
        List<Reserva> reserva;

        try {
            em.getTransaction().begin();
            Query q = em.createQuery("select e from Reserva e where e.status=true");
            reserva = q.getResultList();

            em.getTransaction().commit();

        } catch (Exception e) {
            reserva = new ArrayList<>();
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return reserva;
    }

    
    public List<Reserva> buscarReservasPorId(int id) {
        EntityManager em = getEm();
        List<Reserva> f;

        try {
            em.getTransaction().begin();
            TypedQuery<Reserva> q = em.createQuery("select u from Reserva u where u.usuario.id = :identificador", Reserva.class);
            q.setParameter("identificador", id);
            f = q.getResultList();
            em.getTransaction().commit();
            return f;

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return null;
    }

   public EntityManager getEm() {
        return ConnectionBD.getConnection().createEntityManager();
    }
}
