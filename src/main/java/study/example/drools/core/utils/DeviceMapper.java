package study.example.drools.core.utils;

import study.example.drools.core.domain.Device;
import study.example.drools.rest.dto.DeviceDto;

public class DeviceMapper {

    public static Device toEntity(DeviceDto deviceDto) {
        return Device.builder()
                .deviceId(deviceDto.getId())
                .operating(deviceDto.getOperating())
                .temperature(deviceDto.getTemperature())
                .type(deviceDto.getType())
                .build();
    }

    public static DeviceDto toDto(Device device) {
        return DeviceDto.builder()
                .id(device.getDeviceId())
                .operating(device.getOperating())
                .temperature(device.getTemperature())
                .type(device.getType())
                .build();
    }
}