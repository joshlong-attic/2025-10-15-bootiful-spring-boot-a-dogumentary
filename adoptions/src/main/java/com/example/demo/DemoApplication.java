package com.example.demo;

import org.jspecify.annotations.Nullable;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.data.jdbc.core.dialect.JdbcPostgresDialect;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.resilience.annotation.EnableResilientMethods;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authorization.EnableGlobalMultiFactorAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.FactorGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import tools.jackson.databind.jsontype.NamedType;

import java.util.Set;

@EnableResilientMethods
@ImportRuntimeHints(DemoApplication.Hints.class)
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    static class Hints implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, @Nullable ClassLoader classLoader) {
            hints.reflection().registerType(NamedType.class, MemberCategory.values());
        }
    }

    @Bean
    JdbcPostgresDialect jdbcPostgresDialect() {
        return JdbcPostgresDialect.INSTANCE;
    }


}

@EnableGlobalMultiFactorAuthentication(authorities = {
        FactorGrantedAuthority.OTT_AUTHORITY,
        FactorGrantedAuthority.PASSWORD_AUTHORITY
})
@Configuration
class SecurityConfiguration {

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder pw) {
        var users = Set.of(
                User.withUsername("josh").roles("USER").password(pw.encode("pw")).build(),
                User.withUsername("rob").roles("USER").password(pw.encode("pw")).build()
        );
        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    Customizer<HttpSecurity> httpSecurityCustomizer() {
        return httpSecurity -> httpSecurity
                .oneTimeTokenLogin(ott ->
                        ott
                                .tokenGenerationSuccessHandler((request, response, oneTimeToken) -> {
                                    response.getWriter().println("you've got console mail!");
                                    response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
                                    IO.println("please go to http://localhost:8080/login/ott?token=" +
                                            oneTimeToken.getTokenValue());
                                }))
                .webAuthn(w -> w
                        .allowedOrigins("http://localhost:8080")
                        .rpName("bootiful")
                        .rpId("localhost")
                );
    }
}