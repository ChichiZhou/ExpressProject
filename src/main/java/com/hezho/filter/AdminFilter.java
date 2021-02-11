package com.hezho.filter;

import com.hezho.util.UserUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({"/admin/views/*", "/admin/index.html"})
public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)req).getSession();
        String admin = UserUtil.getAdmin(session);
        System.out.println("The admin is " + admin);
        String requestURI = ((HttpServletRequest) req).getRequestURI();
        System.out.println("The requestURI is " + requestURI);
        System.out.println("getContextPath 的结果为 " + ((HttpServletRequest) req).getContextPath());
        if(admin != null)
            chain.doFilter(req, resp);
        else
            ((HttpServletResponse)resp).sendRedirect(((HttpServletRequest) req).getContextPath() + "/index.jsp");
    }

    @Override
    public void destroy() {

    }
}
