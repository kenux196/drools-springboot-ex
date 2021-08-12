package study.example.drools.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.example.drools.core.domain.enums.DeviceType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceAddRequest implements Serializable {
    private DeviceType deviceType;
    private Boolean operation;

    @NotNull
    private Integer deviceCount;
}