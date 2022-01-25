package com.openpayd.APITest;

import com.openpayd.utilities.ConfigurationReader;
import net.serenitybdd.rest.SerenityRest;
import org.apache.log4j.Logger;

public class BaseAPI extends SerenityRest {
    private static final Logger logger = Logger.getLogger(BaseAPI.class);
    protected final static String BASE_URI;

    static {
        BASE_URI = ConfigurationReader.getProperty("api.url");
        logger.info("API endpoint: " + BASE_URI);
    }
}
