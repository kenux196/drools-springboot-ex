package study.example.drools.repository;

import org.springframework.stereotype.Repository;
import study.example.drools.domain.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 서비스 명   : drools
 * 패키지 명   : study.example.drools.repository
 * 클래스 명   : DeviceRepository
 * 설명       :
 *
 * ====================================================================================
 *
 * </pre>
 *
 * @author skyun
 * @date 2021-06-18
 **/

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