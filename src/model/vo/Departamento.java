package model.vo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name = "depto", sequenceName = "departamento_seq", allocationSize = 1, initialValue = 1)
@Table(name = "departamento")
@NamedQuery(name = "Departamento.getAll", query = "select e from Departamento e")
public class Departamento implements Serializable {

    @Id
    @GeneratedValue(generator = "depto", strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(length = 50, nullable = false)
    private String nome;
    
    
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "curso_departamento")
    private List<Curso> cursos;

    @OneToMany(cascade = CascadeType.MERGE,mappedBy = "professor_departamento")
    private List<Professor> professores;

    public Departamento() {
    }

    public List<Professor> getProfessores() {
        return professores;
    }
    
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    @Override
    public String toString() {
        return this.getNome();
        
    }
    
    

}
