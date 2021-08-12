package study.example.drools.core.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "rule")
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruleId;

    private String name;

    @Column(name = "description")
    private String desc;

    @OneToMany(mappedBy = "rule")
    private List<RuleDevice> ruleDevices = new ArrayList<>();

    @OneToMany(mappedBy = "rule")
    private List<RuleCondition> ruleConditions;

    @OneToMany(mappedBy = "rule")
    private List<RuleOperation> ruleOperations;
}
