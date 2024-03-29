package model.nativeQueries;

import Sigleton.ConnectionBD;
import javax.persistence.EntityManager;

public class StoredProcedure {

    public void cadastrarFuncionario(String cpf, int matricula, String nome, String senha) throws Exception {
        EntityManager em = getEm();

        try {
            em.getTransaction().begin();
            em.createNativeQuery("SELECT public.cadastrar_funcionario('" + cpf + "'," + matricula + ",'" + nome + "','" + senha + "');").getSingleResult();
            em.getTransaction().commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void suspenderAluno() throws Exception {
        EntityManager em = getEm();

        try {
            em.getTransaction().begin();
            em.createNativeQuery("SELECT public.suspender_aluno();").getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void expirarReservas() throws Exception {
        EntityManager em = getEm();

        try {
            em.getTransaction().begin();
            em.createNativeQuery("SELECT public.expirar_reservas();").getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void suspenderProfessor() throws Exception {
        EntityManager em = getEm();

        try {
            em.getTransaction().begin();
            em.createNativeQuery("SELECT public.suspender_professor();").getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public int validarFuncionario(String login, String senha) throws Exception {
        EntityManager em = getEm();
        int var = 0;

        try {
            em.getTransaction().begin();
            var = (int) em.createNativeQuery("SELECT public.validar_funcionario('" + login + "','" + senha + "');").getSingleResult();

            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
        return var;
    }

    public EntityManager getEm() throws Exception {
        return ConnectionBD.getConnection().createEntityManager();
    }

}
