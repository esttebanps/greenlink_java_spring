package com.api.greenlink.repository;

import com.api.greenlink.entity.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
    //@Query("SELECT s.created_at, s.value, s.sensor_id FROM sensor_data s WHERE s.sensor_id=:id")
    List<SensorData> findAllBySensorId(Long id);
}
