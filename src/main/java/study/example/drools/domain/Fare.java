package study.example.drools.domain;

import lombok.Data;

/**
* <pre>
* 서비스 명   : drools
* 패키지 명   : study.example.drools.domain
* 클래스 명   : Fare
* 설명       :
*
* ====================================================================================
*
* </pre>
* @date        2021-06-17
* @author      skyun
**/

@Data
public class Fare {
    private Long nightSurcharge;
    private Long rideFare;

    public Long getTotalFare() {
        return nightSurcharge + rideFare;
    }
}