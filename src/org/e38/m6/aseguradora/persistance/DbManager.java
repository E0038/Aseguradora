package org.e38.m6.aseguradora.persistance;

import org.e38.m6.aseguradora.model.IModelMarker;

import javax.persistence.*;


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

    private DbManager() {
        managerFactory = Persistence.createEntityManagerFactory(persistenceUnit());// FIXME: 4/7/16 add method to determine the Persistance unit
        entityManager = managerFactory.createEntityManager();
    }

    private String persistenceUnit() {
        return checkExternalAcces() ? "oracle-external" : "oracle-local";
    }

    private boolean checkExternalAcces() {
        return false;// TODO: 4/7/16
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
    }

    public void insert(IModelMarker obj) throws PersistanceExeception {
        entityGuard(obj);
        try {
            entityManager.persist(obj);
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
            entityManager.merge(obj);
        } catch (Exception e) {
            throw new PersistanceExeception(e.getMessage());
        }
    }


    public void delete(IModelMarker obj) throws PersistanceExeception {
        entityGuard(obj);
        try {
            entityManager.remove(obj);
        } catch (Exception e) {
            throw new PersistanceExeception(e.getMessage());
        }
    }

}
