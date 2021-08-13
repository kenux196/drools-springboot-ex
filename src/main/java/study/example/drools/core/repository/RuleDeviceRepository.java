package study.example.drools.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.example.drools.core.domain.RuleDevice;

public interface RuleDeviceRepository extends JpaRepository<RuleDevice, Long> {
}
