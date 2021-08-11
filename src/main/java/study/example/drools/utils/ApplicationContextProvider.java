package study.example.drools.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 서비스 명   : drools
 * 패키지 명   : study.example.drools.utils
 * 클래스 명   : ApplicaltionContextProvider
 * 설명       :
 *
 * ====================================================================================
 *
 * </pre>
 **/
@Component
public class ApplicationContextProvider implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        applicationContext = ctx;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}