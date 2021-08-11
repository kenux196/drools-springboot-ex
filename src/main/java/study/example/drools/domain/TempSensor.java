package study.example.drools.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class TempSensor {
    private int deviceId;
    private int indoorTemp;
    private int outdoorTemp;
    private int airQuality;
}