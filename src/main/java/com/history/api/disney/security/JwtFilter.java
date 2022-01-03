package com.history.api.disney.security;

import com.history.api.disney.dao.UserDao;
import com.history.api.disney.models.User;
import com.history.api.disney.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;


public class JwtFilter extends BasicAuthenticationFilter {

    private JwtService jwtService;

    public JwtFilter(JwtService jwtService, AuthenticationManager auth) {
        super(auth);
        this.jwtService = jwtService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (isEmpty(header) || !header.startsWith("Bearer ")) {
                chain.doFilter(request, response);
                return;
            }

            String token = header.replace("Bearer ", "");
            String userEmail = jwtService.extractUserName(token);
            if(jwtService.hasTokenExpired(token)){
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                chain.doFilter(request,response);
                return;
            }
            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(
                            userEmail,
                            null,
                            new ArrayList<>()));
            chain.doFilter(request, response);
        }catch (SignatureException | ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            chain.doFilter(request, response);
        }
    }


    private boolean isEmpty(String header) {
        return header == null || header.equals("");
    }


}
