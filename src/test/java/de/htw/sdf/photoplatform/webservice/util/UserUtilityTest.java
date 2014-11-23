/**
 *
 */
package de.htw.sdf.photoplatform.webservice.util;

import org.junit.Assert;
import org.junit.Test;

import de.htw.sdf.photoplatform.common.BaseTester;
import de.htw.sdf.photoplatform.exception.common.AbstractBaseException;
import de.htw.sdf.photoplatform.webservice.dto.UserProfileData;

/**
 * Test class for UserUtility.
 *
 * @author Sergej Meister
 */
public class UserUtilityTest extends BaseTester {

    @Test
    public void testValidateProfileData(){
        UserProfileData userProfileData = new UserProfileData();
        try {
            UserUtility.getInstance().validate(userProfileData);
            Assert.fail("Should be an exception");
        } catch (AbstractBaseException e) {
            Assert.assertNotNull(e);
        }

        userProfileData.setEmail("sergej@test.de");
        try {
            UserUtility.getInstance().validate(userProfileData);
        } catch (AbstractBaseException e) {
            Assert.fail("Should be no exception!");
        }

        userProfileData.setEmail("sergej@testde");
        try {
            UserUtility.getInstance().validate(userProfileData);
            Assert.fail("Should be an exception");
        } catch (AbstractBaseException e) {
            Assert.assertNotNull(e);
        }

        userProfileData.setEmail("sergejtest.de");
        try {
            UserUtility.getInstance().validate(userProfileData);
            Assert.fail("Should be an exception");
        } catch (AbstractBaseException e) {
            Assert.assertNotNull(e);
        }

        userProfileData.setEmail("sergej@test.de");
        userProfileData.setBirthday("23.112014");
        try {
            UserUtility.getInstance().validate(userProfileData);
            Assert.fail("Should be an exception");
        } catch (AbstractBaseException e) {
            Assert.assertNotNull(e);
        }

        userProfileData.setEmail("sergej@test.de");
        userProfileData.setBirthday("23.11.2014");
        try {
            UserUtility.getInstance().validate(userProfileData);
        } catch (AbstractBaseException e) {
            Assert.fail("Should be no exception");
        }
    }
}
