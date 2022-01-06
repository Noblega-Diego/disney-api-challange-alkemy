package com.history.api.disney.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.history.api.disney.dto.ErrorResponse;
import com.history.api.disney.services.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = "";
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        try {
            token = header.replace("Bearer ", "");
            String userEmail = jwtService.extractUserName(token);
            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(
                            userEmail,
                            null,
                            new ArrayList<>()));
            chain.doFilter(request, response);
        }catch (ExpiredJwtException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write( new ObjectMapper().writeValueAsString(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "token is expired: " + token)));
        }catch (SignatureException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write( new ObjectMapper().writeValueAsString(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), "token invalid")));
        }
    }


    private boolean isEmpty(String header) {
        return header == null || header.equals("");
    }


}
