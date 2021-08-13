package study.example.drools.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "rule_condition_device")
public class ConditionDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "condition_id")
    private Condition condition;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id")
    private Device device;

    public void changeDevice(Device device) {
//        if (this.device != null) {
//            this.device.getConditionDevices().remove(this);
//        }
        this.device = device;
//        device.getConditionDevices().add(this);
    }

    public void changeCondition(Condition condition) {
        if (this.condition != null) {
            this.condition.getConditionDevices().remove(this);
        }
        this.condition = condition;
        condition.getConditionDevices().add(this);
    }
}
