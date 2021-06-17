package study.example.drools.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;
import study.example.drools.config.TaxiFareConfiguration;
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
 * @version 1.0.0
 * @date 2021-06-17
 **/

@Slf4j
@RequiredArgsConstructor
@Service
public class TaxiFareCalculatorService {

    private final KieContainer kieContainer;

    public Long calculateFare(TaxiRide taxiRide, Fare rideFare) {
        final KieSession kieSession = kieContainer.newKieSession();
        kieSession.setGlobal("rideFare", rideFare);
        kieSession.insert(taxiRide);
        kieSession.fireAllRules();
        kieSession.dispose();

        log.info("!! RIDE FARE !! " + rideFare.getTotalFare());
        return rideFare.getTotalFare();
    }
}