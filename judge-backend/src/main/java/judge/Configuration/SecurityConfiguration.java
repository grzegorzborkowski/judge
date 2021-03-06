package judge.Configuration;

import judge.Component.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static String REALM="JUDGE_REALM";

    @Autowired
    private CustomAuthenticationProvider authProvider;


    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/submission/getAllForUser").hasAnyAuthority("student", "teacher", "admin")
                .antMatchers("/submission/getAll").hasAnyAuthority("teacher", "admin")
                .antMatchers("/user/add").hasAnyAuthority("teacher", "admin")
                .antMatchers("/user/addMultipleStudents").hasAnyAuthority("teacher", "admin")
                .antMatchers("/user/addOneStudent").hasAnyAuthority("teacher", "admin")
                .antMatchers("/user/addTeacher").hasAnyAuthority("teacher", "admin")
                .antMatchers("/problems/add").hasAnyAuthority("teacher", "admin")
                .antMatchers("/problems/remove?id=").hasAnyAuthority("teacher", "admin")
                .antMatchers("/judge/submit").hasAnyAuthority("student", "teacher", "admin")
                .and().httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//We don't need sessions to be created.
    }

    @Autowired
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new CustomBasicAuthenticationEntryPoint();
    }

    /* To allow Pre-flight [OPTIONS] request from browser */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
    }
}
