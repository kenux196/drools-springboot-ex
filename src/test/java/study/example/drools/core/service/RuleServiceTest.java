package study.example.drools.core.service;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import study.example.drools.core.domain.Device;
import study.example.drools.core.domain.enums.DeviceType;
import study.example.drools.rest.dto.ConditionDto;
import study.example.drools.rest.dto.DeviceDto;
import study.example.drools.rest.dto.OperationDto;
import study.example.drools.rest.dto.RuleDto;

import java.util.Collections;
import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 DB 사용하고 싶을때 NONE 사용
@Rollback(value = false)
class RuleServiceTest {

    @Autowired
    RuleService ruleService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    DroolsService droolsService;

    @Test
    void createRuleTest() throws Exception {
        final List<DeviceDto> devices = deviceService.getDevices();

        final DeviceDto monitoringDevice = devices.stream()
                .filter(device -> device.getType().equals(DeviceType.AIR_QUALITY_SENSOR))
                .findFirst().orElseThrow(() -> new Exception("모니터링 기기 못찾음"));

        monitoringDevice.setTemperature(30);

        final ConditionDto conditionDto = ConditionDto.builder()
                .operand("temperature")
                .comparator(">")
                .value("30")
                .devices(Collections.singletonList(monitoringDevice))
                .build();

        final DeviceDto targetDevice = devices.stream()
                .filter(device -> device.getType().equals(DeviceType.AIR_CONDITIONER))
                .findFirst().orElseThrow(() -> new Exception("타겟 기기 못찾음"));

        final OperationDto operationDto = OperationDto.builder()
                .deviceCommand("AirCondition-Power")
                .value("on")
                .build();

        final RuleDto ruleDto = RuleDto.builder()
                .name("테스트 룰 - 01010")
                .conditions(Collections.singletonList(conditionDto))
                .devices(Collections.singletonList(targetDevice))
                .operations(Collections.singletonList(operationDto))
                .build();

        ruleService.createRule(ruleDto);
        droolsService.validateForce();
    }
}