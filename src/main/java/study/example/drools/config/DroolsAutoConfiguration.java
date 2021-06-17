package study.example.drools.config;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.spring.KModuleBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

/**
 * <pre>
 * 서비스 명   : drools
 * 패키지 명   : study.example.drools.config
 * 클래스 명   : TaxiFareConfiguration
 * 설명       :
 *
 * ====================================================================================
 *
 * </pre>
 *
 * @author skyun
 * @version 1.0.0
 * @date 2021-06-17
 **/

@Configuration
//@ComponentScan("study.example.drools")
public class DroolsAutoConfiguration {
//    public static final String drlFile = "rules/TAXI_FARE_RULE.drl";
    public static final String RULES_PATH = "rules/";

    @Bean
    @ConditionalOnMissingBean(KieFileSystem.class)
    public KieFileSystem kieFileSystem() throws IOException {
        KieFileSystem kieFileSystem = getKieServices().newKieFileSystem();
        for (Resource file : getRuleFiles()) {
            kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH + file.getFilename(), "UTF-8"));
        }
        return kieFileSystem;
    }

    private Resource[] getRuleFiles() throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        return resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "**/*.*");
    }

    @Bean
    @ConditionalOnMissingBean(KieContainer.class)
    public KieContainer kieContainer() throws IOException {
        final KieRepository kieRepository = getKieServices().getRepository();

        kieRepository.addKieModule(new KieModule() {
            public ReleaseId getReleaseId() {
                return kieRepository.getDefaultReleaseId();
            }
        });

        KieBuilder kieBuilder = getKieServices().newKieBuilder(kieFileSystem());
        kieBuilder.buildAll();

        KieContainer kieContainer=getKieServices().newKieContainer(kieRepository.getDefaultReleaseId());

        return kieContainer;
    }

    private KieServices getKieServices() {
        return KieServices.Factory.get();
    }

    @Bean
    @ConditionalOnMissingBean(KieBase.class)
    public KieBase kieBase() throws IOException {
        return kieContainer().getKieBase();
    }

    @Bean
    @ConditionalOnMissingBean(KieSession.class)
    public KieSession kieSession() throws IOException {
        return kieContainer().newKieSession();
    }

    @Bean
    @ConditionalOnMissingBean(KModuleBeanFactoryPostProcessor.class)
    public KModuleBeanFactoryPostProcessor kiePostProcessor() {
        return new KModuleBeanFactoryPostProcessor();
    }

//    @Bean
//    public KieContainer kieContainer() {
//        KieServices kieServices = KieServices.Factory.get();
//
//        // 클래스 경로에 있는 모든 규칙 파일을 컴파일하고,
//        // 그 결과인 KieModule 객체를 KieContainer 에 추가한다.
//        return kieServices.getKieClasspathContainer();
//
//        KieBase kBase = KnowledgeBaseFactory.newKnowledgeBase();
//        kSession = kBase.newStatelessKnowledgeSession();
//        KieBase base = kSession.getKieBase();
//        kieSession = base.newKieSession();
////        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
////        kieFileSystem.write(ResourceFactory.newClassPathResource(drlFile));
////        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
////        kieBuilder.buildAll();
////        KieModule kieModule = kieBuilder.getKieModule();
////
////        return kieServices.newKieContainer(kieModule.getReleaseId());
//    }
}