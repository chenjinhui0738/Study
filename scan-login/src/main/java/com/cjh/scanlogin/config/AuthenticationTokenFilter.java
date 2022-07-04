package com.cjh.scanlogin.config;

import com.cjh.scanlogin.service.JwtTokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Token 校验过滤器
 *
 * @author Guoqing
 */
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader("Authorization");

        if (Objects.isNull(authToken)) {
            chain.doFilter(request, response);
            return;
        }

        String username = StringUtils.isNotBlank(authToken) ? jwtTokenService.getUsernameFromToken(authToken) : null;

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null && !jwtTokenService.isTokenExpired(authToken)) {
            UserDetails userDetails = jwtTokenService.getUserDetails(authToken);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}