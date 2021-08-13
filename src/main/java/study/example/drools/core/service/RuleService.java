package study.example.drools.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.example.drools.core.domain.*;
import study.example.drools.core.repository.*;
import study.example.drools.rest.dto.ConditionDto;
import study.example.drools.rest.dto.DeviceDto;
import study.example.drools.rest.dto.OperationDto;
import study.example.drools.rest.dto.RuleDto;

import java.util.ArrayList;
import java.util.List;
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

    public void createRule(RuleDto ruleDto) {
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

    }

//    private void getRuleOperation(List<OperationDto> operationDtoList) {
//        operationDtoList.forEach(operationDto -> {
//            operationDto.getDevices()
//        });
//    }

    public void deleteRule(List<Long> ids) {

    }

    public static SingleStatusRule createSingleStatusRule(long ruleId, long deviceId, boolean onOff, int value, String compare) {
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
}