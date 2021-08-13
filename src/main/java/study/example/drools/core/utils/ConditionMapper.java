package study.example.drools.core.utils;

import study.example.drools.core.domain.Condition;
import study.example.drools.core.domain.ConditionDevice;
import study.example.drools.core.domain.Device;
import study.example.drools.rest.dto.ConditionDto;

import java.util.ArrayList;
import java.util.List;

public class ConditionMapper {

    public static ConditionDto toDto(Condition condition) {
        return null;
    }

    public static Condition toEntity(ConditionDto conditionDto) {
        final Condition condition = Condition.builder()
                .id(conditionDto.getId())
                .comparator(conditionDto.getComparator())
                .operand(conditionDto.getOperand())
                .value(conditionDto.getValue())
                .build();

        List<ConditionDevice> conditionDevices = new ArrayList<>();
        conditionDto.getDevices().forEach(deviceDto -> {
            final Device device = DeviceMapper.toEntity(deviceDto);
            ConditionDevice conditionDevice = new ConditionDevice();
            conditionDevice.changeDevice(device);
            conditionDevice.changeCondition(condition);
            conditionDevices.add(conditionDevice);
        });

//        condition.

        return condition;
    }
}