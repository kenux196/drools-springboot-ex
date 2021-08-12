package study.example.drools.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.example.drools.core.domain.Device;
import study.example.drools.core.domain.enums.DeviceType;
import study.example.drools.core.repository.DeviceRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public void addDevice(Device device) {
        deviceRepository.addDevice(device);
    }

    public List<Device> getDevices() {
        return deviceRepository.getAllDevices();
    }

    public List<Device> getDeviceByType(DeviceType deviceType) {
        return deviceRepository.getDevices(deviceType);
    }

    public Optional<Device> getDeviceById(long deviceId) {
        return deviceRepository.findByDeviceId(deviceId);
    }

    public void changeAllAirConditionerDeviceStatus(boolean onOff) {
        getDevices().stream()
                .filter(device -> device.getType().equals(DeviceType.AIR_CONDITIONER))
                .forEach(device -> device.changeOperating(onOff));
    }

    public void changeDeviceStatus(List<Long> deviceId, boolean onOff) {
        deviceRepository.findByDeviceIds(deviceId)
                .forEach(device -> device.changeOperating(onOff));
    }

    public int getDeviceCount() {
        return deviceRepository.getDeviceCount();
    }
}