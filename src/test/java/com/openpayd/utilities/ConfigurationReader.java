package com.openpayd.utilities;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

public class ConfigurationReader {
    private static final EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();

    public static String getProperty(String propertyName) {
        return EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(propertyName);
    }
}
