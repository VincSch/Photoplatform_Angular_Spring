/*
 *
 * Copyright (C) 2014
 *
 */

package de.htw.sdf.photoplatform;

import de.htw.sdf.photoplatform.common.StartUpUtil;
import de.htw.sdf.photoplatform.manager.ImageSearchManager;
import de.htw.sdf.photoplatform.security.RequestLoggerInterceptor;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.log4j.Logger;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

/**
 * This class represents the application config, which is done in java instead
 * of XML.
 *
 * @author <a href="mailto:s0541962@htw-berlin.de">Vincent Schwarzer</a>
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
@ImportResource("classpath:applicationContext.xml")
public class Application extends WebMvcConfigurerAdapter {

    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(Application.class.getName());
    private static ConfigurableApplicationContext context;

    /**
     * Main method.
     *
     * @param args the args
     */
    public static void main(final String[] args) {
        context = SpringApplication.run(Application.class);
        context.getBean(StartUpUtil.class).cleanUploadDirectories();
        //init elastic search indexes, for all images in db.
        //I know, that is no a good idea, but for study project is ok!
        //we don't have a lot of images!
        ImageSearchManager imageSearchManager = context.getBean(ImageSearchManager.class);
        //delete image indexes.
        imageSearchManager.deleteIndexes();
        // create indexes
        imageSearchManager.initIndexes();
    }

    /**
     * @return the context
     */
    public static ConfigurableApplicationContext getContext() {
        return context;
    }

    /**
     * The application needs to be able to determine which locale the
     * application is currently running in.
     *
     * @return the locale resolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.GERMAN);
        return slr;
    }

    /**
     * Configuring an interceptor that is responsible or swapping out the
     * current locale allows for easy testing by a developer, and also gives you
     * the option of including a select list in your UI that lets the user pick
     * the locale they prefer.
     *
     * @return the local change interceptor
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    /**
     * Add interceptor.
     *
     * @param registry the registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(new RequestLoggerInterceptor());
    }

    /**
     * Create MultipartConfigElement.
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("128MB");
        factory.setMaxRequestSize("128MB");
        return factory.createMultipartConfig();
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.addAdditionalTomcatConnectors(createSslConnector());
        return tomcat;
    }

    /**
     * Init elasticsearch client and returns ElasticsearchTemplate.
     *
     * @return default ElasticsearchTemplate
     */
    @Bean
    public ElasticsearchTemplate elasticSearchTemplate() {
        return new ElasticsearchTemplate(nodeBuilder().local(true).node().client());
    }

    private Connector createSslConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        try {
            File keystore = new ClassPathResource("keystore.p12").getFile();
            //File truststore = new ClassPathResource("keystore").getFile();
            connector.setScheme("https");
            connector.setSecure(true);
            connector.setPort(8555);
            protocol.setSSLEnabled(true);
            protocol.setKeystoreFile(keystore.getAbsolutePath());
            protocol.setKeystorePass("photoplatform");
            protocol.setKeystoreType("PKCS12");
            //protocol.setTruststoreFile(truststore.getAbsolutePath());
            //protocol.setTruststorePass("changeit");
            protocol.setKeyAlias("tomcat");
            return connector;
        } catch (FileNotFoundException ex) {
            throw new IllegalStateException("can't access keystore: [" + "keystore"
                    + "] or truststore: [" + "keystore" + "]", ex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connector;
    }
}
