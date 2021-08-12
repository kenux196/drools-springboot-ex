package study.example.drools.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.example.drools.core.domain.Device;
import study.example.drools.core.domain.SingleStatusRule;
import study.example.drools.core.domain.TempSensor;
import study.example.drools.core.domain.enums.DeviceType;
import study.example.drools.core.drools.template.SingleStatusTemplate;
import study.example.drools.core.repository.DeviceRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @PostConstruct
    private void init() {
        createTargetDevices();
    }

    private void createTargetDevices() {
        for (int i = 0; i < 100; i++) {
            Device device = new Device(DeviceType.AIR_CONDITIONER, false);
            addDevice(device);
        }
    }

    public void addDevice(Device device) {
        deviceRepository.addDevice(device);
    }

    public List<Device> getDevices() {
        return deviceRepository.getDeviceList();
    }

    public void changeDeviceStatus(boolean onOff) {
        for (Device device : getDevices()) {
            device.setOperating(onOff);
        }
    }
}