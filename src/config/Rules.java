package config;

public class Rules {

    private String sytemName = "Bem-vindo ao Sistema de Gerenciamento da Biblioteca da Califonia University";

    private int diasEmprestimoProfessor = 7;
    private int diasEmprestimoAluno = 7;

    private Long limiteEmprestimoProfessor = 5l;
    private Long limiteEmprestimoAluno = 3l;

    private double valorMulta = 1;
    private int diasParaSuspencao = 30;

    public Rules() {
    }

    
    public int getDiasEmprestimoProfessor() {
        return diasEmprestimoProfessor;
    }

    public int getDiasEmprestimoAluno() {
        return diasEmprestimoAluno;
    }

    public String getSytemName() {
        return sytemName;
    }

    public void setSytemName(String sytemName) {
        this.sytemName = sytemName;
    }

    public void setDiasEmprestimoProfessor(int diasEmprestimoProfessor) {
        this.diasEmprestimoProfessor = diasEmprestimoProfessor;
    }

    public void setDiasEmprestimoAluno(int diasEmprestimoAluno) {
        this.diasEmprestimoAluno = diasEmprestimoAluno;
    }

    public Long getLimiteEmprestimoProfessor() {
        return limiteEmprestimoProfessor;
    }

    public void setLimiteEmprestimoProfessor(Long limiteEmprestimoProfessor) {
        this.limiteEmprestimoProfessor = limiteEmprestimoProfessor;
    }

    public Long getLimiteEmprestimoAluno() {
        return limiteEmprestimoAluno;
    }

    public void setLimiteEmprestimoAluno(Long limiteEmprestimoAluno) {
        this.limiteEmprestimoAluno = limiteEmprestimoAluno;
    }

    public double getValorMulta() {
        return valorMulta;
    }

    public void setValorMulta(double valorMulta) {
        this.valorMulta = valorMulta;
    }

    public int getDiasParaSuspencao() {
        return diasParaSuspencao;
    }

    public void setDiasParaSuspencao(int diasParaSuspencao) {
        this.diasParaSuspencao = diasParaSuspencao;
    }



}
