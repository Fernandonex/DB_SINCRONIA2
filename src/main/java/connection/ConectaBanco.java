package connection;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;



public class ConectaBanco {
	private static ConectaBanco instancia;
    private EntityManager em;

    private ConectaBanco() {
        em = Persistence.createEntityManagerFactory("DB_SERVICE").
                createEntityManager();
    }

    public synchronized static ConectaBanco getInstancia() {
        if (instancia == null) {
            instancia = new ConectaBanco();
        }
        return instancia;
    }

    public EntityManager getEm() {
        return em;
    }
    
}
