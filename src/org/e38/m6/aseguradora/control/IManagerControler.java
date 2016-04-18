package org.e38.m6.aseguradora.control;


import org.e38.m6.aseguradora.model.*;

import java.util.List;

public interface IManagerControler {
    Usuari login(String user, String password) throws UserNotFoundException, InvalidCredencialsException;

    Usuari register(String username, String mail, String password) throws InvalidCredencialsException;


    /**
     * Vehicles: inserir, modificar i cerca per matrícula
     * Clients: inserir, eliminació i cerca per nom
     * Pòlisses: inserir, modificar,  cerques de pòlisses d'un client, d'un vehicle i vigents.
     *
     * @param obj
     */

    //:Persistence wrappers

    /**
     * Persistence wrappers
     *
     * @param obj
     * @return
     */
    boolean insert(IModelMarker obj);

    /**
     * Persistence wrappers
     *
     * @param obj
     * @return
     */
    boolean delete(IModelMarker obj);

    /**
     * Persistence wrappers
     *
     * @param obj
     * @return
     */
    boolean update(IModelMarker obj);


    Vehicle findByVeicleMatricula(String matricula);

    List<Client> findByClientName(String name);

    Polissa findByPolisas(Client client);

    List<Polissa> polissafindVigents();
//end Percistante wrappers

}
