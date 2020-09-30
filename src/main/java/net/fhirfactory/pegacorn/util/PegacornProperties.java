package net.fhirfactory.pegacorn.util;

import java.util.Locale;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * Based on ca.uhn.fhir.jpa.starter.HapiProperties, where properties can be overridden with environment variables
 * 
 * @author Jasen Schremmer
 */
public class PegacornProperties {

    private static Properties getProperties() {
        return null; //TODO do we want to load defaults from somewhere?
    }
    
    public static String getProperty(String propertyName) {
        String env = propertyName.toUpperCase(Locale.US);
        env = env.replace(".", "_");
        env = env.replace("-", "_");

        String propertyValue = System.getenv(env);
        if (propertyValue == null) {
            Properties properties = getProperties();
            if (properties != null) {
                propertyValue = properties.getProperty(propertyName);
            }
        }

        return propertyValue == null ? null : propertyValue.trim();
    }

    public static String getProperty(String propertyName, String defaultValue) {
        String value = getProperty(propertyName);

        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }

        return value;
    }

    public static Boolean getBooleanProperty(String propertyName, Boolean defaultValue) {
        String value = getProperty(propertyName);

        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }

        return Boolean.parseBoolean(value);
    }

    public static boolean getBooleanProperty(String propertyName, boolean defaultValue) {
        return getBooleanProperty(propertyName, Boolean.valueOf(defaultValue));
    }

    public static Integer getIntegerProperty(String propertyName, Integer defaultValue) {
        String value = getProperty(propertyName);

        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }

        return Integer.parseInt(value);
    }
    
    /**
     * Sample call is getPropertyEnum("elasticsearch.required_index_status", ElasticsearchIndexStatus.class, ElasticsearchIndexStatus.YELLOW)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T extends Enum> T getPropertyEnum(String thePropertyName, Class<T> theEnumType, T theDefaultValue) {
        String value = getProperty(thePropertyName, theDefaultValue.name());
        return (T) Enum.valueOf(theEnumType, value);
    }    
}
