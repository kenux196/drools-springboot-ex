package study.example.drools.core.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.example.drools.core.domain.Device;
import study.example.drools.core.domain.enums.DeviceType;
import study.example.drools.core.repository.DeviceRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeviceServiceTest {

    @Autowired
    DeviceRepository deviceRepository;

    @Autowired
    DeviceService deviceService;

    @Test
    void addDevice() {
        int deviceCount = deviceService.getDeviceCount();
        deviceService.addDevice(Device.createAirConditioner(false));
        int deviceCount1 = deviceService.getDeviceCount();
        assertThat(deviceCount1).isEqualTo(deviceCount + 1);
    }

    @Test
    void getDeviceByType() {
        List<Device> result = deviceService.getDeviceByType(DeviceType.AIR_CONDITIONER);
        assertThat(result).size().isNotEqualTo(0);
        assertThat(result.get(0).getType()).isEqualTo(DeviceType.AIR_CONDITIONER);
    }

    @Test
    void changeDeviceStatus() {
        List<Device> result = deviceService.getDeviceByType(DeviceType.AIR_CONDITIONER);
        assertThat(result).size().isNotEqualTo(0).isGreaterThanOrEqualTo(10);
        assertThat(result.get(0).getType()).isEqualTo(DeviceType.AIR_CONDITIONER);
        List<Long> ids = result.stream()
                .map(Device::getId)
                .filter(id -> id < 10)
                .collect(Collectors.toList());
        deviceService.changeDeviceStatus(ids, true);
    }
}