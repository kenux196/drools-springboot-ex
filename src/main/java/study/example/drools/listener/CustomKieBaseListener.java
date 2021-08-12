/**
 * Project: B.IOT Cloud
 *
 * Copyright (c) 2017 DMC R&D Center, SAMSUNG ELECTRONICS Co.,LTD.
 * (Maetan dong)129, Samsung-ro Yeongtong-gu, Suwon-si. Gyeonggi-do 443-742, Korea
 * All right reserved.
 *
 * This software is the confidential and proprietary information of Samsung Electronics, Inc.
 * ("Confidential Information"). You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement you entered
 * into with Samsung Electronics.
 */
package study.example.drools.listener;

import lombok.extern.slf4j.Slf4j;
import org.kie.api.event.kiebase.*;

@Slf4j
public class CustomKieBaseListener implements KieBaseEventListener {
    @Override
    public void beforeKiePackageAdded(BeforeKiePackageAddedEvent event) {
        log.info("---------------------------------beforeKiePackageAdded");
        event.getKiePackage().getRules().forEach(rule -> log.info(rule.getName()));
    }

    @Override
    public void afterKiePackageAdded(AfterKiePackageAddedEvent event) {
        log.info("---------------------------------afterKiePackageAdded");
        event.getKiePackage().getRules().forEach(rule -> log.info(rule.getName()));
    }

    @Override
    public void beforeKiePackageRemoved(BeforeKiePackageRemovedEvent event) {
        log.info("---------------------------------beforeKiePackageRemoved");
        event.getKiePackage().getRules().forEach(rule -> log.info(rule.getName()));
    }

    @Override
    public void afterKiePackageRemoved(AfterKiePackageRemovedEvent event) {
        log.info("---------------------------------afterKiePackageRemoved");
        event.getKiePackage().getRules().forEach(rule -> log.info(rule.getName()));
    }

    @Override
    public void beforeKieBaseLocked(BeforeKieBaseLockedEvent event) {

    }

    @Override
    public void afterKieBaseLocked(AfterKieBaseLockedEvent event) {

    }

    @Override
    public void beforeKieBaseUnlocked(BeforeKieBaseUnlockedEvent event) {

    }

    @Override
    public void afterKieBaseUnlocked(AfterKieBaseUnlockedEvent event) {

    }

    @Override
    public void beforeRuleAdded(BeforeRuleAddedEvent event) {
        log.info("---------------------------------beforeRuleAdded");
        log.info(event.getRule().getName());
    }

    @Override
    public void afterRuleAdded(AfterRuleAddedEvent event) {
        log.info("---------------------------------afterRuleAdded");
        log.info(event.getRule().getName());
    }

    @Override
    public void beforeRuleRemoved(BeforeRuleRemovedEvent event) {
        log.info("---------------------------------beforeRuleRemoved");
        log.info(event.getRule().getName());
    }

    @Override
    public void afterRuleRemoved(AfterRuleRemovedEvent event) {
        log.info("---------------------------------afterRuleRemoved");
        log.info(event.getRule().getName());
    }

    @Override
    public void beforeFunctionRemoved(BeforeFunctionRemovedEvent event) {
        log.info("---------------------------------beforeFunctionRemoved");
        log.info(event.getFunction());
    }

    @Override
    public void afterFunctionRemoved(AfterFunctionRemovedEvent event) {
        log.info("---------------------------------afterFunctionRemoved");
        log.info(event.getFunction());
    }

    @Override
    public void beforeProcessAdded(BeforeProcessAddedEvent event) {
        log.info("---------------------------------beforeProcessAdded");
        log.info(event.getProcess().getName());
    }

    @Override
    public void afterProcessAdded(AfterProcessAddedEvent event) {
        log.info("---------------------------------afterProcessAdded");
        log.info(event.getProcess().getName());
    }

    @Override
    public void beforeProcessRemoved(BeforeProcessRemovedEvent event) {
        log.info("---------------------------------beforeProcessRemoved");
        log.info(event.getProcess().getName());
    }

    @Override
    public void afterProcessRemoved(AfterProcessRemovedEvent event) {
        log.info("---------------------------------afterProcessRemoved");
        log.info(event.getProcess().getName());
    }
}