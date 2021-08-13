package study.example.drools.core.service;


import lombok.extern.slf4j.Slf4j;
import study.example.drools.utils.ApplicationContextProvider;

import java.util.Set;

@Slf4j
public class RuleActionHandler {

    protected static RuleActionHandler instance = null;
//    private final Set<Long> ruleSet;

    private final DeviceService deviceService;

    private RuleActionHandler() {
        deviceService = ApplicationContextProvider.getApplicationContext().getBean(DeviceService.class);

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
        deviceService.changeDeviceStatus(ruleId, conditionId);
    }
}
