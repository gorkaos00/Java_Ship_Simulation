package com.example.projekt_java;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.Random;

import static java.lang.Math.floor;

public class Pasazer extends Thread {

    Statek statek;
    Random random = new Random();

    int nr_pasazera;
    public Pasazer(int nr_pasazera, Statek statek){
        this.statek=statek;
        this.nr_pasazera = nr_pasazera;
    }
    public void run() {
        int x_b = nr_pasazera % 16;
        int y_b = 0;
        if (nr_pasazera > 15) {
            y_b++;
            if (nr_pasazera > 31) {
                y_b++;
            }
        }
        Circle kolko = new Circle();
        kolko.setCenterX(270 + 40 * x_b);
        kolko.setCenterY(20 + y_b * 40);
        kolko.setRadius(20);
        kolko.setFill(Color.YELLOW);
        Platform.runLater(() -> {
            HelloApplication.root.getChildren().add(kolko);
        });
        try {
            statek.sem_chron_statek.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            statek.sem_chron_mostek.acquire();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (statek.sem_czas_sie_skonczyl.availablePermits() == 0) {
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        statek.liczba_os_na_mostku++;
        statek.M = statek.M % statek.K;

        int x_m = statek.M % 2, y_m = statek.M / 2;


        statek.M++;
        System.out.println("Pasazer " + nr_pasazera + " znajduje się na mostku jako " + statek.liczba_os_na_mostku + " z " + statek.K);


        MoveTo poczatek_m = new MoveTo();
        poczatek_m.setX(kolko.getCenterX());
        poczatek_m.setY(kolko.getCenterY());
        LineTo koniec_m = new LineTo();
        int x, y;
        koniec_m.setX(x = 544 + 50 * x_m);
        koniec_m.setY(y = 166 + 50 * y_m);
        Path droga_b_m = new Path();
        droga_b_m.getElements().addAll(poczatek_m, koniec_m);
        PathTransition animacja_b_m = new PathTransition(Duration.millis(statek.czas_animacji), droga_b_m, kolko);
        animacja_b_m.setOnFinished(e -> {
                    synchronized (this) {
                        notifyAll();
                    }
                }

        );

        Platform.runLater(() -> {
            animacja_b_m.play();
        });
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        statek.liczba_os_na_statku++;
        Path droga_m_s = new Path();
        MoveTo pocztek_s = new MoveTo();//
        pocztek_s.setX(x);
        pocztek_s.setY(y);
        LineTo koniec_s = new LineTo();
        koniec_s.setX(322 + statek.liczba_os_na_statku % 10 * 50);
        koniec_s.setY(432 + statek.liczba_os_na_statku / 10 * 50);
        droga_m_s.getElements().addAll(pocztek_s, koniec_s);
        PathTransition animacja_m_s = new PathTransition(Duration.millis(statek.czas_animacji), droga_m_s, kolko);
        animacja_m_s.setOnFinished(e -> {
                    synchronized (this) {
                        notifyAll();
                    }
                }

        );
        Platform.runLater(() -> {
            animacja_m_s.play();
        });
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        statek.liczba_os_na_mostku--;


        System.out.println("Pasazer " + nr_pasazera + " znajduje się na statku jako " + statek.liczba_os_na_statku + " z " + statek.N + " os na mostku:" + statek.liczba_os_na_mostku);
        statek.sem_chron_mostek.release();

        while (true) {
            try {
                sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (statek.liczba_os_na_mostku == 0 && statek.sem_czas_sie_skonczyl.availablePermits() == 0) {
                Platform.runLater(() -> {
                    HelloApplication.root.getChildren().remove(kolko);
                });
                break;
            }
        }
    }
}
