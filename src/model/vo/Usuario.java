package model.vo;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import model.DAO.DAOFactory;
import model.DAO.EnderecoDAO;

@Entity
@SequenceGenerator(name = "usuario", sequenceName = "usuario_seq", allocationSize = 1, initialValue = 1)
@Table(name = "usuario")
@NamedQuery(name = "Usuario.getAll", query = "select e from Usuario e")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario")
    private int id;
    @Column(length = 50, nullable = false)
    String nome;
    @Column(length = 8, nullable = false)
    private String senha;
    @Column(length = 11, nullable = false, unique = true)
    private String cpf;
    private boolean professor;
    private boolean status;
    @Column(length = 11)
    private String celular;
    @Column(length = 10, nullable = false)
    private int matricula;
    @Column(length = 50)
    private String email;
    @Column(length = 10, nullable = false)
    private String telefone;
    private double debitos;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Endereco endereco;

    public Usuario() {
    }

    public Usuario(String nome, String senha, String cpf, boolean professor, boolean status, String celular, int matricula, String email, String telefone, double debitos, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.cpf = cpf;
        this.professor = professor;
        this.status = status;
        this.celular = celular;
        this.matricula = matricula;
        this.email = email;
        this.telefone = telefone;
        this.debitos = debitos;
        this.endereco = endereco;
    }

    public boolean isProfessor() {
        return professor;
    }

    public void setProfessor(boolean professor) {
        this.professor = professor;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getDebitos() {
        return debitos;
    }

    public void setDebitos(double debitos) {
        this.debitos = debitos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Endereco getEndereco(int id) throws Exception {
        if (this.endereco != null) {
            System.out.println("ENTREI AQUI");
            return endereco;
        }
       System.out.println("nao ENTREI AQUI");
        DAOFactory factory = DAOFactory.getInstace();
        return factory.getEnderecoDAO().find(id);
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return nome; //To change body of generated methods, choose Tools | Templates.
    }

}
