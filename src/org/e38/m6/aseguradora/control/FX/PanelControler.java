package org.e38.m6.aseguradora.control.FX;

import javafx.event.ActionEvent;
import org.e38.m6.aseguradora.control.FxControler;

/**
 * Created by sergi on 4/11/16.
 */
public interface PanelControler {
    void eliminar(ActionEvent actionEvent);

    void search(ActionEvent actionEvent);

    void inserir(ActionEvent actionEvent);

    FxControler getFxControler();

    PanelControler setFxControler(FxControler fxControler);
}
