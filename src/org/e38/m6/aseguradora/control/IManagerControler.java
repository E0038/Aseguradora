package org.e38.m6.aseguradora.control;


public interface IManagerControler {
    void login(String user, String password) throws UserNotFoundException, InvalidCredencialsException;

    void register(String username, String mail, String password) throws InvalidCredencialsException;

    // TODO: 4/7/16 add application logic
}
