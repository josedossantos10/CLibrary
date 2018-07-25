package model;

import config.Data;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQuery(name = "Emprestimo.getAll", query = "select e from Emprestimo e")
public class Emprestimo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean status;

    @Temporal(TemporalType.DATE)
    private Date data_entrega;
    @Temporal(TemporalType.DATE)
    private Date data_realizacao;
    @Temporal(TemporalType.DATE)
    private Date data_estimada;

    @OneToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @OneToOne
    @JoinColumn(name = "id_exemplar")
    private Exemplar exemplar;

    @OneToOne
    @JoinColumn(name = "id_funcionario")
    private Funcionario funcionario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getData_entrega() {
        return data_entrega;
    }

    public void setData_entrega(Date data_entrega) {
        this.data_entrega = data_entrega;
    }

    public Date getData_realizacao() {
        return data_realizacao;
    }

    public void setData_realizacao(Date data_realizacao) {
        this.data_realizacao = data_realizacao;
    }

    public Date getData_estimada() {
        return data_estimada;
    }

    public void setData_estimada(Date data_estimada) {
        this.data_estimada = data_estimada;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public String toString() {
        if (data_entrega != null) {
            return "Número OP: " + id + ": " + exemplar.getTitulo() + "\n Data Realizada: " + Data.dataMedio.format(data_realizacao) + "\n Data Prevista para entrega: " + Data.dataMedio.format(data_estimada) + "\n Data da Entrega: " + Data.dataMedio.format(data_entrega) + "\n Funcionario: " + funcionario.getNome();
        } else {
            return "Número OP: " + id + ": " + exemplar.getTitulo() + "\n Data Realizada: " + Data.dataMedio.format(data_realizacao) + "\n Data Prevista para entrega: " + Data.dataMedio.format(data_estimada) + "\n Data da Entrega: NÃO ENTREGUE" + "\n Funcionario: " + funcionario.getNome();
        }
    }

}
