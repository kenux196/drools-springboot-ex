package study.example.drools.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class AirConditioner {
    private long id;
    private float temp;
    private boolean operating;
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

    public String getAirConditionerName() {
        return id + "번 에어컨";
    }

    public AirConditioner(int id) {
        this.id = id;
        this.temp = 20;
        this.operating = false;
        makeDummy();
    }

    public AirConditioner(int id, float temp, boolean operating) {
        this.id = id;
        this.temp = temp;
        this.operating = operating;
        makeDummy();
    }

    private void makeDummy() {
        for (int i = 0; i < 10; i++) {
            dummy.add(base);
        }
    }
}
