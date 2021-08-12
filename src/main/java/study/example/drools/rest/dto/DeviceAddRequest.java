package study.example.drools.rest.dto;

import lombok.Data;
import study.example.drools.core.domain.enums.DeviceType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class DeviceAddRequest implements Serializable {
    private DeviceType type;
    private Boolean operation;

    @NotNull
    private Integer deviceCount;
}