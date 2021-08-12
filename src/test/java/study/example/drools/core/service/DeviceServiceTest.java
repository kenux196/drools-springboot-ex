package study.example.drools.core.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.example.drools.core.domain.Device;
import study.example.drools.core.domain.SingleStatusRule;
import study.example.drools.core.domain.TempSensor;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DeviceServiceTest {

    @Autowired
    DeviceService deviceService;
    @Autowired
    DroolsService droolsService;

    @Test
    @DisplayName("기기 추가 테스트")
    void addDeviceTest() {
        final Device device = Device.createAirConditioner(false);
        deviceService.addDevice(device);
        droolsService.fireAllRules();
        final Collection<FactHandle> factHandles = droolsService.getFactHandles();
        assertThat(factHandles).hasSize(1);
    }

    @Test
    @DisplayName("온도가 30도 이상이면 에어컨 켠다.")
    void airConditionerOnTest() {
        final Device device = Device.createAirConditioner(false);
        deviceService.addDevice(device);
        droolsService.validateRule(35);
        droolsService.fireAllRules();
        assertThat(device.getOperating()).isTrue();
    }

    @Test
    @DisplayName("온도 30도 이상, 새로운 센서 객체 주입")
    void airConditionerOnTestFromNewSensor() {
        final Device device = Device.createAirConditioner(false);
        deviceService.addDeviceOnlyRepository(device);
        for (int i = 0; i < 4; i++) {
            int temperature = 23;
            if (i == 3) temperature = 33;
            final TempSensor tempSensor = new TempSensor(i, temperature, 35, 3);
            droolsService.validateRule(tempSensor);
        }
        droolsService.fireAllRules();
        assertThat(device.getOperating()).isTrue();
    }

    @Test
    @DisplayName("룰 추가 테스트")
    void addRuleTest() throws InterruptedException {
        final Device device = Device.createAirConditioner(false);
        deviceService.addDeviceOnlyRepository(device);

        final long conditionId = 10;
        final long ruleId = 10;
        final String ruleName = "RES-" + ruleId + "-" + conditionId;
        droolsService.addRule3(
                SingleStatusRule.builder()
                        .className(TempSensor.class.getSimpleName())
                        .deviceId("3")
                        .ruleId(ruleId)
                        .ruleName(ruleName)
                        .conditionId(conditionId)
                        .duration(null)
                        .value("30")
                        .comparator(">")
                        .operand("indoorTemp")
                        .build());


        Thread.sleep(20000);

        for (int i = 0; i < 100; i++) {
            int temperature = 23;
            int deviceId = i % 10;
            if (i  == 3) temperature = 33;
            final TempSensor tempSensor = new TempSensor(i, temperature, 35, 3);
            droolsService.validateRule(tempSensor);
        }
        droolsService.fireAllRules();
        assertThat(device.getOperating()).isTrue();
    }

    @Test
    @DisplayName("온도가 30도 미만이면 에어컨 끈다")
    void airConditionerOffTest() {

        final Device device = Device.createAirConditioner(true);
        deviceService.addDevice(device);
        droolsService.validateRule(24);
        assertThat(device.getOperating()).isFalse();
    }
}