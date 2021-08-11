package study.example.drools.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieBase;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.stereotype.Service;
import study.example.drools.domain.Device;
import study.example.drools.domain.TempSensor;
import study.example.drools.listener.CustomAgendaEventListener;
import study.example.drools.listener.CustomRuleRunTimeEventListener;
import study.example.drools.listener.CustomProcessEventListener;
import study.example.drools.repository.DeviceRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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
    private final KieSession kieSession;
    private final DeviceRepository deviceRepository;

    private final TempSensor tempSensor = new TempSensor();

    @PostConstruct
    private void initService() {
        kieSession.addEventListener(new CustomAgendaEventListener());
        kieSession.addEventListener(new CustomRuleRunTimeEventListener());
        kieSession.addEventListener(new CustomProcessEventListener());
    }

    public FactHandle addDevice(Device device) {
        deviceRepository.addDevice(device);
        FactHandle factHandle = kieSession.insert(device);
//        fireAllRules();
        printFactSize("기기 추가 => " + device.getDeviceInfo(), false);
        return factHandle;
    }

    public void addDeviceOnlyRepository(Device device) {
        deviceRepository.addDevice(device);
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
//        fireAllRules();
        printFactSize("온도 값 변경 완료", false);
    }

    public void updateSensorData(TempSensor tempSensor) {
        log.debug("온도 센서 값 변경 = " + tempSensor.getIndoorTemp());
        tempSensor.setIndoorTemp(tempSensor.getIndoorTemp());
        FactHandle factHandle = kieSession.getFactHandle(tempSensor);
        if (factHandle != null) {
            kieSession.update(factHandle, tempSensor); // update exist fact
            log.debug("온도 센서 Fact 업데이트");
        } else {
            kieSession.insert(tempSensor); // insert new fact
            log.debug("온도 센서 Fact 추가");
        }
//        fireAllRules();
        printFactSize("온도 값 변경 완료", false);
    }

    public void fireAllRules() {
        final int firedRuleCount = kieSession.fireAllRules();
        log.info("fired rule count = " + firedRuleCount);
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

    public void changeDeviceStatus() {
        for (Device device : getDevices()) {
            device.setOperating(true);
        }
    }

    public void reviewKieContainer() {
        log.info("kieBaseNames : " + kieContainer.getKieBaseNames());
        final KieBase kieBase = kieContainer.getKieBase();
        Collection<KiePackage> kiePackages = kieBase.getKiePackages();
        log.info("kiePackages : " + kiePackages);
        for (KiePackage kiePackage : kiePackages) {
            log.info("kiePackage : " + kiePackage.getName());
            Collection<Rule> rules = kiePackage.getRules();
            for (Rule rule : rules) {
                log.info("    " + rule);
                log.info("        meta : " + rule.getMetaData());
            }
        }
    }

    public List<String> getRules() {
        List<String> ruleNames = new ArrayList<>();
        final KieBase kieBase = kieContainer.getKieBase();
        Collection<KiePackage> kiePackages = kieBase.getKiePackages();
        log.info("kiePackages : " + kiePackages);
        for (KiePackage kiePackage : kiePackages) {
            log.info("kiePackage : " + kiePackage.getName());
            Collection<Rule> rules = kiePackage.getRules();
            for (Rule rule : rules) {
                ruleNames.add(rule.getName());
            }
        }
        return ruleNames;
    }
}