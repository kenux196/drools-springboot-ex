package study.example.drools.dto;

import lombok.Builder;
import lombok.Data;
import study.example.drools.domain.enums.DeviceType;

import java.io.Serializable;

/**
 * <pre>
 * 서비스 명   : drools
 * 패키지 명   : study.example.drools.dto
 * 클래스 명   : DeviceStatusResponse
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
@Builder
public class DeviceStatusResponse implements Serializable {
    private Long id;
    private DeviceType type;
    private Boolean operation;
}