package com.example.HealthX.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.util.UUID;

@Configuration
public class AuthorizationServerConfiguration {

    @Bean
    @Order(1)
    public SecurityFilterChain asSecurityFilterChain(HttpSecurity http) throws Exception {

        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                new OAuth2AuthorizationServerConfigurer();

        http
                // accepta numai url related cu OAuth2
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())

                // activeaza toate configurarile legate de OAuth2 si activeaza OpenID Connect
                .with(authorizationServerConfigurer ,configurer ->
                        configurer.oidc(Customizer.withDefaults()))

                // in cazul in care da eroare in ceea ce consta authentificarea, redirect catre pagina de login
                .exceptionHandling(e ->
                        e.authenticationEntryPoint(
                                new LoginUrlAuthenticationEntryPoint("/login")
                        ));

        return http.build();

    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.formLogin(Customizer.withDefaults());

        http.authorizeHttpRequests(authorize ->
                authorize.anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        UserDetails user = User.withUsername("user")
                            .password("password")
                            .authorities("read")
                .build();

        manager.createUser(user);

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {

        RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("client-id")
                .clientSecret("secret")
                .clientAuthenticationMethod(
                        ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://localhost:8080/login/oauth2/code/my-client")
                .redirectUri("http://localhost:8080/login/oauth2/code/my-client")
                .redirectUri("http://localhost:8080/callback")
                .scope("PROJECT")
                .build();

        return new InMemoryRegisteredClientRepository(client);
    }

}
