package study.example.drools.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.example.drools.core.domain.Device;
import study.example.drools.core.domain.SingleStatusRule;
import study.example.drools.core.domain.TempSensor;
import study.example.drools.core.drools.template.SingleStatusTemplate;
import study.example.drools.core.repository.DeviceRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final SingleStatusTemplate singleStatusTemplate;
    private final TempSensor tempSensor = new TempSensor();


    private SingleStatusRule getSingleStatusRule() {
        final long conditionId = 10;
        final long ruleId = 10;
        final int deviceId = 3;
        final String ruleName = "RES-" + ruleId + "-" + conditionId;
        return SingleStatusRule.builder()
                .className(TempSensor.class.getSimpleName())
                .deviceId(String.valueOf(deviceId))
                .ruleId(ruleId)
                .ruleName(ruleName)
                .conditionId(conditionId)
                .duration(null)
                .value("30")
                .comparator(">")
                .operand("indoorTemp")
                .build();
    }

    public void addDevice(Device device) {
        deviceRepository.addDevice(device);
    }

    public void addDeviceOnlyRepository(Device device) {
        deviceRepository.addDevice(device);
    }


    public List<Device> getDevices() {
        return deviceRepository.getDeviceList();
    }

    public void changeDeviceStatus() {
        for (Device device : getDevices()) {
            device.setOperating(true);
        }
    }
}