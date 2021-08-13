package study.example.drools.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.example.drools.core.domain.Condition;

public interface RuleConditionRepository extends JpaRepository<Condition, Long> {

}
