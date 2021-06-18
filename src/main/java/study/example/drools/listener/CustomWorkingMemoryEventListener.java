package study.example.drools.listener;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;

/**
* <pre>
* 서비스 명   : drools
* 패키지 명   : study.example.drools.listener
* 클래스 명   : CustomWorkingMemoryEventListener
* 설명       :
*
* ====================================================================================
*
* </pre>
* @date        2021-06-18
* @author      skyun
**/

@Slf4j
public class CustomWorkingMemoryEventListener implements RuleRuntimeEventListener {
    @Override
    public void objectInserted(ObjectInsertedEvent event) {
        log.info("objectInserted event.getObject().getClass() = " + event.getObject().getClass());
        log.info("objectInserted event.getFactHandle() = " + event.getFactHandle());
        if (event.getRule() != null) {
            log.info("objectInserted event.getRule().getName() = " + event.getRule().getName());
        }
    }

    @Override
    public void objectUpdated(ObjectUpdatedEvent event) {
        log.info("objectUpdated event.getObject().getClass() = " + event.getObject().getClass());
        log.info("objectUpdated event.getFactHandle() = " + event.getFactHandle());
        log.info("objectUpdated event.getRule().getName() = " + event.getRule().getName());
    }

    @Override
    public void objectDeleted(ObjectDeletedEvent event) {
        log.info("objectInserted event.getOldObject().getClass() = " + event.getOldObject().getClass());
        log.info("objectInserted event.getFactHandle() = " + event.getFactHandle());
        log.info("objectInserted event.getRule().getName() = " + event.getRule().getName());
    }
}
