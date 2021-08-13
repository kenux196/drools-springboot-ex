package study.example.drools.core.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.example.drools.core.domain.Device;
import study.example.drools.core.domain.enums.DeviceType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DeviceServiceTest {

    @Autowired
    DeviceService deviceService;

    @Test
    void initTest() {
        final List<Device> devices = deviceService.getDevices();
        assertThat(devices).isNotNull().isNotEmpty();
        assertThat(devices.size()).isEqualTo(60);
        System.out.println("devices.size() = " + devices.size());
    }

    @Test
    void addDevice() {
        final Device airConditioner = Device.createAirConditioner(true);
        final Device saveDevice = deviceService.addDevice(airConditioner);

        final Optional<Device> findDevice = deviceService.getDevice(saveDevice.getId());
        assertThat(findDevice).isPresent()
                .containsInstanceOf(Device.class)
                .hasValueSatisfying(d -> {
                    assertThat(d.getId()).isEqualTo(saveDevice.getId());
                    assertThat(d.getType()).isEqualTo(DeviceType.AIR_CONDITIONER);
                    assertThat(d.getOperating()).isTrue();
                });
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

        final List<Device> devices = deviceService.getDeviceByType(DeviceType.AIR_CONDITIONER);
        assertThat(devices).size().isNotEqualTo(0).isGreaterThanOrEqualTo(10);
        assertThat(devices.get(0).getType()).isEqualTo(DeviceType.AIR_CONDITIONER);
        assertThat(devices.get(0).getOperating()).isTrue();
    }
}