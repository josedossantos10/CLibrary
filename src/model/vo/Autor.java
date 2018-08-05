package model.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "autor", sequenceName = "autor_seq", allocationSize = 1, initialValue = 1)
@NamedQuery(name = "Autor.getAll", query = "select e from Autor e")

public class Autor implements Serializable {

    @Id
    @GeneratedValue(generator = "autor", strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(length =50,nullable = false)
    private String nome;
    @ManyToMany( mappedBy = "autores",cascade = CascadeType.MERGE)
    private List<Exemplar> exemplares;

    public Autor() {
    }

    public Autor(String nome) {
        this.nome = nome;
        exemplares=new ArrayList<>();
    }

    public List<Exemplar> getExemplares() {
        return exemplares;
    }

    public void setExemplares(List<Exemplar> exemplares) {
        this.exemplares = exemplares;
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

}
