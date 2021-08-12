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
        final SingleStatusRule rule = DeviceService.createRule(3, device.getId(), true, 30, ">");
        droolsService.addRule3(rule);
        TempSensor tempSensor = new TempSensor(device.getId(), 35, 33, 0);
        droolsService.validateRule(tempSensor);
        droolsService.fireAllRules();
        assertThat(device.getOperating()).isTrue();
    }

    @Test
    @DisplayName("룰 추가 테스트")
    void addRuleTest() throws InterruptedException {
        final Device device = Device.createAirConditioner(false);
        deviceService.addDevice(device);

        droolsService.addRule3(
                DeviceService.createRule(3, device.getId(), true, 30, ">"));

        for (int i = 0; i < 100; i++) {
            int temperature = 23;
            int deviceId = i % 10;
            if (deviceId == device.getId()) temperature = 33;
            final TempSensor tempSensor = new TempSensor(i, temperature, 35, 3);
            droolsService.validateRule(tempSensor);
        }
        droolsService.fireAllRules();
        assertThat(device.getOperating()).isTrue();
    }

    @Test
    @DisplayName("온도가 25도 미만이면 에어컨 끈다")
    void airConditionerOffTest() {
        final Device device = Device.createAirConditioner(true);
        deviceService.addDevice(device);

        final SingleStatusRule rule = DeviceService.createRule(1, device.getId(), false, 25, "<");
        droolsService.addRule3(rule);

        TempSensor tempSensor = new TempSensor(device.getId(), 24, 33, 0);
        droolsService.validateRule(tempSensor);
        droolsService.fireAllRules();
        assertThat(device.getOperating()).isFalse();
    }

}