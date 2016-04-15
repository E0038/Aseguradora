package org.e38.m6.aseguradora.persistance;

import org.e38.m6.aseguradora.model.IModelMarker;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;


/**
 * PART 2:  Gestió de la persistència d'entitats amb JPA
 * <p>
 * El projecte ha de realitzar la persistència de totes les entitats anteriorment mapades. Per a la seva implementació feu servir: Ús d'excepcions, i ús de NamedQueries i Querys. En la gestió s'haurà d'incloure:
 * Inserció, modificació, eliminació i cerca de cadascuna de les entitats.
 * Cerca de clients per nom.
 * Cerca de pòlisses d'un client.
 * Cerca de pòlissa d'un vehicle.
 * Cerca de pòlisses vigents.
 */
@PersistenceContext
public class DbManager {
    public static final int EXTERNAL_PORT = 8081, LOCAL_PORT = 1521;
    private static DbManager ourInstance = new DbManager();
    private final EntityManagerFactory managerFactory;
    private final EntityManager entityManager;
    private final String CONEC_STRING_LOCAL = "jdbc:oracle:thin:@192.168.180.10:1521:BD1314",
            CONEC_STRING_EXTERNAL = "jdbc:oracle:thin:@ieslaferreria.xtec.cat:8081:BD1314";
    @SuppressWarnings("DuplicateStringLiteralInspection")
    private String user = "SAZNAR", password = "SAZNAR";

    private DbManager() {
        managerFactory = Persistence.createEntityManagerFactory(persistenceUnit());
        entityManager = managerFactory.createEntityManager();
    }

    private String persistenceUnit() {
        return checkExternalAcces() ? "oracle-external" : "oracle-local";
    }

    private boolean checkExternalAcces() {
//        return false;
        try {
            //noinspection UseOfJDBCDriverClass
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            Connection c = DriverManager.getConnection(CONEC_STRING_EXTERNAL, user, password);
            c.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static DbManager getInstance() {
        return ourInstance;
    }

    private boolean checkLocalAcces() {
        return true;// TODO: 4/7/16
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void prepareEnv() {
//        entityManager.setProperty();//todo set mode drop-create
//        // TODO: 4/11/16 create mock instances
//        entityManager.setProperty();// TODO: 4/11/16 set mode update
    }

    public void insert(IModelMarker obj) throws PersistanceExeception {
        entityGuard(obj);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new PersistanceExeception(e.getMessage());
        }
    }

    private void entityGuard(IModelMarker obj) throws NoSuchEntityExeception {
        if (!obj.getClass().isAnnotationPresent(Entity.class))
            throw new NoSuchEntityExeception("not a Entity");
    }

    public void update(IModelMarker obj) throws PersistanceExeception {
        entityGuard(obj);
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(obj);
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            throw new PersistanceExeception(e.getMessage());
        }
    }

    public List<? extends IModelMarker> selectAll(Class<? extends IModelMarker> aClass) throws NoSuchEntityExeception {
        if (!aClass.isAnnotationPresent(Entity.class))
            throw new NoSuchEntityExeception("not a Entity");
        String name = aClass.getSimpleName();
        return entityManager.createQuery("SELECT p FROM " + name + " p ", aClass).getResultList();
    }

    public void delete(IModelMarker obj) throws PersistanceExeception {
        entityGuard(obj);
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(obj);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new PersistanceExeception(e.getMessage());
        }
    }

    public void reconfigure(Map<String, Object> propeties) {
        for (String key : propeties.keySet()) {
            entityManager.setProperty(key, propeties.get(key));
        }
    }
}
