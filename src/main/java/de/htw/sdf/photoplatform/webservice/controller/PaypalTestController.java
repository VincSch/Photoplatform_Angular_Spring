package de.htw.sdf.photoplatform.webservice.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.core.rest.PayPalRESTException;

import de.htw.sdf.photoplatform.manager.common.PaypalService;
import de.htw.sdf.photoplatform.webservice.BaseAPIController;
import de.htw.sdf.photoplatform.webservice.Endpoints;

@RestController
public class PaypalTestController extends BaseAPIController {

    @Resource
	private PaypalService paypalService;
	
    @RequestMapping(value = Endpoints.PAYPALTEST_OAUTHTOKEN, method = RequestMethod.GET)
    @ResponseBody
    public String GetPaypalToken() throws PayPalRESTException {
    	return paypalService.getOAuthToken();
    }
}
