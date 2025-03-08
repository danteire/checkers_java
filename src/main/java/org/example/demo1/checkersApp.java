package org.example.demo1;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;




public class checkersApp extends Application {

    public static final int ROZMIAR_KAWFELKA = 100;
    public static final int szerokosc = 8;
    public static final int wysokosc = 8;


    public static TypPionka obecnyRuch = TypPionka.RED;

    private Label zegarGracz1;
    private Label zegarGracz2;
    private zegar zegarGracz11;
    private zegar zegarGracz22;

    private Kafelki[][] plansza = new Kafelki[szerokosc][wysokosc];

    private Group grupaKafelkow = new Group();
    private Group grupaPionkow = new Group();



    private Parent createContent() {

        Pane planszaRoot = new Pane();
        planszaRoot.setPrefSize(szerokosc * ROZMIAR_KAWFELKA + 200, wysokosc * ROZMIAR_KAWFELKA);

        zegarGracz1 = new Label();
        zegarGracz2 = new Label();

        zegarGracz11 = new zegar(zegarGracz1, 60); // np. 60 sekund na gracza
        zegarGracz22 = new zegar(zegarGracz2, 60); // np. 60 sekund na gracza

        zegarGracz1.relocate(szerokosc * ROZMIAR_KAWFELKA + 20, 20);
        zegarGracz2.relocate(szerokosc * ROZMIAR_KAWFELKA + 20, 60);

        planszaRoot.getChildren().addAll(grupaKafelkow, grupaPionkow, zegarGracz1, zegarGracz2);


        for (int y = 0; y < wysokosc; y++) {
            for (int x = 0; x < szerokosc; x++) {
                Kafelki kafelki = new Kafelki((x+y)%2==0,x,y);
                plansza[x][y] = kafelki;
                grupaKafelkow.getChildren().add(kafelki);

                Pionek pionek = null;

                if(y<=2 && (x+y)%2!=0){
                    pionek = makePionek(TypPionka.RED,x,y);
                }
                if(y>=5 && (x+y)%2!=0){
                    pionek = makePionek(TypPionka.WHITE,x,y);
                }
                if(pionek!=null){
                    kafelki.setPionek(pionek);
                    grupaPionkow.getChildren().add(pionek);
                }

            }
        }

        zegarGracz11.start();

        return planszaRoot;
    }

    private WynikRuchu tryRuch(Pionek pionek, int nowyX, int nowyY) {
        if(plansza[nowyX][nowyY].hasPiece() || (nowyX+nowyY)%2==0){
            return new WynikRuchu(TypRuchu.NONE);
        }

        int x0 = naPlansze(pionek.getStareX());
        int y0 = naPlansze(pionek.getStareY());

        if(pionek.getTyp() != obecnyRuch){
            return new WynikRuchu(TypRuchu.NONE);
        }
        if(Math.abs(nowyX-x0) == 1 && nowyY - y0 == pionek.getTyp().kierunekRuchu){
            return new WynikRuchu(TypRuchu.NORMAL);
        } else if (Math.abs(nowyX-x0) == 2 && nowyY - y0 == pionek.getTyp().kierunekRuchu * 2) {
            int x1 = x0 + (nowyX-x0)/2;
            int y1 = y0 + (nowyY-y0)/2;
            if(plansza[x1][y1].hasPiece() && plansza[x1][y1].getPionek().getTyp() != pionek.getTyp()){
                return new WynikRuchu(TypRuchu.KILL,plansza[x1][y1].getPionek());
            }
        }
        return new WynikRuchu(TypRuchu.NONE);
    }

    private int naPlansze(double pixel){
        return (int)(pixel + ROZMIAR_KAWFELKA/2)/ROZMIAR_KAWFELKA;
    }
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent());
        stage.setTitle("Warcaby");
        stage.setScene(scene);
        stage.show();
    }
    private Pionek makePionek(TypPionka typPionka, int x, int y) {
        Pionek pionek = new Pionek(typPionka, x, y);

        pionek.setOnMouseReleased(e->{

            int nowyX = naPlansze(pionek.getLayoutX());
            int nowyY = naPlansze(pionek.getLayoutY());

            WynikRuchu wynikRuchu = tryRuch(pionek, nowyX, nowyY);

            int x0 = naPlansze(pionek.getStareX());
            int y0 = naPlansze(pionek.getStareY());
            switch (wynikRuchu.getTyp()){
                case NONE:
                    pionek.porzucRuch();
                    break;
                case KILL:
                    pionek.move(nowyX, nowyY);
                    plansza[x0][y0].setPionek(null);
                    plansza[nowyX][nowyY].setPionek(pionek);

                    if (pionek.getTyp() == TypPionka.RED) {
                        obecnyRuch = TypPionka.WHITE;
//                        zegarGracz11.stopTimer();
//                        zegarGracz22.start();
                    } else {
                        obecnyRuch = TypPionka.RED;
//                        zegarGracz22.stopTimer();
//                        zegarGracz11.start();
                    }

                    Pionek bityPionek = wynikRuchu.getPionek();
                    plansza[naPlansze(bityPionek.getStareX())][naPlansze(bityPionek.getStareY())].setPionek(null);
                    grupaPionkow.getChildren().remove(bityPionek);

                    boolean przeciwnikZbity = grupaPionkow.getChildren().stream()
                            .map(node -> (Pionek) node)
                            .noneMatch(p -> p.getTyp() == bityPionek.getTyp());

                    if (przeciwnikZbity) {
                        //Platform.exit();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Koniec Gry");
                        alert.setHeaderText(null);
                        alert.setContentText(pionek.getTyp() + " wygrywa!");

                        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), ae -> Platform.exit()));
                        timeline.play();

                        alert.show();

                    }
                    break;
                case NORMAL:
                    pionek.move(nowyX, nowyY);
                    plansza[x0][y0].setPionek(null);
                    plansza[nowyX][nowyY].setPionek(pionek);
                    if (pionek.getTyp() == TypPionka.RED) {
                        obecnyRuch = TypPionka.WHITE;
//                        zegarGracz11.stopTimer();
//                        zegarGracz22.start();
                    } else {
                        obecnyRuch = TypPionka.RED;
//                        zegarGracz22.stopTimer();
//                        zegarGracz11.start();
                    }
                    break;
            }
        });

        return pionek;
    }
    public static void main(String[] args) {launch(args);}
}
