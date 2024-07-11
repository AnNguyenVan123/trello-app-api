package app.security_filter;

import app.service.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class UserFilter extends OncePerRequestFilter {
    private final UserService userService;

    public UserFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        request.getRequestURI();
        String requestURI = request.getRequestURI();
        Long userId = Long.parseLong(requestURI.substring(requestURI.lastIndexOf("/") + 1));
        if (authentication != null) {

            if (!authentication.getName().equals(userService.getById(userId).getAccount())) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Not right user");
            }

        }
        filterChain.doFilter(request, response);
    }
}
