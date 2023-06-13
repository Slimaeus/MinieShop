package com.master.minieshop;

import com.master.minieshop.entity.AppUser;
import com.master.minieshop.enumeration.Role;
import com.master.minieshop.service.CustomUserDetailsService;
import com.master.minieshop.service.OAuthService;
import com.master.minieshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private OAuthService oAuthService;
    @Autowired
    private UserService userService;
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
            Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/css/**", "/js/**", "/pictures/**", "/", "/register",
                                "/error", "", "/home", "/home/about")
                        .permitAll()

                        // Test
                        .requestMatchers("/orders/**")
                        .permitAll()

                        .requestMatchers( "/admin/**")

                        .hasAnyAuthority("Manager")

                        .anyRequest().permitAll()

                )
                .oauth2Login(oauth2Login -> oauth2Login.loginPage("/login")
                        .failureUrl("/login?error")
                        .userInfoEndpoint(userInfoEndpoint ->
                                userInfoEndpoint
                                        .userService(oAuthService)
                        )
                        .successHandler(
                                (request, response,
                                 authentication) -> {
                                    var oidcUser = (DefaultOidcUser) authentication.getPrincipal();

                                    if (userService.findByUsername(oidcUser.getName()) == null) {
                                        AppUser user = new AppUser();
                                        user.setUserName(oidcUser.getName());
                                        user.setEmail(oidcUser.getEmail());
                                        user.setFullName(oidcUser.getFullName());
                                        user.setPassword(new BCryptPasswordEncoder().encode(oidcUser.getName()));
                                        user.setRole(Role.LoyalCustomer);
                                        userService.save(user);
                                    };

                                    response.sendRedirect("/");
                                }
                        )
                        .permitAll())
                .logout(logout -> logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)

                        .permitAll()

                )
                .formLogin(formLogin -> formLogin.loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")

                        .permitAll()

                )
                .rememberMe(rememberMe -> rememberMe.key("uniqueAndSecret")
                        .tokenValiditySeconds(86400)
                        .userDetailsService(userDetailsService())
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedPage("/403"))
                .build();
    }

}
