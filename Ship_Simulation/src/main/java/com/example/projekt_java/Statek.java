package com.example.projekt_java;

import javafx.scene.control.TextField;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Statek {
    public  int czas_odplywu;
    public int czas_animacji;
    public static int N;
    public  static int K;

    public static int M=0;
    public volatile int liczba_os_na_mostku = 0;
    public volatile int liczba_os_na_statku = 0;
    public  static volatile Semaphore sem_chron_mostek = new Semaphore(0);
    public static volatile Semaphore sem_czas_sie_skonczyl = new Semaphore(0);
    public  static volatile Semaphore sem_chron_statek = new Semaphore(0);
    public Statek( int b, int c){
        this.N=b;
        this.K=c;
        this.sem_chron_mostek.release(K);
        this.sem_chron_statek.release(N);
    }

}


