package com.example.jwtauth.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
String jwtToken=parseJwt(request);
        //reguestin içinden gelen tokeni alma
        try {
            if (jwtToken!=null&&jwtUtils.validateToken(jwtToken)) {
                String userName=jwtUtils.getUserNameFromJwtToken(jwtToken);
                UserDetails userDetails =userDetailsService.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken authenticationToken=
                        new UsernamePasswordAuthenticationToken (userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (UsernameNotFoundException e) {
            throw new RuntimeException(e);
        }
        filterChain.doFilter(request,response);

    }

    private String parseJwt(HttpServletRequest request){
        //reguestin header kısmındaki Authorization
        String header=request.getHeader("Authorization");
        if (StringUtils.hasText(header)&&header.startsWith("Bearer")){
            return header.substring(7);
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher antPathMatcher=new AntPathMatcher();
        return antPathMatcher.match("/register",request.getServletPath())||
                antPathMatcher.match("/login",request.getServletPath());
    }
}
