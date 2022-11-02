package ru.job4j.cinema.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class AuthFilter implements Filter {

    private final Set<String> mappings =
            new HashSet<>(Set.of("mainPage", "loginPage", "login", "fail", "success", "registration", "addUser"));

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (mappingIsPresent(uri) || showPostersWithoutLogin(uri)) {
            chain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/loginPage");
            return;
        }
        chain.doFilter(req, res);
    }

    private boolean mappingIsPresent(String uri) {
        return mappings.stream().anyMatch(uri::endsWith);
    }

    private boolean showPostersWithoutLogin(String uri) {
        return uri.contains("/sessionPoster/");
    }
}
