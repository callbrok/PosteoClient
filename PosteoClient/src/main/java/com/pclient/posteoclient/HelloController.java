package com.pclient.posteoclient;

import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.util.Timer;
import java.util.TimerTask;

public class HelloController {
    private static final String POSTEO_URL = "https://posteo.de/en";

    @FXML
    private WebView mainWebView;
    @FXML
    private StackPane mainStackPane;


    private void setWebView(WebEngine webEngine){
        webEngine.load(POSTEO_URL);

        // Wait after page fully loaded
        webEngine.getLoadWorker().stateProperty().addListener(
                (ObservableValue<? extends Worker.State> observable,
                 Worker.State oldValue,
                 Worker.State newValue) -> {
                    if( newValue != Worker.State.SUCCEEDED ) {
                        return;
                    }


                    // print Main Color
                    getPageMainColor();


                    injectJSCredential(webEngine, "prova");

                } );
    }

    private void injectJSCredential(WebEngine webEngine, String username){
        // Inject Username
        webEngine.executeScript(
                "function login(user){"
                        + " var usernameField = document.getElementById(\"user_session_username\");"
                        + " usernameField.value = user;"
                        + "}"
                        + "login('" + username + "');"
        );
    }

    private void getPageMainColor(){
        int xValue = 200;
        int yValue = 200;
        Robot robot = new Robot();

        Color c = robot.getPixelColor(xValue, yValue);
    }

    public void initialize(){
        WebEngine webEngine = mainWebView.getEngine();
        setWebView(webEngine);

        // check periodically if account logged out and re-logging in
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {public void run() {
            if(!webEngine.getLocation().equals(POSTEO_URL)){
                setWebView(webEngine);
            }
        }}, 0, 3000);
    }


}