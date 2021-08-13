package study.example.drools.core.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class SingleRule implements Serializable {

    @NonNull
    private Long ruleId;

    @NonNull
    private Long conditionId;

    @NonNull
    private String deviceId;

    private Long duration;

    @NonNull
    private String operand;

    @NonNull
    private String comparator;

    @NonNull
    private String value;

    @NonNull
    private String ruleName;

    @NonNull
    private String className;

}