package study.example.drools.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.naming.factory.BeanFactory;
import study.example.drools.utils.ApplicationContextProvider;

import java.util.Set;

import static java.util.Objects.requireNonNull;

@Slf4j
@RequiredArgsConstructor
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

    public void handler(long deviceId, long temp) {
        log.debug("RuleActionHandler callback deviceId =  " + deviceId + " 온도 =  " + temp);
        deviceService.changeDeviceStatus();
    }
}
