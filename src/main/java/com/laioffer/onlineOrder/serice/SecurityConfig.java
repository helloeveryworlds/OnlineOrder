package com.laioffer.onlineOrder.serice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

//https://docs.spring.io/spring-security/site/docs/5.5.5/reference/html5/
// 告诉spring这个是dependency，
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()//跨域访问local 开发的时候disable，web的时候就able
                .formLogin()//可以用其他login，比如facebook账户
                .failureForwardUrl("/login?error=true");//告诉customer 一个返回的内容
        http
                .authorizeRequests()
                //这些check必须要Authentication而且Authorization by users
                .antMatchers("/order/*", "/cart", "/checkout").hasAuthority("ROLE_USER")
                // 其他的就在这儿，login就是在这边，还没login没发Authorization， menu没必要Authorization。
                .anyRequest().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // user信息存在mysql里面。都是用jdbc（支持不同的数据库）。
        // 就是说Authorization通过database实现的
        //如果是mongodatabase，那就没必要用了
        //当然有第三方的数据库就更加懒人了，就对方给你提供API
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                // 帮你做一些， 每一个人的application的database scheme都是不一样的，设计一个query，query到用户名和密码，还有一些enable 的query。
                .usersByUsernameQuery("SELECT email, password, enabled FROM customers WHERE email=?")
                //真正的去check有没有user row。
                .authoritiesByUsernameQuery("SELECT email, authorities FROM authorities WHERE email=?");

    }

    @Bean
    //不想人铭文看到password
    //如果被人黑了数据库，那么看到这些加密的密码，别人不会知道密码。
    //把常用的密码加到字典里面去，把hash过的密码反过来推理
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}

