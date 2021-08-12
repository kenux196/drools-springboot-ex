package study.example.drools.core.drools.listener;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.event.rule.*;

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
