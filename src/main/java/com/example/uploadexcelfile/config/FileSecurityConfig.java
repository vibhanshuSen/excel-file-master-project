package com.example.uploadexcelfile.config;

import com.example.uploadexcelfile.constants.ConstantsUtils;
import com.example.uploadexcelfile.model.UserDetails;
import com.example.uploadexcelfile.utils.UserHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@EnableWebSecurity
public class FileSecurityConfig extends WebSecurityConfigurerAdapter {

    // Function to encode the password
    // assign to the particular roles.
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * This helper method is used to set up user authentication details
     *
     * @param authenticationManagerBuilder : authenticationManagerBuilder
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        List<UserDetails> userDetails = UserHelper.getUsers();
        userDetails.forEach(userDetailsObj -> {
            try {
                authenticationManagerBuilder.inMemoryAuthentication()
                        .withUser(userDetailsObj.getUserName())
                        .password(userDetailsObj.getPassword())
                        .roles(userDetailsObj.getRole());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * This method is used to configure the api endpoint according to roles
     *
     * @param http : http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/file/uploadFile")
                .hasRole(ConstantsUtils.ADMIN_ROLE)
                .antMatchers("/files/checkStatus/{fileId}")
                .hasRole(ConstantsUtils.ADMIN_ROLE)
                .antMatchers("/files/getUploadedFiles")
                .hasAnyRole(ConstantsUtils.ADMIN_ROLE, ConstantsUtils.USER_ROLE)
                .antMatchers("/files/deleteById/{fileId}")
                .hasAnyRole(ConstantsUtils.ADMIN_ROLE)
                .antMatchers("/files/{fileId}")
                .hasAnyRole(ConstantsUtils.ADMIN_ROLE, ConstantsUtils.USER_ROLE)
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

}
