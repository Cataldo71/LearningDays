package com.autodesk.ic.content.application;


import java.io.File;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.EnvironmentConfig;
import org.glassfish.jersey.jettison.JettisonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import com.autodesk.ic.content.rest.WebService;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.validation.ValidationConfig;

import com.sleepycat.je.Environment;
/**
 * Created by cataldp on 1/22/2015.
 */

public class ContentApplication extends ResourceConfig {

    private static Logger logger = Logger.getLogger(ContentApplication.class.getSimpleName());

    public ContentApplication() {
        register(JettisonFeature.class);
        register(WebService.class);
        register(ValidationConfig.class);
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.setMapper(mapper);
        register(provider);

        try {
            // create a configuration for DB environment
            EnvironmentConfig envConf = new EnvironmentConfig();
            // environment will be created if not exists
            //envConf.setAllowCreate(true);

            // open/create the DB environment using config
            Environment dbEnv = new Environment(
                    new File("/berkeleydb"), envConf);

        } catch (DatabaseException dbe) {
            System.out.println("Error :" + dbe.getMessage() );
        }
    }
}