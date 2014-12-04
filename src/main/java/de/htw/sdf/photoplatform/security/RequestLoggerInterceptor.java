package de.htw.sdf.photoplatform.security;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by patrick on 03.12.14.
 */
@Component
public class RequestLoggerInterceptor extends HandlerInterceptorAdapter {

    private static final String POST_METHOD = "POST";
    protected Logger log = Logger.getLogger(RequestLoggerInterceptor.class);

    public boolean preHandle(HttpServletRequest request,
        HttpServletResponse response, Object handler) throws Exception {
        log.info(request.getMethod() + " : " + request.getRequestURI());
        if (POST_METHOD.equals(request.getMethod())) {
            Map<String, String[]> params = request.getParameterMap();
            log.info("Request-Data: "+params);
        }
        return true;
    }
}
