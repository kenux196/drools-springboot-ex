package study.example.drools.core.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import study.example.drools.core.domain.Condition;
import study.example.drools.core.domain.ConditionDevice;
import study.example.drools.core.domain.Device;
import study.example.drools.core.domain.enums.DeviceType;


@DataJpaTest
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 실제 DB 사용하고 싶을때 NONE 사용
class RuleConditionRepositoryTest {

    @Autowired
    RuleConditionRepository ruleConditionRepository;
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    RuleConditionDeviceRepository ruleConditionDeviceRepository;

    @Test
    void createTest() {
        Device device = Device.builder()
                .type(DeviceType.AIR_CONDITIONER)
                .temperature(20)
                .operating(false)
                .build();
        deviceRepository.save(device);

        Condition condition = Condition.builder()
                .comparator("<")
                .operand("indoorTemp")
                .value("33")
                .build();
        ruleConditionRepository.save(condition);

        final ConditionDevice conditionDevice = ConditionDevice.builder()
                .build();
        conditionDevice.changeCondition(condition);
        conditionDevice.changeDevice(device);
        ruleConditionDeviceRepository.save(conditionDevice);

    }


}