/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

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
public class Application extends WebMvcConfigurerAdapter
{

    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(Application.class.getName());
    private static ConfigurableApplicationContext context;

    /**
     * Main method.
     *
     * @param args
     *            the args
     */
    public static void main(final String[] args) {
        context = SpringApplication.run(Application.class);
        DBUtil dbUtil = context.getBean(DBUtil.class);
        dbUtil.insertTestData();
    }

    /**
     * The application needs to be able to determine which locale the application is currently
     * running in.
     * 
     * @return the locale resolver
     */
    @Bean
    public LocaleResolver localeResolver()
    {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.GERMAN);
        return slr;
    }

    /**
     * Configuring an interceptor that is responsible or swapping out the current locale allows for
     * easy testing by a developer, and also gives you the option of including a select list in your
     * UI that lets the user pick the locale they prefer.
     * 
     * @return the local change interceptor
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor()
    {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    /**
     * Add interceptor.
     * 
     * @param registry
     *            the registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(localeChangeInterceptor());
    }

    /**
     * @return the context
     */
    public static ConfigurableApplicationContext getContext() {
        return context;
    }
}
