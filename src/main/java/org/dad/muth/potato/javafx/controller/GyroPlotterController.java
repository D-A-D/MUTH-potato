package org.dad.muth.potato.javafx.controller;

import com.thalmic.myo.Quaternion;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.dad.muth.potato.GyroPlotter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Mayank on 10/10/2016.
 */
public class GyroPlotterController implements Initializable{
    @FXML
    public LineChart xLineChart;
    @FXML
    public NumberAxis xTimeAxis;
    public GyroPlotterController(){

    }
    public void loadData(ActionEvent actionEvent) {
    }

    public void pauseData(ActionEvent actionEvent) {

    }

    /**
     *
     * @return
     */
    public ObservableList<XYChart.Series<Long,Double >> initGyroPlotData(){
        ObservableList<XYChart.Series<Long, Double>> list = FXCollections.observableArrayList();
        XYChart.Series<Long, Double> series = new XYChart.Series<>();
        list.add(series);
        return list;
    }
    public void addGyroData(final long timestamp, final Quaternion rotation) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                XYChart.Series<Long, Double> xSeries = (XYChart.Series<Long, Double>) xLineChart.getData().get(0);
                xSeries.getData().add(new XYChart.Data<Long, Double>(timestamp,rotation.getY()*10));
                if(xSeries.getData().size()>250){
                    xSeries.getData().remove(0);
                }
                System.out.println(" X : " + xSeries.getData().size());
                xTimeAxis.setTickUnit(1);
                xTimeAxis.setLowerBound(xSeries.getData().get(0).getXValue());
                xTimeAxis.setUpperBound(xSeries.getData().get(xSeries.getData().size()-1).getXValue());
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GyroPlotter.gyroPlotterController = this;
        xLineChart.setAnimated(true);
        xLineChart.setData(initGyroPlotData());
       // prepareTimeline();
    }

    private void prepareTimeline() {
        // Every frame to take any data from queue and add to chart
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                //pitchChartSeries.getData().add(new XYChart.Data(1,2));
            }
        }.start();
    }
}
