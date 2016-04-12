package org.e38.m6.aseguradora.view.fx;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by sergi on 4/12/16.
 */
public class MatchingProperty extends SimpleStringProperty {
    public BooleanProperty matches(String reg) {
        return new SimpleBooleanProperty(get().matches(reg));
    }

    public BooleanProperty contains(CharSequence sequence) {
        return new SimpleBooleanProperty(get().contains(sequence));
    }
}
