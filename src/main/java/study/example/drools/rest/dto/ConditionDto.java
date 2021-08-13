package study.example.drools.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConditionDto implements Serializable {
    private Long id;

    private String operand;

    private String comparator;

    private String value;

    private List<DeviceDto> devices;
}