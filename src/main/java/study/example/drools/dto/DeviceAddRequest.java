package study.example.drools.dto;

import lombok.Data;
import study.example.drools.domain.enums.DeviceType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <pre>
 * 서비스 명   : drools
 * 패키지 명   : study.example.drools.dto
 * 클래스 명   : DeviceRequest
 * 설명       :
 *
 * ====================================================================================
 *
 * </pre>
 *
 * @author skyun
 * @date 2021-06-18
 **/
@Data
public class DeviceAddRequest implements Serializable {
    private DeviceType type;
    private Boolean operation;

    @NotNull
    private Integer deviceCount;
}