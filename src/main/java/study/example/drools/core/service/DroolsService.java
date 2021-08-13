package study.example.drools.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.definition.KnowledgePackage;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatelessKnowledgeSession;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import study.example.drools.core.domain.ConditionDevice;
import study.example.drools.core.domain.Device;
import study.example.drools.core.domain.SingleStatusRule;
import study.example.drools.core.domain.TempSensor;
import study.example.drools.core.drools.listener.CustomAgendaEventListener;
import study.example.drools.core.drools.listener.CustomKieBaseListener;
import study.example.drools.core.drools.listener.CustomProcessEventListener;
import study.example.drools.core.drools.listener.CustomRuleRunTimeEventListener;
import study.example.drools.core.drools.template.SingleStatusTemplate;
import study.example.drools.core.repository.DeviceRepository;
import study.example.drools.core.repository.RuleConditionDeviceRepository;
import study.example.drools.core.repository.RuleConditionRepository;

import javax.annotation.PostConstruct;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class DroolsService {

    private KnowledgeBase kBase;
    private StatelessKnowledgeSession kSession;
    private KieServices kieServices;
    private KieSession kieSession;
    private final SingleStatusTemplate singleStatusTemplate;

    private ConcurrentHashMap<Long, FactHandle> factMapForDevice;

    private final RuleConditionRepository ruleConditionRepository;
    private final RuleConditionDeviceRepository ruleConditionDeviceRepository;
    private final DeviceRepository deviceRepository;

    @PostConstruct
    private void initService() {
        kBase = KnowledgeBaseFactory.newKnowledgeBase();
        kSession = kBase.newStatelessKnowledgeSession();
        KieBase base = kSession.getKieBase();
        kieSession = base.newKieSession();
//        kieServices = KieServices.Factory.get();

        kieSession.addEventListener(new CustomAgendaEventListener());
        kieSession.addEventListener(new CustomRuleRunTimeEventListener());
        kieSession.addEventListener(new CustomProcessEventListener());
        kieSession.getKieBase().addEventListener(new CustomKieBaseListener());

        factMapForDevice = new ConcurrentHashMap<>();
    }

    private String createRule(SingleStatusRule singleStatusRule) {
        return singleStatusTemplate.createRule(singleStatusRule);
    }

    public void createDroolsRule(study.example.drools.core.domain.Rule rule) {
        log.info("createDroolsRule~~~~~~");
        rule.getConditions().forEach(condition -> {
            final ConditionDevice conditionDevice = condition.getConditionDevices().get(0);
            final Long deviceId = conditionDevice.getDevice().getDeviceId();
            final SingleStatusRule singleStatusRule = SingleStatusRule.builder()
                    .className(Device.class.getSimpleName())
                    .deviceId(String.valueOf(deviceId))
                    .ruleId(rule.getRuleId())
                    .ruleName("Test Rule - " + rule.getRuleId() + " - " + deviceId)
                    .conditionId(condition.getId())
                    .duration(null)
                    .value(String.valueOf(condition.getValue()))
                    .comparator(condition.getComparator())
                    .operand(condition.getOperand())
                    .build();
            addRule(singleStatusRule);
        });
    }

     public boolean addRule(SingleStatusRule singleStatusRule) {
        log.info("addRule~~~~~~~~~~~~~~~");
        final String rule = createRule(singleStatusRule);
        Resource myResource = ResourceFactory.newReaderResource(new StringReader(rule));
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        builder.add(myResource, ResourceType.DRL);
        if (builder.hasErrors()) {
            KnowledgeBuilderErrors error = builder.getErrors();
            log.error(error.toString());
            return false;
        }
        Collection<KnowledgePackage> packages = builder.getKnowledgePackages();
        kBase.addKnowledgePackages(packages);
        return true;
    }

    public void validateRule(TempSensor tempSensor) {
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

    public List<String> getRules() {
        List<String> ruleNames = new ArrayList<>();
        Collection<KiePackage> kiePackages = kBase.getKiePackages();
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

    @Scheduled(initialDelay = 20000L, fixedDelay = 20000L)
    public void validateRules() {
        log.info("validateRules ~~~~ run");
        Set<Long> conditionDevices = ruleConditionDeviceRepository.findAllConditionDevice();
        final Set<Device> devices = new HashSet<>(deviceRepository.findAllById(conditionDevices));
        executeDevice(devices);
        fireAllRules();
    }

    public void validateForce() {
        validateRules();
    }

    public boolean executeDevice(Set<Device> devices) {
        try {
            // Updating the facts in current session.
            for (Device object : devices) {
                FactHandle fact;
                if (factMapForDevice.containsKey(object.getDeviceId())) {
                    fact = factMapForDevice.get(object.getDeviceId());
                    kieSession.update(fact, object);
                } else {
                    fact = kieSession.insert(object);
                }
                factMapForDevice.put(object.getDeviceId(), fact);
            }
            return true;
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
        }
        return false;
    }
}