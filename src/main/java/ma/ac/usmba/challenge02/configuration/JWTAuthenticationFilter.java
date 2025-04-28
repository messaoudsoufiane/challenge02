package ma.ac.usmba.challenge02.configuration;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ma.ac.usmba.challenge02.services.jwt.UserService;
import ma.ac.usmba.challenge02.utils.JWTUtils;
import ma.ac.usmba.challenge02.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtils;

    private final UserService userService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if(StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ") ){
            filterChain.doFilter(request, response);
            return ;

        }
        jwt=authHeader.substring(7);
        userEmail=jwtUtils.extractUsername(jwt);
        if(StringUtils.isNoneEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
           UserDetails userDetails =userService.userDetailsService().loadUserByUsername(userEmail);
           if(jwtUtils.validateToken(jwt ,userDetails.getUsername())){
               SecurityContext context= SecurityContextHolder.createEmptyContext();
               UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                       new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
               usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               context.setAuthentication(usernamePasswordAuthenticationToken);
               SecurityContextHolder.setContext(context);
           }
        }
         filterChain.doFilter(request, response);

    }
}
