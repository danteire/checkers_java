package org.example.demo1;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static org.example.demo1.checkersApp.ROZMIAR_KAWFELKA;
public class Pionek extends StackPane {

    private TypPionka typ;
    public TypPionka getTyp() {
        return typ;
    }

    private double myszX, myszY;
    private double stareX, stareY;

    public double getStareX() {
        return stareX;
    }

    public double getStareY() {
        return stareY;
    }

    public Pionek(TypPionka typPionka, int x, int y) {
        this.typ = typPionka;

        move(x , y );
        Ellipse background = new Ellipse(ROZMIAR_KAWFELKA * 0.3125,ROZMIAR_KAWFELKA * 0.26);
        background.setFill(Color.BLACK);
        background.setStroke(Color.BLACK);
        background.setStrokeWidth(ROZMIAR_KAWFELKA * 0.03);

        background.setTranslateX((ROZMIAR_KAWFELKA - ROZMIAR_KAWFELKA * 0.3125 * 2) /2);
        background.setTranslateY((ROZMIAR_KAWFELKA - ROZMIAR_KAWFELKA * 0.26 * 2)/2 + ROZMIAR_KAWFELKA * 0.07);


        Ellipse ellipse = new Ellipse(ROZMIAR_KAWFELKA * 0.3125,ROZMIAR_KAWFELKA * 0.26);
        ellipse.setFill(typPionka == TypPionka.RED ? Color.valueOf("#c40003") : Color.WHITE);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(ROZMIAR_KAWFELKA * 0.03);

        ellipse.setTranslateX((ROZMIAR_KAWFELKA - ROZMIAR_KAWFELKA * 0.3125 * 2) /2);
        ellipse.setTranslateY((ROZMIAR_KAWFELKA - ROZMIAR_KAWFELKA * 0.26 * 2)/2);

        getChildren().addAll(background,ellipse);

        setOnMousePressed(e ->{
            myszX = e.getSceneX();
            myszY = e.getSceneY();
        });
        setOnMouseDragged(e ->{
            relocate(e.getSceneX() - myszX + stareX, e.getSceneY() - myszY +stareY);
        });
    }

    public void move(int x, int y) {
        stareX = x * ROZMIAR_KAWFELKA;
        stareY = y * ROZMIAR_KAWFELKA;

        relocate(stareX, stareY);
    }

    public void porzucRuch(){
        relocate(stareX, stareY);
    }
}
