/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * This class represents the application config, which is done in java instead
 * of XML.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 *
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@ImportResource("classpath:applicationContext.xml")
public class Application {

    static Logger log = Logger.getLogger(Application.class.getName());
    private static ConfigurableApplicationContext context;

    /**
     * Main method.
     * 
     * @param args
     *            the args
     */
    public static void main(String[] args) {
        context = SpringApplication.run(Application.class);
        DBUtil dbUtil = context.getBean(DBUtil.class);
        dbUtil.insertTestData();
    }

    /**
     * @return the context
     */
    public static ConfigurableApplicationContext getContext() {
        return context;
    }
}
