package de.htw.sdf.photoplatform.manager;

import com.dumbster.smtp.SimpleSmtpServer;
import de.htw.sdf.photoplatform.common.BaseTester;
import de.htw.sdf.photoplatform.persistence.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by vs on 08.12.14.
 */
public class MailServiceTest extends BaseTester {


    @Before
    public void setUp() throws Exception {
        insertTestData();
    }


    /**
     * SimpleSmtpServer is used as a Mailserver-Mock and startet on port 1313
     * on unix systems its requires root privileges to open a port < 1024
     * The same port needs to be specified in the mail.properties file /test/resources
     */
    @Test
    public void sendBasicTestMail() {
        SimpleSmtpServer server = SimpleSmtpServer.start(1313);
        User user = new User();
        user.setUsername("testnutzer@testwebsite.com");
        mailService.sendMail(user, "Test Mail", "Test message");
        Assert.assertEquals(true, server.getReceivedEmailSize() == 1);
        Assert.assertEquals(false, mailService.sendMail(user, null, "Test message"));
        Assert.assertEquals(false, mailService.sendMail(null, "Test Mail", "Test message"));
        Assert.assertEquals(false, mailService.sendMail(user, "Test Mail", null));
        Assert.assertEquals(true, server.getReceivedEmailSize() == 1);
        server.stop();
    }


    @After
    public void tearDown() throws Exception {
        clearTables();
    }
}
