package study.example.drools.domain;

import lombok.Data;

/**
* <pre>
* 서비스 명   : drools
* 패키지 명   : study.example.drools.domain
* 클래스 명   : TaxiRide
* 설명       :
*
* ====================================================================================
*
* </pre>
* @date        2021-06-18
* @author      skyun
**/

@Data
public class TaxiRide {
    private Boolean isNightSurcharge;
    private Long distanceInMile;
}