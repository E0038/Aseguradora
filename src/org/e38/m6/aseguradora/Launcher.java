/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.e38.m6.aseguradora;


import org.e38.m6.aseguradora.view.fx.ManagerApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author sergi
 */
public class Launcher {

    public static final String helpMesage = "usage:\njava -jar [jarfile]" +
            "\n\t mode specific args:" +
            "\n\t\t-p port of web mode Optional" +
            "\n\t\t-fx fx launch extra args Optional";

    private static final String ARG_PORT = "-p";
    private static final String ARG_FX = "-fx";
    private Consumer laucher;

    /**
     * @param args the command line arguments
     *             -p Port only in WEB mode
     *             -fx fx launch extra args
     */
    public static void main(String[] args) {
        new Launcher().configureAndLaunch(args);
    }

    private void configureAndLaunch(String[] args) {
        List<String> argsv = new ArrayList<>(Arrays.asList(args));
        parserArgs(argsv);
    }

    private void parserArgs(List<String> argsv) {
        boolean lauched = false;
        for (int i = 0; i < argsv.size(); i++) {
            switch (argsv.get(i)) {
                case ARG_FX:
                    lauched = true;
                    List<String> extras = argsv.subList(i, argsv.size());
                    ManagerApplication.launch(extras.toArray(new String[argsv.size()]));
                    break;
                case ARG_PORT:
                    lauched = true;
                    String port = null;
                    if (i + 1 < argsv.size()) {
                        port = argsv.get(i + 1);
                    }
                    throw new RuntimeException("not implemented");
                    // TODO: 4/7/16 web mode laucher
                default:
                    break;
            }
        }
        if (!lauched) ManagerApplication.launch();
    }
}
