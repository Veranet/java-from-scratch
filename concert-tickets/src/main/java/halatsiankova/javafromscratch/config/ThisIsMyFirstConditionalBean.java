package halatsiankova.javafromscratch.config;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ThisIsMyFirstConditionalBean {
    private static final Logger LOGGER = Logger.getLogger(ThisIsMyFirstConditionalBean.class.getSimpleName());

    public ThisIsMyFirstConditionalBean(String conditionalString) {
        LOGGER.log(Level.INFO, conditionalString);
    }
}
