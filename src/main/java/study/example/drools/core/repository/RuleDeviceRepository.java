package study.example.drools.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.example.drools.core.domain.Device;
import study.example.drools.core.domain.RuleDevice;

import java.util.Set;

public interface RuleDeviceRepository extends JpaRepository<RuleDevice, Long> {

    @Query("select distinct rd.device from RuleDevice rd " +
            "where rd.rule.ruleId in :ruleIds")
    Set<Device> findDevice(@Param("ruleIds") Set<Long> ruleIds);
}
