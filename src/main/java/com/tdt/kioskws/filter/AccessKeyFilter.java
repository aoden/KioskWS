package com.tdt.kioskws.filter;

import com.tdt.kioskws.repository.ClientRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * AccessKeyFilter
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
        HttpSession session = servletRequest.getSession();
        String submittedAccessKey = servletRequest.getParameter("key");
        if (StringUtils.isNotEmpty(submittedAccessKey)) {

            if (servletRequest.getServletPath().equals("/link") || submittedAccessKey.equals(session.getAttribute("key"))) {

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
