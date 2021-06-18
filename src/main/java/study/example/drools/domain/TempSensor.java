package study.example.drools.domain;

import lombok.Data;
import lombok.ToString;

/**
* <pre>
* 서비스 명   : drools
* 패키지 명   : study.example.drools.domain
* 클래스 명   : TempSensor
* 설명       :
*
* ====================================================================================
*
* </pre>
* @date        2021-06-18
* @author      skyun
**/

@Data
@ToString
public class TempSensor {
    private int indoorTemp;
}