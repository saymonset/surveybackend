package com.security_delete;

/**
 * Created by simon on 3/25/2019.
 */

/*
import static com.security.SecurityConstants.SEND_ENCUESTAS;
import static com.security.SecurityConstants.SENT_SURVEY;
import static com.security.SecurityConstants.SENT_RESULT;
*/


/*@EnableGlobalMethodSecurity
@EnableWebSecurity*/
public class WebSecurity {
    //
/* extends WebSecurityConfigurerAdapter {
    private UserServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    @Bean
    public JwtTokenFilter jwtTokenFilter(){
        return new JwtTokenFilter();
    }

    public WebSecurity(UserServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, AUTHENTICATE).permitAll()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
              //  .antMatchers(HttpMethod.POST, SEND_ENCUESTAS).permitAll()
                .antMatchers(HttpMethod.GET, DATAFILL).permitAll()
              //  .antMatchers(HttpMethod.GET, "/tree/territorial").permitAll()
              // .antMatchers(HttpMethod.GET, "/tree/servicio").permitAll()
            //    .antMatchers(HttpMethod.POST, "/search/nps*").permitAll()
                //.antMatchers(HttpMethod.GET, "/chart/nps*").permitAll()
               *//* .antMatchers(HttpMethod.GET, SENT_SURVEY).permitAll()
                .antMatchers(HttpMethod.POST, SENT_RESULT).permitAll()*//*

                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("*//**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }*/


}