package study.example.drools.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.example.drools.core.domain.Operation;

public interface RuleOperationRepository extends JpaRepository<Operation, Long> {
}
