package org.e38.m6.aseguradora.control;

/**
 Crear un controlador específic per a gestionar cada entitat.

 Desenvolupar la part  de la vista que es descriu a continuació:
 Usuari: registre i validació per entrar a l'aplicació.
 Vehicles: inserir, modificar i cerca per matrícula
 Clients: inserir, eliminació i cerca per nom
 Pòlisses: inserir, modificar,  cerques de pòlisses d'un client, d'un vehicle i vigents.
 */
public class CommonControler implements IManagerControler {
    // TODO: 4/7/16 all
    @Override
    public void login(String user, String password) throws UserNotFoundException, InvalidCredencialsException {
    }

    @Override
    public void register(String username, String mail, String password) throws InvalidCredencialsException {

    }

}
