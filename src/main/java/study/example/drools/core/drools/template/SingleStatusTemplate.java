package study.example.drools.core.drools.template;

import lombok.extern.slf4j.Slf4j;
import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import study.example.drools.core.domain.SingleStatusRule;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
public class SingleStatusTemplate {

    private static class SingleStatusDataProvider implements DataProvider {

        private Iterator<SingleStatusRule> iterator;

        /**
         * Instantiates a new Single status data provider.
         * It will provide the array of data to the Rules Template for creating it.
         *
         * @param rows the rows
         */
        SingleStatusDataProvider(List<SingleStatusRule> rows) {
            this.iterator = rows.iterator();
        }

        public boolean hasNext() {
            return iterator.hasNext();
        }

        public String[] next() {
            SingleStatusRule next = iterator.next();
            return new String[]{
                    String.valueOf(next.getRuleId()),
                    String.valueOf(next.getConditionId()),
                    next.getDeviceId(),
                    String.valueOf(next.getDuration()),
                    next.getOperand(),
                    next.getGetterOperand(),
                    next.getComparator(),
                    next.getValue(),
                    next.getRuleName(),
                    next.getClassName()};
        }
    }

    public String createRule(SingleStatusRule rule) {

        ArrayList<SingleStatusRule> rules = new ArrayList<>();
        rules.add(rule);

        SingleStatusDataProvider tdp = new SingleStatusDataProvider(rules);
        final DataProviderCompiler converter = new DataProviderCompiler();
        InputStream is = getDroolTemplate("rules/SingleStatus.drt");
        // Compile the rule to create it
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
}
