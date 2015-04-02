package com.autodesk.ic.content.filters;

/**
 * Created by cataldp on 1/22/2015.
 */
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ContentServletFilter implements Filter {

    public static final String SESSION_ATTR_KEY_USER_ID = "UserId";
    public static final String SESSION_ATTR_KEY_SERVICE_KEY = "ServiceKey";
    public static final String SESSION_ATTR_KEY_USER_NAME = "UserName";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        //////////
        // This is a HUGE security hole to get around CORS! For our prototype it will be fine
        // but this could never be part of production code!
        //////////
        HttpServletResponse resp = (HttpServletResponse)response;
        resp.setHeader("Access-Control-Allow-Origin","*");
        resp.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
        chain.doFilter(request, response);
    }

}