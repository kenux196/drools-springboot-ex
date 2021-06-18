package study.example.drools.service;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import study.example.drools.domain.Fare;
import study.example.drools.domain.TaxiRide;

/**
 * <pre>
 * 서비스 명   : drools
 * 패키지 명   : study.example.drools.service
 * 클래스 명   : TaxiFareCalculatorService
 * 설명       :
 *
 * ====================================================================================
 *
 * </pre>
 *
 * @author skyun
 * @date 2021-06-17
 **/

@Slf4j
@Service
public class TaxiFareCalculatorService {

    private final KieSession kieSession;

    public TaxiFareCalculatorService(KieContainer kieContainer) {
        this.kieSession = kieContainer.newKieSession();
    }

    public Long calculateFare(TaxiRide taxiRide, Fare rideFare) {
        kieSession.setGlobal("rideFare", rideFare);
        kieSession.insert(taxiRide);
        kieSession.fireAllRules();
        kieSession.dispose();

        log.info("!! RIDE FARE !! " + rideFare.getTotalFare());
        return rideFare.getTotalFare();
    }
}