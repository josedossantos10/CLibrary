package model.vo;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "aluno")
public class Aluno extends Usuario {

 
    
    public Aluno() {
    }
 

    @Override
    public String toString() {
        return nome;
    }

}
