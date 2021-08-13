package study.example.drools.core.drools.callback;


import lombok.extern.slf4j.Slf4j;
import study.example.drools.core.service.DeviceService;
import study.example.drools.core.service.DroolsService;
import study.example.drools.core.service.RuleService;
import study.example.drools.utils.ApplicationContextProvider;

@Slf4j
public class RuleActionHandler {

    protected static RuleActionHandler instance = null;
    private final DroolsService droolsService;

    private RuleActionHandler() {
        droolsService = ApplicationContextProvider.getApplicationContext().getBean(DroolsService.class);
    }

    public static synchronized RuleActionHandler getInstance() {

        if (instance != null) {
            return instance;
        }
        instance = new RuleActionHandler();
        return instance;
    }

    public void handler(long ruleId, long conditionId) {
        log.info("RuleActionHandler callback ruleId =  " + ruleId + " conditionId =  " + conditionId);
        droolsService.addFiredRule(ruleId);
    }
}
