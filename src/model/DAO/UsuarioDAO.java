package model.DAO;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Usuario;

public class UsuarioDAO {

    public void salvar(Usuario a) {
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
            System.out.println("Erro ao salvar " + e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

    }

    public List<Usuario> listarTodos() {
        EntityManager em = getEm();
        List<Usuario> usuarios;

        try {
            em.getTransaction().begin();
            Query q = em.createNamedQuery("Usuario.getAll");
            usuarios = q.getResultList();

            em.getTransaction().commit();

        } catch (Exception e) {
            usuarios = new ArrayList<>();
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return usuarios;
    }

    public Usuario buscarPorID(int id) {
        EntityManager em = getEm();
        Usuario usuario;

        try {
            em.getTransaction().begin();
            usuario = em.find(Usuario.class, id);
//            usuario = (Usuario) q.getSingleResult();

            em.getTransaction().commit();

        } catch (Exception e) {
            usuario = null;
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return usuario;
    }

    public Usuario buscarPorCpf(String cpf) {
        EntityManager em = getEm();
        Usuario f;

        try {
            em.getTransaction().begin();
            TypedQuery<Usuario> q = em.createQuery("select u from Usuario u where u.cpf = :cpf", Usuario.class);
            q.setParameter("cpf", cpf);
            f = q.getSingleResult();
            em.getTransaction().commit();
            //System.out.println("\n\n"+f.getCpf()+ "  "+ f.getNome()+"\n\n");
            return f;

        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
        return null;
    }

    public void delete(int id) {
        EntityManager em = getEm();

        try {
            em.getTransaction().begin();
            Usuario a = em.find(Usuario.class, id);
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

    public List<Usuario> busca(String str) {
        EntityManager em = getEm();
        List<Usuario> users;

        try {
            em.getTransaction().begin();
            TypedQuery<Usuario> q = em.createQuery("select e from Usuario e where LOWER(e.nome) like :string or e.cpf like :cpf order by e.nome", Usuario.class);
            q.setParameter("string", "%" + str.toLowerCase() + "%");
            q.setParameter("cpf", str + "%");

            users = q.getResultList();
            em.getTransaction().commit();

            return users;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return null;
    }

    public EntityManager getEm() {
        return ConnectionBD.getConnection().createEntityManager();
    }

}
