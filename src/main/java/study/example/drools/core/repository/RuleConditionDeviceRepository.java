package study.example.drools.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import study.example.drools.core.domain.ConditionDevice;

import java.util.Set;

public interface RuleConditionDeviceRepository extends JpaRepository<ConditionDevice, Long> {

    @Query(value = "select distinct cd.device.id from ConditionDevice cd")
    Set<Long> findAllConditionDevice();
}
