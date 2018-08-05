package model.vo;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name = "curso", sequenceName = "curso_seq", allocationSize = 1, initialValue = 1)
@Table(name = "curso")
@NamedQuery(name = "Curso.getAll", query = "select e from Curso e")
public class Curso implements Serializable {

    @Id
    @GeneratedValue(generator = "curso", strategy = GenerationType.SEQUENCE)
    private int id;
    
    @Column(length = 50, nullable = false)
    private String nome;
    
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "departamento_id")
    private Departamento curso_departamento;

    public Curso() {
    }

    public Curso(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Departamento getCurso_departamento() {
        return curso_departamento;
    }

    public void setCurso_departamento(Departamento curso_departamento) {
        this.curso_departamento = curso_departamento;
    }
    

}
