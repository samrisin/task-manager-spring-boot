
package com.example.taskmanager.security;

import com.example.taskmanager.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

   @Override
protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain)
        throws ServletException, IOException {

    System.out.println("========== JWT FILTER EXECUTED ==========");
    System.out.println("Request URI: " + request.getRequestURI());

    final String authHeader = request.getHeader("Authorization");

    System.out.println("Authorization header present: "
            + (authHeader != null));

    // Keep the rest of your existing code below

        // No Bearer token: continue the filter chain.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        try {
            String email = jwtService.extractUsername(jwt);
            System.out.println("JWT Email: " + email);
            if (email != null &&
                SecurityContextHolder.getContext()
                        .getAuthentication() == null) {

                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(email);

                if (jwtService.isTokenValid(jwt, userDetails.getUsername())) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                System.out.println("Authentication successfully set");
                System.out.println("Authenticated user: "
        + authentication.getName());

System.out.println("Authorities: "
        + authentication.getAuthorities());
                }
            }

        } catch (io.jsonwebtoken.JwtException |
                 org.springframework.security.core.userdetails.UsernameNotFoundException ex) {

            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}
