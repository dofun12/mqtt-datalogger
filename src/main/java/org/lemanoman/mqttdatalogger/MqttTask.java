package org.lemanoman.mqttdatalogger;

import org.lemanoman.mqttdatalogger.model.CollectorModel;

import java.util.Date;

public class MqttTask implements Runnable {

    private String url;
    private String topic;
    private OnChangeStatus onChangeStatus;
    private MqttHelper mqttHelper;

    public MqttTask(String url, String topic, OnChangeStatus onChangeStatus) {
        this.url = url;
        this.topic = topic;
        this.onChangeStatus = onChangeStatus;

        mqttHelper = new MqttHelper(url);

    }

    @Override
    public void run() {
        mqttHelper.subscribe(1, (topic, mqttMessage) -> {
            String[] segments = topic.split("/");
            Integer deviceId = Integer.parseInt(segments[0]);
            String groupName = segments[1];
            String headerName = segments[2];

            String value = new String(mqttMessage.getPayload());
            Double doubleValue = Double.parseDouble(value);

            CollectorModel collectorModel = new CollectorModel();
            collectorModel.setCreated(new Date());
            collectorModel.setGroupName(groupName);
            collectorModel.setHeaderName(headerName);
            collectorModel.setIdDevice(deviceId);
            collectorModel.setValue(doubleValue);

            if(onChangeStatus!=null){
                onChangeStatus.onChangeTemperature(collectorModel);
            }
            System.out.println("Topic: "+topic+" ; "+mqttMessage);
        }, topic);
    }
}
