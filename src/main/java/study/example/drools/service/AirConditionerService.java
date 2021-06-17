package study.example.drools.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.stereotype.Service;
import study.example.drools.domain.AirConditioner;
import study.example.drools.domain.Fare;
import study.example.drools.domain.TaxiRide;
import study.example.drools.domain.TempSensor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <pre>
 * 서비스 명   : drools
 * 패키지 명   : study.example.drools.service
 * 클래스 명   : AirConditionerService
 * 설명       :
 *
 * ====================================================================================
 *
 * </pre>
 *
 * @author skyun
 * @version 1.0.0
 * @date 2021-06-17
 **/

@Slf4j
@Service
public class AirConditionerService {

    private final KieContainer kieContainer;
    private final KieSession kieSession;

    private final List<AirConditioner> airConditioners = new ArrayList<>();
    private final TempSensor tempSensor = new TempSensor();

    public AirConditionerService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
        this.kieSession = kieContainer.newKieSession();
    }

    public FactHandle addAirConditioner(AirConditioner airConditioner) {
        airConditioners.add(airConditioner);
        FactHandle factHandle = kieSession.insert(airConditioner);
        kieSession.fireAllRules();
        printFactSize("에어컨 추가 => " + airConditioner.getAirConditionerName(), false);
        return factHandle;
    }

    public void updateSensorData(int temp) {
        log.debug("온도 센서 값 변경 = " + temp);
        tempSensor.setIndoorTemp(temp);
        FactHandle factHandle = kieSession.getFactHandle(tempSensor);
        if (factHandle != null) {
            kieSession.update(factHandle, tempSensor); // update exist fact
            log.debug("온도 센서 Fact 업데이트");
        } else {
            kieSession.insert(tempSensor); // insert new fact
            log.debug("온도 센서 Fact 추가");
        }
        kieSession.fireAllRules();
        printFactSize("온도 값 변경 완료", false);
    }

    public Collection<FactHandle> getFactHandles() {
        return kieSession.getFactHandles();
    }

    private void printFactSize(String msg, boolean showFactHandleInfo) {
        log.info(msg + " ==> kieSession.getFactCount() = " + kieSession.getFactCount());

        if (showFactHandleInfo) {
            Collection<FactHandle> factHandles = kieSession.getFactHandles();
            for (FactHandle factHandle : factHandles) {
                log.info("factHandle = " + factHandle.toExternalForm());
            }
        }
    }
}