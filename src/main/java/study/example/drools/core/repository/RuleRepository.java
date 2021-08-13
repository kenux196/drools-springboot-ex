package study.example.drools.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.example.drools.core.domain.Device;
import study.example.drools.core.domain.Rule;

import java.util.Set;

public interface RuleRepository extends JpaRepository<Rule, Long> {
}
