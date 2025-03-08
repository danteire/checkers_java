package org.example.demo1;

import javafx.animation.AnimationTimer;

public class WynikRuchu {

    private TypRuchu typ;



    public TypRuchu getTyp() {
        return typ;
    }

    private Pionek pionek;
    public Pionek getPionek() {
        return pionek;
    }

    public WynikRuchu(TypRuchu typ){
        this(typ, null);
    }
    public WynikRuchu(TypRuchu typ, Pionek pionek) {
        this.typ = typ;
        this.pionek = pionek;
    }
}
