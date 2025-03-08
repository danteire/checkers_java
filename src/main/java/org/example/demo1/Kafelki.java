package org.example.demo1;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Kafelki extends Rectangle {

    private Pionek pionek;

    public boolean hasPiece(){
        return pionek != null;
    }
    public Pionek getPionek(){
        return pionek;
    }
    public void setPionek(Pionek pionek){
        this.pionek = pionek;
    }

    public Kafelki(boolean light, int x,int y) {
        setWidth(checkersApp.ROZMIAR_KAWFELKA);
        setHeight(checkersApp.ROZMIAR_KAWFELKA);

        relocate(x*checkersApp.ROZMIAR_KAWFELKA,y*checkersApp.ROZMIAR_KAWFELKA);
    setFill(light ? Color.valueOf("#feb") : Color.valueOf("#582"));
    }
}
