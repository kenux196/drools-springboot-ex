package study.example.drools.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.example.drools.core.domain.Operation;

import java.util.List;
import java.util.Set;

public interface RuleOperationRepository extends JpaRepository<Operation, Long> {

    @Query("select distinct o.value from Operation o " +
            "join fetch Rule r on r.ruleId = o.rule.ruleId " +
            "where r.ruleId in :ruleIds ")
    Set<String> findOperationFromRuleId(@Param("ruleIds") Set<Long> ruleIds);
}
