package study.example.drools.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.example.drools.core.domain.Device;
import study.example.drools.core.domain.Operation;
import study.example.drools.core.domain.Rule;
import study.example.drools.core.domain.RuleDevice;
import study.example.drools.core.repository.RuleDeviceRepository;
import study.example.drools.core.repository.RuleOperationRepository;
import study.example.drools.core.repository.RuleRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RuleControlService {

    private final RuleRepository ruleRepository;
    private final RuleOperationRepository ruleOperationRepository;
    private final RuleDeviceRepository ruleDeviceRepository;
    private final DeviceService deviceService;

    @Transactional
    public void requestDeviceControl(Set<Long> firedRules) {

        List<Rule> rules = ruleRepository.findAllById(firedRules);

        Set<Device> devices = rules.stream()
                .flatMap(rule -> rule.getRuleDevices().stream().map(RuleDevice::getDevice))
                .collect(Collectors.toSet());
        Set<String> values = rules.stream()
                .flatMap(rule -> rule.getOperations().stream().map(Operation::getValue))
                .collect(Collectors.toSet());
//        Set<Device> devices = ruleDeviceRepository.findDevice(firedRules);
//        Set<String> values = ruleOperationRepository.findOperationFromRuleId(firedRules);


        String value = values.stream().findFirst().orElse(null);
        deviceService.changeAllDeviceStatus(devices, value);
    }
}
