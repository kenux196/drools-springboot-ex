package study.example.drools.domain.enums;

import lombok.Getter;

/**
 * <pre>
 * 서비스 명   : drools
 * 패키지 명   : study.example.drools.domain.enums
 * 클래스 명   : DeviceType
 * 설명       :
 *
 * ====================================================================================
 *
 * </pre>
 *
 * @author skyun
 * @date 2021-06-18
 **/
public enum DeviceType {
    AIR_CONDITIONER("AirConditioner");

    @Getter
    private String name;

    DeviceType(String name) {
        this.name = name;
    }
}
