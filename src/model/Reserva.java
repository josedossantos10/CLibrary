package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
//@SequenceGenerator(name = "reserva", sequenceName = "reserva_seq", allocationSize = 1, initialValue = 1)
@Table(name = "reserva")
@NamedQuery(name = "Reserva.getAll", query = "select e from Reserva e")
public class Reserva implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean status;
    @Temporal(TemporalType.DATE)
    private Date data_emprestimo;
    @Temporal(TemporalType.DATE)
    private Date data_realizacao;
    
    @OneToOne
    private Usuario usuario;
    
    @OneToOne
    private Exemplar exemplar;

    public Reserva() {
    }

    public Reserva(Date data_emprestimo, Date data_realizacao) {
        this.data_emprestimo = data_emprestimo;
        this.data_realizacao = data_realizacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData_emprestimo() {
        return data_emprestimo;
    }

    public void setData_emprestimo(Date data_emprestimo) {
        this.data_emprestimo = data_emprestimo;
    }

    public Date getData_realizacao() {
        return data_realizacao;
    }

    public void setData_realizacao(Date data_realizacao) {
        this.data_realizacao = data_realizacao;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public boolean isStatus() {
        return status;
    }
    
   

}
