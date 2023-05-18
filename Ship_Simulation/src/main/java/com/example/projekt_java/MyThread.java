package com.example.projekt_java;

import javafx.scene.image.ImageView;

import java.util.Random;

public class MyThread extends Thread {
        ImageView stateczek;
        Statek statek;

        public MyThread(Statek statek, ImageView stateczek){
            this.statek=statek;
            this.stateczek=stateczek;
        }
        public void run() {
            Random random=new Random();
            int nr_pasazera = 1;

            while (true) {

                statek.sem_czas_sie_skonczyl.release();

                Kapitan Kapitan = new Kapitan(statek, stateczek);
                Kapitan.start();

                while (true) {


                    Pasazer Pasazer = new Pasazer(nr_pasazera, statek);
                    nr_pasazera++;
                    Pasazer.start();

                    try {
                        sleep(random.nextInt(500));

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (nr_pasazera == 46) {
                        nr_pasazera = 0;
                    }
                    if(statek.sem_czas_sie_skonczyl.availablePermits()==0){
                        break;
                    }

                }


                try {
                    Kapitan.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                statek.sem_chron_statek.release(statek.liczba_os_na_statku);
                statek.liczba_os_na_statku = 0;

            }
        }
    }



