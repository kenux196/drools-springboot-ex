package study.example.drools.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.stereotype.Service;
import study.example.drools.domain.Device;
import study.example.drools.domain.TempSensor;
import study.example.drools.listener.CustomAgendaEventListener;
import study.example.drools.listener.CustomWorkingMemoryEventListener;
import study.example.drools.repository.DeviceRepository;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

/**
 * <pre>
 * 서비스 명   : drools
 * 패키지 명   : study.example.drools.service
 * 클래스 명   : DeviceService
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
@Service
@RequiredArgsConstructor
public class DeviceService {

    private final KieContainer kieContainer;
    private final DeviceRepository deviceRepository;

    private KieSession kieSession;

    private final TempSensor tempSensor = new TempSensor();

    @PostConstruct
    private void initService() {
        kieSession = kieContainer.newKieSession();
        kieSession.addEventListener(new CustomAgendaEventListener());
        kieSession.addEventListener(new CustomWorkingMemoryEventListener());
    }

    public FactHandle addDevice(Device device) {
        deviceRepository.addDevice(device);
        FactHandle factHandle = kieSession.insert(device);
        kieSession.fireAllRules();
        printFactSize("기기 추가 => " + device.getDeviceInfo(), false);
        return factHandle;
    }

    public void updateSensorData(int temp) {
        log.debug("온도 센서 값 변경 = " + temp);
        tempSensor.setIndoorTemp(temp);
        FactHandle factHandle = kieSession.getFactHandle(tempSensor);
        if (factHandle != null) {
            kieSession.update(factHandle, tempSensor); // update exist fact
            log.debug("온도 센서 Fact 업데이트");
        } else {
            kieSession.insert(tempSensor); // insert new fact
            log.debug("온도 센서 Fact 추가");
        }
        kieSession.fireAllRules();
        printFactSize("온도 값 변경 완료", false);
    }

    public Collection<FactHandle> getFactHandles() {
        return kieSession.getFactHandles();
    }

    private void printFactSize(String msg, boolean showFactHandleInfo) {
        log.info(msg + " ==> kieSession.getFactCount() = " + kieSession.getFactCount());

        if (showFactHandleInfo) {
            Collection<FactHandle> factHandles = kieSession.getFactHandles();
            for (FactHandle factHandle : factHandles) {
                log.info("factHandle = " + factHandle.toExternalForm());
            }
        }
    }

    public List<Device> getDevices() {
        return deviceRepository.getDeviceList();
    }
}