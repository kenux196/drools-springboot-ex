package study.example.drools.core.repository;

import org.springframework.stereotype.Repository;
import study.example.drools.core.domain.Device;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DeviceRepository {

    private final List<Device> deviceList = new ArrayList<>();

    public void addDevice(Device device) {
        deviceList.add(device);
        final int size = deviceList.size();
        device.setId((long) size);
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }
}