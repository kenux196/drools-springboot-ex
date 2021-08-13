package study.example.drools.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.example.drools.core.domain.ConditionDevice;

public interface RuleConditionDeviceRepository extends JpaRepository<ConditionDevice, Long> {
}
