package study.example.drools.rest.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MonitoringDeviceInfo implements Serializable {
    private Long ruleId;
    private Long deviceId;
    private String comparator;
    private Integer temperature;
    private Boolean operation;
}