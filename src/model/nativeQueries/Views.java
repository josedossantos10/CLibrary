package model.nativeQueries;

import Sigleton.ConnectionBD;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import model.Emprestimo;
import model.Exemplar;
import model.Usuario;

public class Views {

    public List<Exemplar> listarExemplares() {
        EntityManager em = getEm();
        List<Exemplar> exemplares;
        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("SELECT * FROM livros2;", Exemplar.class);
            exemplares = q.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            exemplares = new ArrayList<>();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return exemplares;
    }

    public List<Usuario> listarUsuariosSuspendidos() {
        EntityManager em = getEm();
        List<Usuario> usuarios;

        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("SELECT * FROM public.suspendidos;", Usuario.class);
            usuarios = q.getResultList();
            System.out.println("\nArray Usuarios da view " + usuarios.size());
            em.getTransaction().commit();
            return usuarios.subList(0, usuarios.size() / 3);//por algum motivo o jpa esta triplicando os resultados da busca na view 

        } catch (Exception e) {
            System.out.println(e.getMessage());
            usuarios = new ArrayList<>();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return usuarios;
    }

    public List<Emprestimo> listarEmprestimosAtrasados() {
        EntityManager em = getEm();
        List<Emprestimo> emprestimos;

        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("SELECT * FROM public.atrasados;", Emprestimo.class);
            emprestimos = q.getResultList();
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            emprestimos = new ArrayList<>();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return emprestimos;
    }

    public List<Usuario> listarUsuarios() {
        EntityManager em = getEm();
        List<Usuario> usuarios;

        try {
            em.getTransaction().begin();
            Query q = em.createNativeQuery("SELECT * FROM public.users;", Usuario.class);
            usuarios = q.getResultList();
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            usuarios = new ArrayList<>();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return usuarios;
    }

    public EntityManager getEm() {
        return ConnectionBD.getConnection().createEntityManager();
    }

}
