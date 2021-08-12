package study.example.drools.core.domain.enums;

import lombok.Getter;

public enum DeviceType {
    AIR_QUALITY_SENSOR("AirQualitySensor"),
    AIR_PURIFIER("AirPurifier"),
    AIR_CONDITIONER("AirConditioner");

    @Getter
    private String name;

    DeviceType(String name) {
        this.name = name;
    }
}
