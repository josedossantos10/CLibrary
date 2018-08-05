package model.DAO;

import model.DAO.postgreSQL.*;

public class DAOFactoryPostgreSQL extends DAOFactory {

    @Override
    public AlunoDAO getAlunoDAO() {
        return new PostgreSQLAlunoDAO();
    }

    @Override
    public AutorDAO getAutorDAO() {
        return new PostgreSQLAutorDAO();
    }

    @Override
    public CaixaDAO getCaixaDAOO() {
        return new PostgreSQLCaixaDAO();

    }

    @Override
    public CursoDAO getCursoDAO() {
        return new PostgreSQLCursoDAO();
    }

    @Override
    public DepartamentoDAO getDepartamentoDAO() {
        return new PostgreSQLDepartamentoDAO();
    }

    @Override
    public EmprestimoDAO getEmprestimoDAO() {
        return new PostgreSQLEmprestimoDAO();
    }

    @Override
    public EnderecoDAO getEnderecoDAO() {
        return new PostgreSQLEnderecoDAO();
    }

    @Override
    public ExemplarDAO getExemplarDAO() {
        return new PostgreSQLExemplarDAO();
    }

    @Override
    public FuncionarioDAO getFuncionarioDAO() {
        return new PostgreSQLFuncionarioDAO();
    }

    @Override
    public ProfessorDAO getProfessorDAO() {
        return new PostgreSQLProfessorDAO();
    }

    @Override
    public ReservaDAO getReservaDAO() {
        return new PostgreSQLReservaDAO();
    }

    @Override
    public UsuarioDAO getUsuarioDAO() {
        return new PostgreSQLUsuarioDAO();
    }

}
