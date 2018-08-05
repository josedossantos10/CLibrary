package model.DAO;

import java.util.List;
import javax.persistence.EntityManager;
import model.vo.Reserva;

public abstract class ReservaDAO {

    public abstract void salvar(Reserva a) throws Exception;

    public abstract List<Reserva> listarTodos() throws Exception;

    public abstract List<Reserva> listarReservasAtivas() throws Exception;

    public abstract List<Reserva> buscarReservasPorId(int id) throws Exception;

    public abstract EntityManager getEm() throws Exception;
}
