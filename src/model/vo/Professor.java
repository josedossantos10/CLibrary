package model.vo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "professor")
@NamedQuery(name = "Professor.getAll", query = "select e from Professor e")
public class Professor extends Usuario {

    
    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamento professor_departamento;

    public Professor() {
    }

    public Departamento getProfessor_departamento() {
        return professor_departamento;
    }

    public void setProfessor_departamento(Departamento professor_departamento) {
        this.professor_departamento = professor_departamento;
    }
    
    @Override
    public String toString() {
        return nome;
    }

}
