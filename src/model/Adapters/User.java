package model.Adapters;

import model.vo.*;
import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String nome;
    private String senha;
    private String cpf;
    private boolean professor;
    private boolean status;
    private String celular;
    private int matricula;
    private String email;
    private String telefone;
    private double debitos;
    private Endereco endereco;

    public User(Usuario u) {
    }

    public User(Aluno u) {
    }

    public User(Professor u) {
    }

}
