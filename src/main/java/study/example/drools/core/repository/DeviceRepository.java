package study.example.drools.core.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import study.example.drools.core.domain.Device;
import study.example.drools.core.domain.enums.DeviceType;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DeviceRepository {

    private final List<Device> deviceList = new ArrayList<>();

    @PostConstruct
    private void init() {
        createTargetDevices();
        createMonitoringDevices();
    }

    private void createTargetDevices() {
        for (int i = 0; i < 100; i++) {
            addDevice(Device.createAirConditioner(false));
        }
    }

    private void createMonitoringDevices() {
        for (int i = 0; i < 5; i++) {
            addDevice(Device.createAirQualitySensor(20));
        }
    }

    public void addDevice(Device device) {
        deviceList.add(device);
        final int size = deviceList.size();
        device.setId((long) size);
    }

    public List<Device> getDevices(DeviceType type) {
        return deviceList.stream()
                .filter(device -> device.getType().equals(type))
                .collect(Collectors.toList());
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }
}