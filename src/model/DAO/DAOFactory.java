package model.DAO;

public abstract class DAOFactory {

    public static DAOFactory getInstace() {
        if (true) {
            return new DAOFactoryPostgreSQL();
        } else {
            return new DAOFactoryMySQL();
        }
    }

    public abstract AlunoDAO getAlunoDAO();

    public abstract AutorDAO getAutorDAO();

    public abstract CaixaDAO getCaixaDAOO();

    public abstract CursoDAO getCursoDAO();

    public abstract DepartamentoDAO getDepartamentoDAO();

    public abstract EmprestimoDAO getEmprestimoDAO();

    public abstract EnderecoDAO getEnderecoDAO();

    public abstract ExemplarDAO getExemplarDAO();

    public abstract FuncionarioDAO getFuncionarioDAO();

    public abstract ProfessorDAO getProfessorDAO();

    public abstract ReservaDAO getReservaDAO();

    public abstract UsuarioDAO getUsuarioDAO();

}
