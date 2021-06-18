package study.example.drools.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.example.drools.domain.AirConditioner;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * <pre>
 * 서비스 명   : drools
 * 패키지 명   : study.example.drools.service
 * 클래스 명   : AirConditionerServiceTest
 * 설명       :
 *
 * ====================================================================================
 *
 * </pre>
 *
 * @author skyun
 * @date 2021-06-17
 **/

@SpringBootTest
class AirConditionerServiceTest {

    @Autowired
    AirConditionerService airConditionerService;

    @Test
    void addAirConditionerTest() {
        AirConditioner airConditioner = new AirConditioner(1);
        airConditionerService.addAirConditioner(airConditioner);
        final Collection<FactHandle> factHandles = airConditionerService.getFactHandles();
        assertThat(factHandles).hasSize(1);
    }

    @Test
    @DisplayName("온도가 30도 이상이면 에어컨 켠다.")
    void airConditionerOnTest() {
        AirConditioner airConditioner = new AirConditioner(1, 20, false);
        airConditionerService.addAirConditioner(airConditioner);
        airConditionerService.updateSensorData(35);

        assertThat(airConditioner.isOperating()).isTrue();
    }

    @Test
    @DisplayName("온도가 30도 미만이면 에어컨 끈다")
    void airConditionerOffTest() {
        AirConditioner airConditioner = new AirConditioner(1, 20, true);
        airConditionerService.addAirConditioner(airConditioner);
        airConditionerService.updateSensorData(24);

        assertThat(airConditioner.isOperating()).isFalse();
    }
}