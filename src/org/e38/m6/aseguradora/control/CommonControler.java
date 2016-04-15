package org.e38.m6.aseguradora.control;

import org.e38.m6.aseguradora.model.*;
import org.e38.m6.aseguradora.persistance.DbManager;

import javax.persistence.PersistenceException;
import java.util.List;

import static org.e38.m6.aseguradora.control.SqlInjectorChecker.checkInputStrings;

/**
 * Crear un controlador específic per a gestionar cada entitat.
 * <p>
 * Desenvolupar la part  de la vista que es descriu a continuació:
 * Usuari: registre i validació per entrar a l'aplicació.
 * Vehicles: inserir, modificar i cerca per matrícula
 * Clients: inserir, eliminació i cerca per nom
 * Pòlisses: inserir, modificar,  cerques de pòlisses d'un client, d'un vehicle i vigents.
 */
public class CommonControler implements IManagerControler {
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final String MAIL_REG = "[\\w.]+[@][\\w.]+[.][\\w]{2,10}";
    // TODO: 4/7/16 all
    private DbManager dbManager = DbManager.getInstance();

    public DbManager getDbManager() {
        return dbManager;
    }

    @Override
    public void login(String user, String password) throws UserNotFoundException, InvalidCredencialsException {
        if (!checkInputStrings(user, password))
            throw new InvalidCredencialsException("the input contains invalid words");
        password = encript(password);
        Usuari usuari = new Usuari().setName(user).setPassword(password);
        Usuari dbUser = dbManager.getEntityManager().find(Usuari.class, user);
        if (dbUser == null) throw new UserNotFoundException("usario no encontrado");
        else if (!usuari.getPassword().equals(dbUser.getPassword()))
            throw new InvalidCredencialsException("password not match");
    }

    private String encript(String password) {
        return password;
    }

    @Override
    public void register(String username, String mail, String password) throws InvalidCredencialsException, PersistenceException {
        if (!checkInputStrings(username, password))
            throw new InvalidCredencialsException("the input contains invalid words");
        if (password.length() < MIN_PASSWORD_LENGTH || !mail.matches(MAIL_REG)) {
            throw new InvalidCredencialsException("data rules violation");
        } else if (dbManager.getEntityManager()
                .createQuery("select U from Usuari U WHERE U.name = ?1", Usuari.class)
                .setParameter(1, username).getResultList().size()
                > 0) {
            throw new InvalidCredencialsException("user exist");
        }
        dbManager.getEntityManager().persist(new Usuari().setName(username).setPassword(password));

    }

    @Override
    public boolean insert(IModelMarker obj) {
        try {
            dbManager.insert(obj);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(IModelMarker obj) {
        try {
            dbManager.delete(obj);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(IModelMarker obj) {
        try {
            dbManager.update(obj);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Vehicle findByVeicleMatricula(String matricula) {
        return dbManager.getEntityManager().createNamedQuery(Vehicle.VEICLE_BY_MATRICULA, Vehicle.class)
                .setParameter("matricula", matricula)
                .getSingleResult();
    }

    @Override
    public List<Client> findByClientName(String name) {
        return dbManager.getEntityManager().createNamedQuery(Client.FIND_CLIENT_BY_NAME, Client.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public Polissa findByPolisas(Client client) {
        return dbManager.getEntityManager().createNamedQuery(Polissa.POLISSE_BY_CLIENT, Polissa.class)
                .setParameter("client", client)
                .getSingleResult();
    }

    @Override
    public List<Polissa> polissafindVigents() {
        return dbManager.getEntityManager()
                .createNamedQuery(Polissa.POLISSA_VIGENTS, Polissa.class)
                .getResultList();
    }

}
