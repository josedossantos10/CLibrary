package model.Adapters;

import model.vo.*;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the cpf
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @param cpf the cpf to set
     */
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    /**
     * @return the professor
     */
    public boolean isProfessor() {
        return professor;
    }

    /**
     * @param professor the professor to set
     */
    public void setProfessor(boolean professor) {
        this.professor = professor;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the debitos
     */
    public double getDebitos() {
        return debitos;
    }

    /**
     * @param debitos the debitos to set
     */
    public void setDebitos(double debitos) {
        this.debitos = debitos;
    }

    /**
     * @return the rua
     */
    public String getRua() {
        return rua;
    }

    /**
     * @param rua the rua to set
     */
    public void setRua(String rua) {
        this.rua = rua;
    }

    private int id;
    private String nome;
    private String cpf;
    private boolean professor;
    private boolean status;
    private String email;
    private double debitos;
    private String rua;

    public User(Usuario u) {
        this.id = u.getId();
        this.nome = u.getNome();
        this.cpf = u.getCpf();
        this.professor = u.isProfessor();
        this.status = u.isStatus();
        this.email = u.getEmail();
        this.debitos = u.getDebitos();
       // this.rua = u.getEndereco().getRua() + ", " + u.getEndereco().getNumero();

    }

    public User(Aluno u) {
        this.id = u.getId();
        this.nome = u.getNome();
        this.cpf = u.getCpf();
        this.professor = u.isProfessor();
        this.status = u.isStatus();
        this.email = u.getEmail();
        this.debitos = u.getDebitos();
       // this.rua = u.getEndereco().getRua() + ", " + u.getEndereco().getNumero();
    }

    public User(Professor u) {
        this.id = u.getId();

        this.nome = u.getNome();
        this.cpf = u.getCpf();
        this.professor = u.isProfessor();
        this.status = u.isStatus();
        this.email = u.getEmail();
        this.debitos = u.getDebitos();
       // this.rua = u.getEndereco().getRua() + ", " + u.getEndereco().getNumero();
    }

    public static List<User> users(List<Usuario> usuarios) {
        List<User> users = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            users.add(new User(usuario));

        }

        return users;

    }

}
