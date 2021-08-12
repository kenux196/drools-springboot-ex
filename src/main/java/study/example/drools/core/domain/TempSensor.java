package study.example.drools.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TempSensor {
    private long deviceId;
    private int indoorTemp;
    private int outdoorTemp;
    private int airQuality;
}