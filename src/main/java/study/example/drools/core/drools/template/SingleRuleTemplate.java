package study.example.drools.core.drools.template;

import lombok.extern.slf4j.Slf4j;
import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import study.example.drools.core.domain.Device;
import study.example.drools.core.domain.SingleRule;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
@Component
public class SingleRuleTemplate {

    private static class SingleRuleDataProvider implements DataProvider {

        private Iterator<SingleRule> iterator;

        /**
         * Instantiates a new Single status data provider.
         * It will provide the array of data to the Rules Template for creating it.
         *
         * @param rows the rows
         */
        SingleRuleDataProvider(List<SingleRule> rows) {
            this.iterator = rows.iterator();
        }

        public boolean hasNext() {
            return iterator.hasNext();
        }

        public String[] next() {
            SingleRule next = iterator.next();
            return new String[]{
                    String.valueOf(next.getRuleId()),
                    String.valueOf(next.getConditionId()),
                    next.getDeviceId(),
                    String.valueOf(next.getDuration()),
                    next.getOperand(),
                    next.getComparator(),
                    next.getValue(),
                    next.getRuleName(),
                    next.getClassName()};
        }
    }

    public String createRule(SingleRule rule) {

        ArrayList<SingleRule> rules = new ArrayList<>();
        rules.add(rule);

        SingleRuleDataProvider tdp = new SingleRuleDataProvider(rules);
        final DataProviderCompiler converter = new DataProviderCompiler();
        InputStream is = getDroolTemplate("rules/SingleRule.drt");
        final String drl = converter.compile(tdp, is);
        log.info(drl);
        return drl;
    }

    public InputStream getDroolTemplate(String path) {

        Resource resource = new ClassPathResource(path);
        try {
            return resource.getInputStream();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public static HashMap getClassDetails(Device monitor) throws Exception {

        String methodName = null;
        String className = null;
        try {
            for (PropertyDescriptor pd : Introspector.getBeanInfo(Device.class).getPropertyDescriptors()) {
                if (pd.getReadMethod() != null && monitor.toString().equals(pd.getName())) {
                    methodName = pd.getReadMethod().getName() + "()";
                    className = "Device";
                    break;
                }
            }
            if (className != null) {
                HashMap<String, String> classDetails = new HashMap<>();
                classDetails.put("method", methodName);
                classDetails.put("className", className);
                return classDetails;
            }
        } catch (IntrospectionException e) {
            log.error(e.getMessage());
        }
        throw new Exception(monitor.toString() + " invalid monitor");
    }
}
