package study.example.drools.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "rule_device")
public class RuleDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rule_id")
    private Rule rule;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id")
    private Device device;

    public void changeDevice(Device device) {
//        if (this.device != null) {
//            this.device.getRuleDevices().remove(this);
//        }
        this.device = device;
//        device.getRuleDevices().add(this);
    }

    public void changeRule(Rule rule) {
        if (this.rule != null) {
            this.rule.getRuleDevices().remove(this);
        }
        this.rule = rule;
        rule.getRuleDevices().add(this);
    }
}
