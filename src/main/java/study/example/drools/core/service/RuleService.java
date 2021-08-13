package study.example.drools.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.example.drools.core.domain.*;
import study.example.drools.core.domain.enums.DeviceType;
import study.example.drools.core.repository.*;
import study.example.drools.rest.dto.ConditionDto;
import study.example.drools.rest.dto.DeviceDto;
import study.example.drools.rest.dto.OperationDto;
import study.example.drools.rest.dto.RuleDto;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RuleService {

    private final DeviceRepository deviceRepository;
    private final RuleRepository ruleRepository;
    private final RuleConditionRepository ruleConditionRepository;
    private final RuleDeviceRepository ruleDeviceRepository;
    private final RuleOperationRepository ruleOperationRepository;
    private final RuleConditionDeviceRepository ruleConditionDeviceRepository;
    private final DroolsService droolsService;

    public Long createRule(RuleDto ruleDto) {
        final Rule rule = Rule.builder()
                .name(ruleDto.getName())
                .build();
        ruleRepository.save(rule);

        final List<ConditionDto> conditions = ruleDto.getConditions();
        List<ConditionDevice> conditionDevices = new ArrayList<>();
        conditions.forEach(conditionDto -> {
            final Condition condition = Condition.builder()
                    .operand(conditionDto.getOperand())
                    .comparator(conditionDto.getComparator())
                    .value(conditionDto.getValue())
                    .build();
            condition.updateRule(rule);
            ruleConditionRepository.save(condition);
            final List<DeviceDto> devices = conditionDto.getDevices();
            final List<Long> deviceIds = devices.stream()
                    .map(DeviceDto::getId)
                    .collect(Collectors.toList());
            final List<Device> findDevices = deviceRepository.findAllById(deviceIds);
            for (Device device : findDevices) {
                ConditionDevice conditionDevice = new ConditionDevice();
                conditionDevice.changeCondition(condition);
                conditionDevice.changeDevice(device);
                conditionDevices.add(conditionDevice);
            }
        });
        ruleConditionDeviceRepository.saveAll(conditionDevices);

        // device
        final List<DeviceDto> devices = ruleDto.getDevices();
        final List<Long> deviceIds = devices.stream()
                .map(DeviceDto::getId)
                .collect(Collectors.toList());
        final List<Device> targetDevices = deviceRepository.findAllById(deviceIds);

        List<RuleDevice> ruleDeviceList = new ArrayList<>();
        targetDevices.forEach(device -> {
            final RuleDevice ruleDevice = RuleDevice.builder()
                    .rule(rule)
                    .device(device)
                    .build();
            ruleDevice.changeRule(rule);
            ruleDevice.changeDevice(device);
            ruleDeviceList.add(ruleDevice);
        });
        ruleDeviceRepository.saveAll(ruleDeviceList);

        // operation
        List<Operation> operations = new ArrayList<>();
        ruleDto.getOperations().forEach(operationDto -> {
            final Operation operation = Operation.builder()
                    .command(operationDto.getDeviceCommand())
                    .value(operationDto.getValue())
                    .build();
            operation.changeRule(rule);
            operations.add(operation);
        });
        ruleOperationRepository.saveAll(operations);

        droolsService.createDroolsRule(rule);

        return rule.getRuleId();
    }

    public List<RuleDto> getAllRules() {
        return null;
    }

    public RuleDto getRule(Long id) throws Exception {
//        final Rule foundRule = ruleRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("룰 못 찾음 : " + id));
//        final RuleDto ruleDto = RuleDto.builder().build();
//        return ruleDto;
        return getExampleRuleDto();
    }


    public void deleteRule(List<Long> ids) {

    }

    private RuleDto getExampleRuleDto() throws Exception {
        final List<Device> devices = deviceRepository.findAll();

        final Device monitoringDevice = devices.stream()
                .filter(device -> device.getType().equals(DeviceType.AIR_QUALITY_SENSOR))
                .findFirst().orElseThrow(() -> new Exception("모니터링 기기 못찾음"));

        DeviceDto monitoringDeviceDto = DeviceDto.builder()
                .id(monitoringDevice.getDeviceId())
                .type(monitoringDevice.getType())
                .temperature(30)
                .build();

        final ConditionDto conditionDto = ConditionDto.builder()
                .operand("indoorTemp")
                .comparator(">")
                .value("30")
                .devices(Collections.singletonList(monitoringDeviceDto))
                .build();

        final Device targetDevice = devices.stream()
                .filter(device -> device.getType().equals(DeviceType.AIR_CONDITIONER))
                .findFirst().orElseThrow(() -> new Exception("타겟 기기 못찾음"));

        final DeviceDto targetDeviceDto = DeviceDto.builder()
                .id(targetDevice.getDeviceId())
                .operating(targetDevice.getOperating())
                .temperature(targetDevice.getTemperature())
                .type(targetDevice.getType())
                .build();

        final OperationDto operationDto = OperationDto.builder()
                .deviceCommand("AirCondition-Power")
                .value("on")
                .build();

        return RuleDto.builder()
                .id(1L)
                .name("테스트 룰 - 01010")
                .conditions(Collections.singletonList(conditionDto))
                .devices(Collections.singletonList(targetDeviceDto))
                .operations(Collections.singletonList(operationDto))
                .build();
    }
}