package org.lemanoman.mqttdatalogger;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import java.util.Arrays;

public class MqttHelper {
    private MqttClient client = null;


    public MqttHelper(String url){
        try {
            MqttConnectOptions mqttOptions = new MqttConnectOptions();
            mqttOptions.setMaxInflight(200);
            mqttOptions.setConnectionTimeout(3);
            mqttOptions.setKeepAliveInterval(10);
            mqttOptions.setAutomaticReconnect(true);
            mqttOptions.setCleanSession(false);

            //client = new MqttClient("tcp://192.168.15.66:1883", String.format("cliente_java_%d", System.currentTimeMillis()), new MqttDefaultFilePersistence(System.getProperty("java.io.tmpdir")));
            client = new MqttClient(url, String.format("cliente_java_%d", System.currentTimeMillis()), new MqttDefaultFilePersistence(System.getProperty("java.io.tmpdir")));
            client.setCallback(new MqttCallbackExtended() {
                @Override
                public void connectComplete(boolean b, String s) {

                }

                @Override
                public void connectionLost(Throwable throwable) {

                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });
            client.connect(mqttOptions);
        } catch (MqttException ex) {
            System.out.println("Erro ao se conectar ao broker mqtt "  + " - " + ex);
        }
    }

    public IMqttToken subscribe(int qos, IMqttMessageListener gestorMensagemMQTT, String... topicos) {
        if (client == null || topicos.length == 0) {
            return null;
        }
        int tamanho = topicos.length;
        int[] qoss = new int[tamanho];
        IMqttMessageListener[] listners = new IMqttMessageListener[tamanho];

        for (int i = 0; i < tamanho; i++) {
            qoss[i] = qos;
            listners[i] = gestorMensagemMQTT;
        }
        try {
            return client.subscribeWithResponse(topicos, qoss, listners);
        } catch (MqttException ex) {
            System.out.println(String.format("Erro ao se inscrever nos tópicos %s - %s", Arrays.asList(topicos), ex));
            return null;
        }
    }
}
