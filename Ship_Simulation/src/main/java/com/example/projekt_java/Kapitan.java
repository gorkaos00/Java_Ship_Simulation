package com.example.projekt_java;

import javafx.animation.PathTransition;

import javafx.application.Platform;

import javafx.scene.image.ImageView;

import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

import javafx.util.Duration;



public class Kapitan extends Thread {
    Statek statek;
    ImageView stateczek;

    public Kapitan(Statek statek, ImageView stateczek) {
        this.statek = statek;
        this.stateczek=stateczek;

    }

    public void run() {

        try {
            sleep(statek.czas_odplywu);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("obudzilem sie");
        try {
            statek.sem_czas_sie_skonczyl.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (statek.liczba_os_na_mostku == 0) {
                Path sciezka = new Path();
                MoveTo pozycja = new MoveTo();//
                pozycja.setX(310);
                pozycja.setY(143);
                LineTo linia = new LineTo();
                linia.setX(1200);
                linia.setY(300);
                sciezka.getElements().addAll(pozycja, linia);
                PathTransition sciezka2 = new PathTransition(Duration.millis(statek.czas_animacji), sciezka, stateczek);
                sciezka2.setOnFinished(e->{
                            synchronized (this){
                                notifyAll();
                            }
                        }

                );
                Platform.runLater(() -> {
                    sciezka2.play();
                });
                synchronized (this){
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Path powrot_statku_droga = new Path();
                MoveTo punkt_p = new MoveTo();//
                pozycja.setX(-1200);
                pozycja.setY(300);
                LineTo punkt_k = new LineTo();
                linia.setX(310);
                linia.setY(143);
                powrot_statku_droga.getElements().addAll(pozycja, linia);
                PathTransition powrot_statku = new PathTransition(Duration.millis(statek.czas_animacji), powrot_statku_droga, stateczek);
                powrot_statku.setOnFinished(e->{
                            synchronized (this){
                                notifyAll();
                            }
                        }

                );
                Platform.runLater(() -> {
                    powrot_statku.play();
                });
                synchronized (this){
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                System.out.println("Kapiten sprawdza mostek. Liczba osob na mostku to: " + statek.liczba_os_na_mostku);
                System.out.println("Statek odplywa");
                break;
            }
        }
    }

}
