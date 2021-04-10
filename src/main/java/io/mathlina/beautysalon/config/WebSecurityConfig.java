package io.mathlina.beautysalon.config;

import java.nio.charset.StandardCharsets;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private DataSource dataSource;

  @Autowired
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Bean
  public StringHttpMessageConverter stringHttpMessageConverter() {
    return new StringHttpMessageConverter(StandardCharsets.UTF_8);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/resources/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
//    CharacterEncodingFilter filter = new CharacterEncodingFilter();
//    filter.setEncoding("UTF-8");
//    filter.setForceEncoding(true);
//    http.addFilterBefore(filter, CsrfFilter.class);

    http
        .authorizeRequests()
        .antMatchers("/", "/login", "/registration", "/static/**", "/activate/*").permitAll()
        .anyRequest().permitAll()
        .and()
        .formLogin()
        .loginPage("/login").permitAll()
        .and().rememberMe()
        .and()
        .logout().permitAll();

    //TODO: enable csrf
    http.csrf().disable();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.jdbcAuthentication()
        .dataSource(dataSource)
        .usersByUsernameQuery("select username, password, active from usr where username=?")
        .authoritiesByUsernameQuery(
            "select u.username, ur.roles from usr u inner join user_role ur "
                + "on u.id = ur.user_id where u.username=?");
  }


}
