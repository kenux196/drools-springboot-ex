package study.example.drools.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.example.drools.core.domain.enums.DeviceType;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceDto implements Serializable {

    private Long id;

    private DeviceType type;

    private Boolean operating;

    private Integer temperature;
}