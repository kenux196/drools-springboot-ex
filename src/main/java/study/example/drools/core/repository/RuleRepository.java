package study.example.drools.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.example.drools.core.domain.Rule;

import java.util.ArrayList;
import java.util.List;

public interface RuleRepository extends JpaRepository<Rule, Long> {

}
