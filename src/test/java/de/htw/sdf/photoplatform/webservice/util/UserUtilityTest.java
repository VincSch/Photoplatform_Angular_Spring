/**
 *
 */
package de.htw.sdf.photoplatform.webservice.util;

import de.htw.sdf.photoplatform.common.BaseTester;
import org.junit.Test;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * Test class for UserUtility.
 *
 * @author Sergej Meister
 */
public class UserUtilityTest extends BaseTester {

    @Test
    public void testValidateProfileData() {
        Locale test = LocaleContextHolder.getLocale();

//        UserProfileData userProfileData = new UserProfileData();
//        try {
//            UserUtility.getInstance().validate(userProfileData);
//            Assert.fail("Should be an exception");
//        } catch (AbstractBaseException e) {
//            Assert.assertNotNull(e);
//        }
//
//        userProfileData.setEmail("sergej@test.de");
//        try {
//            UserUtility.getInstance().validate(userProfileData);
//        } catch (AbstractBaseException e) {
//            Assert.fail("Should be no exception!");
//        }
//
//        userProfileData.setEmail("sergej@testde");
//        try {
//            UserUtility.getInstance().validate(userProfileData);
//            Assert.fail("Should be an exception");
//        } catch (AbstractBaseException e) {
//            Assert.assertNotNull(e);
//        }
//
//        userProfileData.setEmail("sergejtest.de");
//        try {
//            UserUtility.getInstance().validate(userProfileData);
//            Assert.fail("Should be an exception");
//        } catch (AbstractBaseException e) {
//            Assert.assertNotNull(e);
//        }
//
//        userProfileData.setEmail("sergej@test.de");
//        userProfileData.setBirthday("2014-1123");
//        try {
//            UserUtility.getInstance().validate(userProfileData);
//            Assert.fail("Should be an exception");
//        } catch (AbstractBaseException e) {
//            Assert.assertNotNull(e);
//        }
//
//        userProfileData.setEmail("sergej@test.de");
//        userProfileData.setBirthday("2014-11-23");
//        try {
//            UserUtility.getInstance().validate(userProfileData);
//        } catch (AbstractBaseException e) {
//            Assert.fail("Should be no exception");
//        }
    }
}
