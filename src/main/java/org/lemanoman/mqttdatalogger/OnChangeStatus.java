package org.lemanoman.mqttdatalogger;

import org.lemanoman.mqttdatalogger.model.CollectorModel;

public interface OnChangeStatus {
    void onChangeTemperature(CollectorModel collectorModel);
}
