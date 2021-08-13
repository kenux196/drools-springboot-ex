package study.example.drools.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.example.drools.core.domain.Device;
import study.example.drools.core.repository.RuleDeviceRepository;
import study.example.drools.core.repository.RuleOperationRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RuleControlService {

    private final RuleOperationRepository ruleOperationRepository;
    private final RuleDeviceRepository ruleDeviceRepository;
    private final DeviceService deviceService;

    public void requestDeviceControl(Set<Long> firedRules) {
        Set<Device> devices = ruleDeviceRepository.findDevice(firedRules);
        Set<String> operationValues = ruleOperationRepository.findOperationFromRuleId(firedRules);


        String value = operationValues.stream().findFirst().orElse(null);
        deviceService.changeAllDeviceStatus(devices, value);
    }
}
