package model.vo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name = "funcionario", sequenceName = "funcionario_seq", allocationSize = 1, initialValue = 1)
@Table(name = "funcionario")
@NamedQuery(name = "Funcionario.getAll", query = "select e from Funcionario e")
public class Funcionario implements Serializable {

    @Id
    @GeneratedValue(generator = "funcionario", strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(length = 11, nullable = false, unique = true)
    private String cpf;
    @Column(length = 8, nullable = false)
    private String senha;
    @Column(length = 50, nullable = false)
    private String nome;
    @Column(unique = true, nullable = false)
    private int matricula;

    public Funcionario() {
    }

    public Funcionario(String cpf, String senha, String nome, int matricula) {
        this.cpf = cpf;
        this.senha = senha;
        this.nome = nome;
        this.matricula = matricula;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

}
