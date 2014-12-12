package de.htw.sdf.photoplatform;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by vs on 08.12.14.
 */
@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {

    @Value("${email.host}")
    private String host;

    @Value("${email.port}")
    private int port;

    @Value("${email.protocol}")
    private String protocol;

    @Value("${email.enableSTARTTLS}")
    private String enableSTARTTLS;

    @Value("${email.auth}")
    private String auth;

    @Value("${email.encoding}")
    private String encoding;

    @Value("${email.from}")
    private String from;

    @Value("${email.subject}")
    private String subject;

    @Value("${email.username}")
    private String username;

    @Value("${email.password}")
    private String password;




    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setProtocol(protocol);
        javaMailSender.setDefaultEncoding(encoding);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        Properties javaMailProperties = new Properties();
        javaMailProperties.setProperty("mail.smtp.auth", auth);
        javaMailProperties.setProperty("mail.smtp.starttls.enable", enableSTARTTLS);
        javaMailSender.setJavaMailProperties(javaMailProperties);
        return javaMailSender;
    }

    @Bean
    public SimpleMailMessage simpleMailMessage() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setSubject(subject);
        return simpleMailMessage;
    }

}
