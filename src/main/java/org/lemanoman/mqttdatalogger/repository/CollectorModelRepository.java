package org.lemanoman.mqttdatalogger.repository;

import org.lemanoman.mqttdatalogger.model.CollectorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectorModelRepository extends JpaRepository<CollectorModel, Integer>, JpaSpecificationExecutor<CollectorModel> {

}