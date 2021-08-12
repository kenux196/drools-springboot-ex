package study.example.drools.core.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study.example.drools.utils.ApplicationContextProvider;

import java.util.List;

@Slf4j
public class RuleActionHandler {

    /**
     * The constant instance.
     */
    protected static RuleActionHandler instance = null;

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

    public void handler(List<Long> targetDeviceIds, boolean onOff) {
        log.debug("RuleActionHandler callback targetDevices =  " + targetDeviceIds + " 기기 상태 =  " + onOff);
        deviceService.changeDeviceStatus(targetDeviceIds, onOff);
    }
}
