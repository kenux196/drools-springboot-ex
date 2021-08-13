package study.example.drools.core.domain;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class SingleStatusRule implements Serializable {
    /**
     * Rule Id
     */
    @NonNull
    private Long ruleId;

    /**
     * Condition Id
     */
    @NonNull
    private Long conditionId;

    /**
     * Device ID
     */
    @NonNull
    private String deviceId;

    /**
     * Duration of Rule condition
     */
    private Long duration;

    /**
     * Operand
     */
    @NonNull
    private String operand;

    private String getterOperand;

    /**
     * Comparator like ==, !=, >, < etc
     */
    @NonNull
    private String comparator;

    /**
     * Values for comparison
     */
    @NonNull
    private String value;

    /**
     * Rule name
     */
    @NonNull
    private String ruleName;

    /**
     * Class name
     */
    @NonNull
    private String className;

}