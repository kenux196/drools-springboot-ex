package study.example.drools.domain;

import lombok.Data;
import study.example.drools.domain.enums.DeviceType;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 서비스 명   : drools
 * 패키지 명   : study.example.drools.domain
 * 클래스 명   : Device
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
public class Device {
    private Long id;
    private DeviceType type;
    private Boolean operating;
    private List<String> dummy = new ArrayList<>();

    String base = "{\"name\":null,\"nameDisplayCoordinate\":null,\"type\":null,\"mappedType\":null," +
            "\"description\":null,\"parentId\":null,\"registrationStatus\":null," +
            "\"configuration\":{\"mobilityType\":null,\"remoteControl\":\"Permit\",\"time\":null,\"locationType\":null}," +
            "\"modes\":[{\"id\":\"AirConditioner_Indoor_IndoorMode\",\"description\":null,\"enabled\":null,\"value\":\"Auto\"}," +
            "{\"id\":\"AirConditioner_Indoor_ErvMode\",\"description\":null,\"enabled\":null,\"value\":\"Auto\"}," +
            "{\"id\":\"AirConditioner_Indoor_DhwMode\",\"description\":null,\"enabled\":null,\"value\":\"Standard\"}]," +
            "\"operations\":[{\"id\":\"AirConditioner_Indoor_IndoorPower\",\"value\":\"on\"}," +
            "{\"id\":\"AirConditioner_Indoor_ErvPower\",\"value\":\"on\"}," +
            "{\"id\":\"AirConditioner_Indoor_DhwPower\",\"value\":\"on\"}]," +
            "\"temperatures\":[{\"id\":\"AirConditioner_Indoor_IndoorSetTemp\",\"enabled\":null,\"unit\":null,\"desired\":24.0}," +
            "{\"id\":\"AirConditioner_Indoor_WateroutSetTemp\",\"enabled\":null,\"unit\":null,\"desired\":24.0}," +
            "{\"id\":\"AirConditioner_Indoor_DhwSetTemp\",\"enabled\":null,\"unit\":null,\"desired\":40.0}]," +
            "\"meters\":null,\"lights\":null,\"id\":null,\"airConditioner\":null}";

    public static Device createAirConditioner(boolean operating) {
        return new Device(DeviceType.AIR_CONDITIONER, operating);
    }

    public Device(DeviceType type, boolean operating) {
        this.type = type;
        this.operating = operating;
        makeDummy();
    }

    private void makeDummy() {
        for (int i = 0; i < 10; i++) {
            dummy.add(base);
        }
    }

    public String getDeviceInfo() {
        return id + "번 디바이스 : " + type.getName();
    }

}