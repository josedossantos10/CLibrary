package model.DAO;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.vo.Exemplar;

public class ExemplarDAO {

    public void salvar(Exemplar a) throws Exception {
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
            System.out.println("\nErro al Salvar: " + e.getMessage());
            em.getTransaction().rollback();
        }
    }

    public List<Exemplar> listarTodos() throws Exception {
        EntityManager em = getEm();
        List<Exemplar> exemplares;

        try {
            em.getTransaction().begin();
            Query q = em.createNamedQuery("Exemplar.getAll");
            exemplares = q.getResultList();

            em.getTransaction().commit();

        } catch (Exception e) {
            exemplares = new ArrayList<>();
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }

        return exemplares;

    }

    public List<Exemplar> busca(String str) throws Exception {
        EntityManager em = getEm();
        List<Exemplar> exemplares;
        String query = "select e from Exemplar e join e.autores a where LOWER(a.nome) like :string or LOWER(e.titulo) like :string OR LOWER(e.editora) like :string ";
        int cod = 0;
        boolean entra = false;
        try {
            cod = Integer.valueOf(str);
            query += "or e.codigo >= :cod or e.ano = :cod ";
            entra = true;

        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());

        }

        try {
            em.getTransaction().begin();
            TypedQuery<Exemplar> q = em.createQuery(query + "order by e.titulo", Exemplar.class);
            q.setParameter("string", "%" + str.toLowerCase() + "%");
            if (entra) {
                q.setParameter("cod", cod);

            }
            // q.setParameter("ano", "%"+ Integer.parseInt(str) +"%");
            exemplares = q.getResultList();
            em.getTransaction().commit();
            //System.out.println("\n\n"+f.getCpf()+ "  "+ f.getNome()+"\n\n");
            return exemplares;

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return null;
    }

    public void delete(int id) throws Exception {
        EntityManager em = getEm();

        try {
            em.getTransaction().begin();
            Exemplar a = em.find(Exemplar.class, id);
            //System.out.println(a.get);

            em.remove(a);
            em.getTransaction().commit();

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
    }
    
  public EntityManager getEm() throws Exception {
        return ConnectionBD.getConnection().createEntityManager();
    }


}
