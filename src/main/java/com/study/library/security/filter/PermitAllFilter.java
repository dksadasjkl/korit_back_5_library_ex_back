package com.study.library.security.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class PermitAllFilter extends GenericFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        List<String> antMatchers = List.of("error", "/server", "/auth");

        String url = request.getRequestURI();
        request.setAttribute("isPermitAll", false);
        
        for(String antMatcher : antMatchers) {
            if (url.startsWith(antMatcher)) { // 시작 확인
                request.setAttribute("isPermitAll", true);
            }
        }
        filterChain.doFilter(request, response);
    }
}