package app.config;

import app.security_filter.HouseOwnerFilter;
import app.security_filter.JwtRequestFilter;
import app.security_filter.UserFilter;
import app.service.house.HouseService;
import app.service.user.UserService;
import app.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JWTUtil jwtUtil;
    @Autowired
    UserService userService;
    @Autowired
    HouseService houseService;

    @Bean
    public SecurityFilterChain filterChain1(HttpSecurity http) throws Exception {
        http.securityMatcher("/users/login")
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
    @Bean
    public SecurityFilterChain filterChain6(HttpSecurity http) throws Exception {
        http.securityMatcher("/users/register")
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
    @Bean
    public SecurityFilterChain filterChain4(HttpSecurity http) throws Exception {
        http.securityMatcher("/users/auth")
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtRequestFilter(userDetailsService, jwtUtil), CsrfFilter.class);
        return http.build();
    }

    @Bean
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
        http.securityMatcher("/users/{id}")
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtRequestFilter(userDetailsService, jwtUtil), CsrfFilter.class)
                .addFilterAfter(new UserFilter(userService), JwtRequestFilter.class);
        return http.build();
    }

//    @Bean
//    public SecurityFilterChain filterChain3(HttpSecurity http) throws Exception {
//        http.securityMatcher("/houses/{id}")
//                .csrf(AbstractHttpConfigurer::disable)
//                .addFilterBefore(new JwtRequestFilter(userDetailsService, jwtUtil), CsrfFilter.class)
//                .addFilterAfter(new HouseOwnerFilter(houseService), JwtRequestFilter.class);
//        return http.build();
//    }



}