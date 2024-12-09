package com.proje.healpoint.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader==null){
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader = authHeader.substring(7);
            System.out.println("Token from header: " + authHeader);
            if (jwtService.isTokenValid(authHeader)) {
                String tc=jwtService.extractTc(authHeader);
                System.out.println("Extracted Tc: " + tc);
                String userType = jwtService.extractUserType(authHeader);
                System.out.println("UserType: " + userType);


                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(tc, null, Collections.emptyList()));
            }

        }
        filterChain.doFilter(request,response);
    }
}
