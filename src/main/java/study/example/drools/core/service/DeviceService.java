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

    public static SingleStatusRule createRule(long ruleId, long deviceId, boolean onOff, int value, String compare) {
        return SingleStatusRule.builder()
                .className(TempSensor.class.getSimpleName())
                .deviceId(String.valueOf(deviceId))
                .ruleId(ruleId)
                .ruleName("Test Rule - " + ruleId + " - " + deviceId + " - " + onOff)
                .conditionId(onOff)
                .duration(null)
                .value(String.valueOf(value))
                .comparator(compare)
                .operand("indoorTemp")
                .build();
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