package de.htw.sdf.photoplatform.manager.common;

import de.htw.sdf.photoplatform.persistence.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Vincent Schwarzer on 05.12.14.
 */
@Service
@Transactional
public class MailService extends DAOReferenceCollector {

    private Logger log = Logger.getLogger(this.getClass().getName());

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SimpleMailMessage templateMessage;

    public boolean sendSalesConfirmationMail() {
        return true;
    }

    public boolean sendPasswordRecoveryMail() {
        return true;
    }


    public boolean sendTestMailTo(User user) {
        SimpleMailMessage msg = new SimpleMailMessage(templateMessage);
        String email = user.getUsername();
        msg.setTo(email);
        msg.setText(generateTestMailTxt());
        try {
            this.mailSender.send(msg);
        } catch (MailException ex) {
            log.info(ex.getMessage());
            return false;
        }
        return true;
    }

    private String generateTestMailTxt() {
        StringBuffer buffer = new StringBuffer(20);
        buffer.append("Test Mail");
        return buffer.toString();
    }
}
