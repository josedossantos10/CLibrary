package Sigleton;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionBD {

    public static EntityManagerFactory factory = null;

    private ConnectionBD() {
    }

    public static synchronized EntityManagerFactory getConnection() throws Exception{
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("CULibraryPU");
        }
        return factory;
    }

}
