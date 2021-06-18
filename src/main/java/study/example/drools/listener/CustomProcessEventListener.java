package study.example.drools.listener;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.event.process.*;

/**
 * <pre>
 * 서비스 명   : drools
 * 패키지 명   : study.example.drools.listener
 * 클래스 명   : CustomProcessEventListener
 * 설명       :
 *
 * ====================================================================================
 *
 * </pre>
 *
 * @author skyun
 * @date 2021-06-18
 **/

@Slf4j
public class CustomProcessEventListener implements ProcessEventListener {
    @Override
    public void beforeProcessStarted(ProcessStartedEvent event) {
        log.info("beforeProcessStarted : " + event);
    }

    @Override
    public void afterProcessStarted(ProcessStartedEvent event) {
        log.info("afterProcessStarted : " + event);
    }

    @Override
    public void beforeProcessCompleted(ProcessCompletedEvent event) {
        log.info("beforeProcessCompleted : " + event);
    }

    @Override
    public void afterProcessCompleted(ProcessCompletedEvent event) {
        log.info("afterProcessCompleted : " + event);
    }

    @Override
    public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
        log.info("beforeNodeTriggered : " + event);
    }

    @Override
    public void afterNodeTriggered(ProcessNodeTriggeredEvent event) {
        log.info("afterNodeTriggered : " + event);
    }

    @Override
    public void beforeNodeLeft(ProcessNodeLeftEvent event) {
        log.info("beforeNodeLeft : " + event);
    }

    @Override
    public void afterNodeLeft(ProcessNodeLeftEvent event) {
        log.info("afterNodeLeft : " + event);
    }

    @Override
    public void beforeVariableChanged(ProcessVariableChangedEvent event) {
        log.info("beforeVariableChanged : " + event);
    }

    @Override
    public void afterVariableChanged(ProcessVariableChangedEvent event) {
        log.info("afterVariableChanged : " + event);
    }
}