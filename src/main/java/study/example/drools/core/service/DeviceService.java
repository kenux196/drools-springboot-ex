package study.example.drools.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.example.drools.core.domain.Device;
import study.example.drools.core.domain.enums.DeviceType;
import study.example.drools.core.repository.DeviceRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @PostConstruct
    private void init() {
        createDevices();
    }

    private void createDevices() {
        List<Device> devices = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            devices.add(Device.createAirConditioner(false));
        }

        for (int i = 0; i < 2; i++) {
            devices.add(Device.createAirQualitySensor(33));
        }

        deviceRepository.saveAll(devices);
    }

    public Device addDevice(Device device) {
        return deviceRepository.save(device);
    }

    public List<Device> getDevices() {
        return deviceRepository.findAll();
    }

    public List<Device> getDeviceByType(DeviceType deviceType) {
        return deviceRepository.findAllByType(deviceType);
    }

    public Optional<Device> getDevice(long deviceId) {
        return deviceRepository.findById(deviceId);
    }

    public void changeAllAirConditionerDeviceStatus(boolean onOff) {
        final List<Device> airConditioners = getDeviceByType(DeviceType.AIR_CONDITIONER);
        airConditioners.forEach(device -> device.changeOperating(onOff));
        deviceRepository.saveAll(airConditioners);
    }

    public void changeAllDeviceStatus(List<Long> deviceId, boolean onOff) {
        final List<Device> devices = deviceRepository.findAllById(deviceId);
        devices.forEach(device -> device.changeOperating(onOff));
        deviceRepository.saveAll(devices);
    }

    public void changeDeviceStatus(long devicId, long conditionId) {
        log.info("changeDeviceStatus~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ impl");
    }

    public long getDeviceCount() {
        return deviceRepository.count();
    }
}