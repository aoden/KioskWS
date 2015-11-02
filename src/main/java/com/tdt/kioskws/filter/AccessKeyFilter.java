package com.tdt.kioskws.filter;

import com.tdt.kioskws.repository.ClientRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AccessKeyFilter
 *
 * @author aoden
 */
@Component
public class AccessKeyFilter implements Filter {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        ServletContext sce = servletRequest.getSession().getServletContext();
        String submittedAccessKey = servletRequest.getParameter("key") != null ?
                servletRequest.getParameter("key") :
                servletRequest.getParameter("access_token");
        boolean isNewRequests = servletRequest.getServletPath().equals("/sign_up")
                || servletRequest.getServletPath().equals("/add_key");
        if (StringUtils.isNotEmpty(submittedAccessKey) || isNewRequests) {

            if (servletRequest.getServletPath().equals("/link")
                    || isNewRequests
                    || submittedAccessKey.equals(sce.getAttribute("key"))) {

                chain.doFilter(request, response);
            }
        } else {

            HttpServletResponse servletResponse = (HttpServletResponse) response;
            servletResponse.setStatus(401);
            servletResponse.flushBuffer(); //send response to client
        }
    }

    @Override
    public void destroy() {

    }
}
