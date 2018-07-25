package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "exemplar", sequenceName = "exemplar_seq", allocationSize = 1, initialValue = 1)
@NamedQuery(name = "Exemplar.getAll", query = "select e from Exemplar e order by e.titulo")
public class Exemplar implements Serializable {

//    public Emprestimo getEmprestimo() {
//        return emprestimo;
//    }
//
//    public void setEmprestimo(Emprestimo emprestimo) {
//        this.emprestimo = emprestimo;
//    }

    @Id
    @GeneratedValue(generator = "exemplar", strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(unique = true, nullable = false)
    private int codigo;
    private int ano;
    @Column(length = 50, nullable = false)
    private String titulo;
    @Column(length = 50)
    private String editora;
    private int edicao;
    @Column(nullable = false)
    private int quantidade;
    private int quantidade_disponivel;
    
    @ManyToMany(cascade ={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinTable(name = "exemplar_autor",joinColumns =@JoinColumn(name = "exemplar_id"), inverseJoinColumns = @JoinColumn(name = "autores_id"))
    private List<Autor> autores;   

    public Exemplar() {
    }

    public Exemplar(int codigo, int ano, String titulo, String editora, int edicao, int quantidade) {
        this.codigo = codigo;
        this.ano = ano;
        this.titulo = titulo;
        this.editora = editora;
        this.edicao = edicao;
        this.quantidade = quantidade;
        this.quantidade_disponivel = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getEdicao() {
        return edicao;
    }

    public void setEdicao(int edicao) {
        this.edicao = edicao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getQuantidade_disponivel() {
        return quantidade_disponivel;
    }

    public void setQuantidade_disponivel(int quantidade_disponivel) {
        this.quantidade_disponivel = quantidade_disponivel;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    @Override
    public String toString() {
        return titulo; //To change body of generated methods, choose Tools | Templates.
    }
    
}
