package study.example.drools.rest.dto;

import lombok.Builder;
import lombok.Data;
import study.example.drools.core.domain.enums.DeviceType;

import java.io.Serializable;

@Data
@Builder
public class DeviceStatusResponse implements Serializable {
    private Long id;
    private DeviceType type;
    private Boolean operation;
}