package study.example.drools.core.domain;

import lombok.*;
import study.example.drools.core.domain.enums.DeviceType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"ruleDevices", "conditionDevices"})
@Entity
@Table(name = "device")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeviceType type;

    private Boolean operating;

    private Integer temperature;

    @OneToMany(mappedBy = "device")
    @Builder.Default
    private List<RuleDevice> ruleDevices = new ArrayList<>();

    @OneToMany(mappedBy = "device")
    @Builder.Default
    private List<ConditionDevice> conditionDevices = new ArrayList<>();

    public static Device createAirConditioner(boolean operating) {
        return new Device(DeviceType.AIR_CONDITIONER, operating);
    }

    public static Device createAirQualitySensor(int temperature) {
        return new Device(DeviceType.AIR_QUALITY_SENSOR, temperature);
    }

    public Device(DeviceType type, boolean operating) {
        this.type = type;
        this.operating = operating;
    }

    public Device(DeviceType type, int temperature) {
        this.type = type;
        this.temperature = temperature;
    }

    public void changeOperating(boolean isOperating) {
        this.operating = isOperating;
    }

    public void changeTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void assignDeviceId(long id) {
        this.id = id;
    }
}