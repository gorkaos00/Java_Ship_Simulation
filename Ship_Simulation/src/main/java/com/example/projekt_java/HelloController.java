package com.example.projekt_java;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class HelloController {
    int a,b,c;
    @FXML
    private Slider czas;

    @FXML
    private ScrollBar czas_animacji;

    @FXML
    private TextField mostek_max;

    @FXML
    private ImageView stateczek;

    @FXML
    private TextField statek_max;

    @FXML
    private Button uruchom;

    @FXML
    private Button zapis;

    @FXML
    void uruchom_program(ActionEvent event) {
        Statek statek = new Statek(b, c);
        MyThread thread = new MyThread(statek,stateczek);
        thread.start();
        statek.czas_animacji=(int)czas_animacji.getValue();
        statek.czas_odplywu=(int)czas.getValue();
    }

    @FXML
    void zapisz_dane(ActionEvent event) {
        b=Integer.parseInt(statek_max.getText());
        c=Integer.parseInt(mostek_max.getText());
        System.out.println(a+" "+b+" "+c);
        int k;
        if(c%2==0){
            k=0;
        }
        else{
            k=1;
        }
        int x=0;
        int y=0;
        for(int i=0;i<c;i++){
            if(i>0 && i%((c/2)+k)==0) {
                y = 0;
                x++;
            }
            Rectangle kwadrat = new Rectangle();
            kwadrat.setX(521+50*x);
            kwadrat.setY(143+50*y);
            kwadrat.setHeight(45);
            kwadrat.setWidth(45);
            kwadrat.setOpacity(0.32);
            Platform.runLater(() -> {
                HelloApplication.root.getChildren().add(kwadrat);
            });
            y++;
        }
    }

}




