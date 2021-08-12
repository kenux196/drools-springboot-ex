package study.example.drools.core.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study.example.drools.utils.ApplicationContextProvider;

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

    public void handler(long deviceId, boolean onOff) {
        log.debug("RuleActionHandler callback deviceId =  " + deviceId + " 기기 상태 =  " + onOff);
        deviceService.changeDeviceStatus(onOff);
    }
}
