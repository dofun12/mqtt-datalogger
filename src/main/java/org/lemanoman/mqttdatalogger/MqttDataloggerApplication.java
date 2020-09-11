package org.lemanoman.mqttdatalogger;

import org.lemanoman.mqttdatalogger.model.CollectorModel;
import org.lemanoman.mqttdatalogger.repository.CollectorModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@SpringBootApplication
public class MqttDataloggerApplication  implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(MqttDataloggerApplication.class, args);
    }

    @Autowired
    private CollectorModelRepository repository;

    @Value("${mqtt.brokerurl}")
    private String brokerurl;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        MqttHelper mqttHelper = new MqttHelper(brokerurl);
        mqttHelper.subscribe( 1, (topic, mqttMessage) -> {
            String[] segments = topic.split("/");
            Integer deviceId = Integer.parseInt(segments[0]);
            String groupName = segments[1];
            String headerName = segments[2];

            String value = new String(mqttMessage.getPayload());
            Double doubleValue = Double.parseDouble(value);


            OffsetDateTime dateTime = OffsetDateTime.now(ZoneOffset.UTC);

            CollectorModel collectorModel = new CollectorModel();
            collectorModel.setCreated(new Date(dateTime.toInstant().toEpochMilli()));
            collectorModel.setGroupName(groupName);
            collectorModel.setHeaderName(headerName);
            collectorModel.setIdDevice(deviceId);
            collectorModel.setValue(doubleValue);

            //repository.saveAndFlush(collectorModel);
            System.out.println("Logging... "+topic+" : "+value);

        }, "#");
    }
}
