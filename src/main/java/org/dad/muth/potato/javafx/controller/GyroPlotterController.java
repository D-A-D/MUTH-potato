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
    @FXML
    public LineChart yLineChart;
    @FXML
    public NumberAxis yTimeAxis;
    @FXML
    public LineChart zLineChart;
    @FXML
    public NumberAxis zTimeAxis;

    static boolean yPeakReached=false;

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
        final XYChart.Series<Long, Double> ySeries = (XYChart.Series<Long, Double>) yLineChart.getData().get(0);
        final ObservableList<XYChart.Data<Long, Double>> ySeriesData = ySeries.getData();
        if(!yPeakReached) {
            if (ySeriesData.size() >= 50) {
                yPeakReached = true;
                System.out.println("Y peak reached");
            }
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(yPeakReached) {
                    System.out.println("Removing " + ySeriesData.size());
                    ySeriesData.add(new XYChart.Data<Long, Double>(timestamp,rotation.getX()*10));
                    ySeriesData.remove(0);
                    yTimeAxis.setLowerBound(ySeriesData.get(0).getXValue());
                    yTimeAxis.setUpperBound(ySeriesData.get(ySeriesData.size() - 1).getXValue());
                }else{
                    ySeriesData.add(new XYChart.Data<Long, Double>(timestamp,rotation.getX()*10));
                }
            }
        });
        /*Platform.runLater(new Runnable() {
            @Override
            public void run() {
                XYChart.Series<Long, Double> xSeries = (XYChart.Series<Long, Double>) xLineChart.getData().get(0);
                xSeries.getData().add(new XYChart.Data<Long, Double>(timestamp,rotation.getX()*10));
                if(xSeries.getData().size()>250){
                    xSeries.getData().remove(0);
                }
                xTimeAxis.setLowerBound(xSeries.getData().get(0).getXValue());
                xTimeAxis.setUpperBound(xSeries.getData().get(xSeries.getData().size()-1).getXValue());
            }
        });
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                XYChart.Series<Long, Double> zSeries = (XYChart.Series<Long, Double>) zLineChart.getData().get(0);
                zSeries.getData().add(new XYChart.Data<Long, Double>(timestamp,rotation.getZ()*10));
                if(zSeries.getData().size()>250){
                    zSeries.getData().remove(0);
                }
                zTimeAxis.setLowerBound(zSeries.getData().get(0).getXValue());
                zTimeAxis.setUpperBound(zSeries.getData().get(zSeries.getData().size()-1).getXValue());
            }
        });*/
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GyroPlotter.gyroPlotterController = this;
        yLineChart.setAnimated(true);
        yLineChart.setData(initGyroPlotData());
        xLineChart.setData(initGyroPlotData());
        zLineChart.setData(initGyroPlotData());
        xTimeAxis.setTickUnit(1);
        yTimeAxis.setTickUnit(1);
        zTimeAxis.setTickUnit(1);
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
