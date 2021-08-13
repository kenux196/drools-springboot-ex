package study.example.drools.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import study.example.drools.core.domain.Operation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RuleDto implements Serializable {

    private Long id;

    @NotEmpty
    @Size(min = 1, max = 30)
    private String name;

    private Boolean activate; // 미사용

    private OffsetDateTime created;

    private OffsetDateTime updated;

    @NotEmpty
    private List<ConditionDto> conditions; // 조건 대상 기기 및 조건 정보

    @NotEmpty
    private List<DeviceDto> devices; // 컨트롤 대상 기기

    @NotEmpty
    private List<OperationDto> operations; // 컨트롤 명령
}