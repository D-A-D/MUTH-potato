package org.dad.muth.potato;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.dad.muth.potato.myo.MyoInitializer;

/**
 * Created by Mayank on 10/10/2016.
 */
public class GyroPlotter extends Application {
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/gyro.fxml"));
        primaryStage.setTitle("Gyro Plots from MYO");
        primaryStage.setScene(new Scene(root,1280, 720));
        primaryStage.show();
        startMyo();
    }

    public static void main(String[] args) {
        launch(args);
    }
    void startMyo(){
        MyoInitializer myoInitializer = new MyoInitializer(1000/50);
        myoInitializer.init();
        myoInitializer.start();

    }
}
