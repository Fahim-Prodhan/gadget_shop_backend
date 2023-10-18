package com.fahim.Ecommerce.config;

import com.fahim.Ecommerce.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static String CURRENT_USER = "";

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestHeader = request.getHeader("Authorization");
        String username = null;
        String jwtToken = null;


        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {

            jwtToken = requestHeader.substring(7);

            try {

                username = this.jwtUtils.extractUsername(jwtToken);
                CURRENT_USER = username;

            } catch (ExpiredJwtException exception) {
                exception.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }

        }else {
            System.out.println("Invalid Token!!!!");
            System.out.println(requestHeader);
        }

        //validate
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (this.jwtUtils.validateToken(jwtToken,userDetails)) {
                // token is validate
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }else {
            System.out.println("Token is not Valid !!!!");
        }

        filterChain.doFilter(request,response);


    }
}
