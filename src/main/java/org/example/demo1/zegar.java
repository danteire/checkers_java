package org.example.demo1;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class zegar extends Thread {

    private Label timerLabel;
    private int timeSeconds;

    public volatile boolean isRunning = true;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();


    public zegar(Label timerLabel, int startTime) {
        this.timerLabel = timerLabel;
        timeSeconds = startTime;
        updateLabel();
    }


    public void run() {
        while (timeSeconds > 0 && isRunning) {
//            synchronized (pauseLock) {
//                if(!isRunning) {
//                    break;
//                }
//                if(paused){
//                    try {
//                        pauseLock.wait();
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//                if(!isRunning) {
//                    break;
//                }
//            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timeSeconds--;
            updateLabel();
            if (timeSeconds <= 0) {
                Platform.runLater(() -> {
                    TypPionka przeciwnik = (checkersApp.obecnyRuch == TypPionka.RED) ? TypPionka.WHITE : TypPionka.RED;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Koniec Gry");
                    alert.setHeaderText(null);
                    alert.setContentText("Czas " + checkersApp.obecnyRuch + " się skończył! " + przeciwnik + " wygrywa!");
                    alert.showAndWait();
                    Platform.exit();
                });
            }
        }
    }

//    public void stopZegar(){
//        isRunning = false;
//        resumeZegar();
//    }
//    public void pause(){
//        paused = true;
//    }
//    public void resumeZegar(){
//        synchronized (pauseLock) {
//            paused = false;
//            pauseLock.notify();
//        }
//    }

    private void updateLabel() {
        timerLabel.setText("Czas: " + timeSeconds + " s");
    }
}