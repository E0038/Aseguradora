package org.e38.m6.aseguradora.control;


import org.e38.m6.aseguradora.model.Client;
import org.e38.m6.aseguradora.model.IModelMarker;
import org.e38.m6.aseguradora.model.Polissa;
import org.e38.m6.aseguradora.model.Vehicle;

import java.util.List;

public interface IManagerControler {
    void login(String user, String password) throws UserNotFoundException, InvalidCredencialsException;

    void register(String username, String mail, String password) throws InvalidCredencialsException;


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

    Client findByClientName(String name);

    Polissa findByPolisas(Client client);

    List<Polissa> polissafindVigents();
//end Percistante wrappers

}
