package study.example.drools.domain;

import lombok.Data;

@Data
public class Fare {
    private Long nightSurcharge;
    private Long rideFare;

    public Long getTotalFare() {
        return nightSurcharge + rideFare;
    }
}