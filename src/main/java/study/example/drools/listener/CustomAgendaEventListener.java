package study.example.drools.listener;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.event.rule.*;

/**
* <pre>
* 서비스 명   : drools
* 패키지 명   : study.example.drools.listener
* 클래스 명   : CustomAgendaEventListener
* 설명       :
*
* ====================================================================================
*
* </pre>
* @date        2021-06-18
* @author      skyun
**/

@Slf4j
public class CustomAgendaEventListener implements AgendaEventListener {
    @Override
    public void matchCreated(MatchCreatedEvent event) {
        log.info("matchCreated event.getMatch() = " + event.getMatch().getRule());
    }

    @Override
    public void matchCancelled(MatchCancelledEvent event) {
        log.info("matchCancelled event.getMatch() = " + event.getMatch().getRule());
    }

    @Override
    public void beforeMatchFired(BeforeMatchFiredEvent event) {
        log.info("beforeMatchFired event.getMatch() = " + event.getMatch().getRule());
    }

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        log.info("afterMatchFired event.getMatch() = " + event.getMatch().getRule());
    }

    @Override
    public void agendaGroupPopped(AgendaGroupPoppedEvent event) {

    }

    @Override
    public void agendaGroupPushed(AgendaGroupPushedEvent event) {

    }

    @Override
    public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
        log.info("beforeRuleFlowGroupActivated event.getRuleFlowGroup() = " + event.getRuleFlowGroup());
    }

    @Override
    public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {
        log.info("afterRuleFlowGroupActivated event.getRuleFlowGroup() = " + event.getRuleFlowGroup());
    }

    @Override
    public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {

    }

    @Override
    public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {

    }
}
