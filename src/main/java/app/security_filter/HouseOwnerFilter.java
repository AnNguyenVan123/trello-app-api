package app.security_filter;

import app.model.sql_models.entities.House;
import app.service.house.HouseService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class HouseOwnerFilter extends OncePerRequestFilter {
    private final HouseService houseService;

    public HouseOwnerFilter(HouseService houseService) {
        this.houseService = houseService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        request.getRequestURI();
        String requestURI = request.getRequestURI();
        Long houseId = Long.parseLong(requestURI.substring(requestURI.lastIndexOf("/") + 1));
        if (authentication != null) {
            String userName = authentication.getName();
            House house = houseService.getById(houseId);
            if (!houseService.IsOwner(house, userName)) {
                  response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Not owner of the house");
            }
        }
        filterChain.doFilter(request, response);
    }
}
