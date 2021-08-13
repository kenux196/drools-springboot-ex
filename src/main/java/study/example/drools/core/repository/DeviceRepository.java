package study.example.drools.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.example.drools.core.domain.Device;
import study.example.drools.core.domain.enums.DeviceType;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    List<Device> findAllByType(DeviceType deviceType);
}