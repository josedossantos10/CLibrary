package model.DAO.postgreSQL;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.DAO.UsuarioDAO;
import model.vo.Usuario;

public class PostgreSQLUsuarioDAO extends UsuarioDAO {

    public void salvar(Usuario a) throws Exception {
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

    public List<Usuario> listarTodos() throws Exception {
        EntityManager em = getEm();
        List<Usuario> usuarios;

        em.getTransaction().begin();
        Query q = em.createNamedQuery("Usuario.getAll");
        usuarios = q.getResultList();

        em.getTransaction().commit();

        em.close();
        return usuarios;
    }

    public Usuario buscarPorID(int id) throws Exception {
        EntityManager em = getEm();
        Usuario usuario;

        em.getTransaction().begin();
        usuario = em.find(Usuario.class, id);
        em.getTransaction().commit();

        em.close();
        return usuario;
    }

    public Usuario buscarPorCpf(String cpf) throws Exception {
        EntityManager em = getEm();
        Usuario f;
        em.getTransaction().begin();
        TypedQuery<Usuario> q = em.createQuery("select u from Usuario u where u.cpf = :cpf", Usuario.class);
        q.setParameter("cpf", cpf);
        f = q.getSingleResult();
        em.getTransaction().commit();
        em.close();
        return f;
    }

    public void delete(int id) throws Exception {
        EntityManager em = getEm();
        em.getTransaction().begin();
        Usuario a = em.find(Usuario.class, id);
        em.remove(a);
        em.getTransaction().commit();
        em.close();

    }

    public List<Usuario> busca(String str) throws Exception {
        EntityManager em = getEm();
        List<Usuario> users;

        em.getTransaction().begin();
        TypedQuery<Usuario> q = em.createQuery("select e from Usuario e where LOWER(e.nome) like :string or e.cpf like :cpf order by e.nome", Usuario.class);
        q.setParameter("string", "%" + str.toLowerCase() + "%");
        q.setParameter("cpf", str + "%");

        users = q.getResultList();
        em.getTransaction().commit();
        em.close();
        return users;
    }

    public EntityManager getEm() throws Exception {
        return ConnectionBD.getConnection().createEntityManager();
    }

}
