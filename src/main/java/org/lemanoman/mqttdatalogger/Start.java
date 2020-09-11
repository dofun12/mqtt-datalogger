package org.lemanoman.mqttdatalogger;

import org.lemanoman.mqttdatalogger.model.CollectorModel;

import javax.swing.*;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Start {

    public static void main(String... args){
        HouseChart chart = new HouseChart();
        JFrame frame = new JFrame();
        frame.setSize(1200,600);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(chart.getMainPanel());
        frame.setVisible(true);

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        chart.init();
        MqttTask mqttTask = new MqttTask("tcp://192.168.15.66:1883", "#", collectorModel -> chart.onChangeTemperature(collectorModel));
        executorService.submit(mqttTask);

    }
}
