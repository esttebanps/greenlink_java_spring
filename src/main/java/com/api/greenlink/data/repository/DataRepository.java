package com.api.greenlink.data.repository;

import com.api.greenlink.data.entity.SensorData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<SensorData, Long> {
    //@Query("SELECT s.created_at, s.value, s.sensor_id FROM sensor_data s WHERE s.sensor_id=:id")
    Page<SensorData> findAllBySensorId(Long id, PageRequest pageRequest);
}
