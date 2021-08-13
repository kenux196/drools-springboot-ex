package study.example.drools.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.example.drools.core.domain.Rule;

public interface RuleRepository extends JpaRepository<Rule, Long> {

}
