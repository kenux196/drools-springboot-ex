package study.example.drools.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.example.drools.domain.Device;
import study.example.drools.domain.TempSensor;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <pre>
 * 서비스 명   : drools
 * 패키지 명   : study.example.drools.service
 * 클래스 명   : DeviceServiceTest
 * 설명       :
 *
 * ====================================================================================
 *
 * </pre>
 *
 * @author skyun
 * @date 2021-06-18
 **/
@SpringBootTest
class DeviceServiceTest {

    @Autowired
    DeviceService deviceService;

    @Test
    @DisplayName("기기 추가 테스트")
    void addDeviceTest() {
        final Device device = Device.createAirConditioner(false);
        deviceService.addDevice(device);
        deviceService.fireAllRules();
        final Collection<FactHandle> factHandles = deviceService.getFactHandles();
        assertThat(factHandles).hasSize(1);
    }

    @Test
    @DisplayName("온도가 30도 이상이면 에어컨 켠다.")
    void airConditionerOnTest() {
        final Device device = Device.createAirConditioner(false);
        deviceService.addDevice(device);
        deviceService.updateSensorData(35);
        deviceService.fireAllRules();
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
            deviceService.updateSensorData(tempSensor);
        }
        deviceService.fireAllRules();
        assertThat(device.getOperating()).isTrue();
    }


    @Test
    @DisplayName("온도가 30도 미만이면 에어컨 끈다")
    void airConditionerOffTest() {

        final Device device = Device.createAirConditioner(true);
        deviceService.addDevice(device);
        deviceService.updateSensorData(24);

        assertThat(device.getOperating()).isFalse();
    }
}