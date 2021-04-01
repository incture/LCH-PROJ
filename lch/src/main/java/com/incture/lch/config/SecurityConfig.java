package com.incture.lch.config;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.Jwt;

 

import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
import com.sap.cloud.security.xsuaa.token.TokenAuthenticationConverter;

 

@Configuration
@EnableWebSecurity
@Profile("security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

 

    @Autowired
    XsuaaServiceConfiguration xsuaaServiceConfiguration;

 

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers("/lch/**").permitAll().anyRequest().authenticated().and().oauth2ResourceServer().jwt()
                .jwtAuthenticationConverter(getJwtAuthenticationConverter());*/
        
        http.csrf().disable()
        //no authentication needed for these context paths
        .authorizeRequests()
        .antMatchers("/updateApprovalWorkflowDetails").permitAll()
        .antMatchers("/LCH/**").permitAll()
        .antMatchers("/swagger-ui.html/**").permitAll()
        .antMatchers("/lch/**").permitAll();
        //http.csrf().disable();

 

    }

 

    Converter<Jwt, AbstractAuthenticationToken> getJwtAuthenticationConverter() {
        TokenAuthenticationConverter converter = new TokenAuthenticationConverter(xsuaaServiceConfiguration);
        converter.setLocalScopeAsAuthorities(true);
        return converter;
    }
    
      @Override
      public void configure(WebSecurity webSecurity) throws Exception
        {
         webSecurity
         .ignoring()
          // All of Spring Security will ignore the requests
          .antMatchers("/lch/**").antMatchers("/LCH/**");
         }  

    

}