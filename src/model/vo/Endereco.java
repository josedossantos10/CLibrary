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
@SequenceGenerator(name = "endereco", sequenceName = "usuario_endereco_seq", allocationSize = 1, initialValue = 1)
@Table(name = "endereco")
@NamedQuery(name = "Endereco.getAll", query = "select e from Endereco e")

public class Endereco implements Serializable {

    @Id
    @GeneratedValue(generator = "endereco", strategy = GenerationType.SEQUENCE)
    private int id;
    @Column(length = 50, nullable = false)
    private String rua;
    private int numero;
    @Column(length = 50, nullable = false)
    private String bairro;
    @Column(length = 50, nullable = false)
    private String cidade;
    @Column(length = 20)
    private String estado;

    public Endereco() {
    }

    public Endereco(String rua, int numero, String bairro, String cidade, String estado) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
