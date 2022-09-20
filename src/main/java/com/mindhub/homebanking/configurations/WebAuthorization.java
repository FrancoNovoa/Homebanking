package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
class WebAuthorization extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/web/index.html").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients/current/accounts").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.POST, "/api/clients/current/cards").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/clients/current/cards").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.PATCH, "/api/clients/current/accounts").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers(HttpMethod.POST,"/api/transactions").hasAnyAuthority("CLIENT", "ADMIN")
                .antMatchers(HttpMethod.POST,"/api/loans").hasAnyAuthority("CLIENT", "ADMIN")
                .antMatchers(HttpMethod.POST,"/api/transactions/filtered").hasAnyAuthority("CLIENT", "ADMIN")
                .antMatchers(HttpMethod.POST,"/api/transactions/payment").permitAll()
                .antMatchers("/web/assets/style.css", "/web/assets/index.js", "/web/assets/account.js", "/web/assets/accounts.js", "/web/assets/cards.js", "/web/assets/img/**").permitAll()
                .antMatchers("/web/assets/manager.js").hasAuthority("ADMIN")
                .antMatchers("/web/manager.html").hasAuthority("ADMIN")
                .antMatchers("/web/**").hasAnyAuthority("CLIENT", "ADMIN");
        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");
        http.logout().logoutUrl("/api/logout");
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) ->
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null){
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
