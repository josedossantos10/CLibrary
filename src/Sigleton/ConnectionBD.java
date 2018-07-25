package Sigleton;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionBD {

    public static EntityManagerFactory factory = null;

    private ConnectionBD() {
    }

    public static EntityManagerFactory getConnection() {
        if (factory == null) {
            try {

                factory = Persistence.createEntityManagerFactory("CULibraryPU");
            } catch (Exception e) {
                System.out.println("Erro ao abrir o Factory: " + e.getMessage());

            }
        }
        return factory;
    }

}
