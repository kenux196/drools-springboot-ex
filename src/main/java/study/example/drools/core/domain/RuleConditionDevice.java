package study.example.drools.core.domain;

import javax.persistence.*;

@Entity
@Table(name = "rule_condition_device")
public class RuleConditionDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condition_id")
    private RuleCondition ruleCondition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private Device device;
}
