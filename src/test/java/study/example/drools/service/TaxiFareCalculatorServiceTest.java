package study.example.drools.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import study.example.drools.config.TaxiFareConfiguration;
import study.example.drools.domain.Fare;
import study.example.drools.domain.TaxiRide;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * <pre>
 * 서비스 명   : drools
 * 패키지 명   : study.example.drools.service
 * 클래스 명   : TaxiFareCalculatorServiceTest
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

@SpringBootTest
@ContextConfiguration(classes = TaxiFareConfiguration.class)
class TaxiFareCalculatorServiceTest {

    @Autowired
    private TaxiFareCalculatorService taxiFareCalculatorService;


    @Test
    void whenNightSurchargeFalseAndDistanceLessThan10_thenFixFareWithoutNightSurcharge() {
        TaxiRide taxiRide = new TaxiRide();
        taxiRide.setIsNightSurcharge(false);
        taxiRide.setDistanceInMile(9L);
        Fare rideFare = new Fare();
        Long totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);

        assertThat(totalCharge).isEqualTo(70L);
    }

    @Test
    void whenNightSurchargeTrueAndDistanceLessThan10_thenFixFareWithNightSurcharge() {
        TaxiRide taxiRide = new TaxiRide();
        taxiRide.setIsNightSurcharge(true);
        taxiRide.setDistanceInMile(5L);
        Fare rideFare = new Fare();
        Long totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);

        assertThat(totalCharge).isEqualTo(100L);
    }

    @Test
    void whenNightSurchargeFalseAndDistanceLessThan100_thenDoubleFareWithoutNightSurcharge() {
        TaxiRide taxiRide = new TaxiRide();
        taxiRide.setIsNightSurcharge(false);
        taxiRide.setDistanceInMile(50L);
        Fare rideFare = new Fare();
        Long totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);

        assertThat(totalCharge).isEqualTo(170L);
    }

    @Test
    void whenNightSurchargeTrueAndDistanceLessThan100_thenDoubleFareWithNightSurcharge() {
        TaxiRide taxiRide = new TaxiRide();
        taxiRide.setIsNightSurcharge(true);
        taxiRide.setDistanceInMile(50L);
        Fare rideFare = new Fare();
        Long totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);

        assertThat(totalCharge).isEqualTo(250L);
    }

    @Test
    void whenNightSurchargeFalseAndDistanceGreaterThan100_thenExtraPercentFareWithoutNightSurcharge() {
        TaxiRide taxiRide = new TaxiRide();
        taxiRide.setIsNightSurcharge(false);
        taxiRide.setDistanceInMile(100L);
        Fare rideFare = new Fare();
        Long totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);

        assertThat(totalCharge).isEqualTo(220L);
    }

    @Test
    void whenNightSurchargeTrueAndDistanceGreaterThan100_thenExtraPercentFareWithNightSurcharge() {
        TaxiRide taxiRide = new TaxiRide();
        taxiRide.setIsNightSurcharge(true);
        taxiRide.setDistanceInMile(100L);
        Fare rideFare = new Fare();
        Long totalCharge = taxiFareCalculatorService.calculateFare(taxiRide, rideFare);

        assertThat(totalCharge).isEqualTo(350L);
    }

}