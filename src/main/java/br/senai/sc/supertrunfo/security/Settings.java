package br.senai.sc.supertrunfo.security;

import br.senai.sc.supertrunfo.security.service.JpaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@EnableWebSecurity
@Configuration
// O EnableMethodSecurity é para habilitar a segurança nos métodos, para que seja possível utilizar as anotações
@EnableMethodSecurity
public class Settings {

    private JpaService jpaService;

    // Configuração do CORS, Busca os usuários que possuem acesso ao sistema
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jpaService).passwordEncoder(new BCryptPasswordEncoder());
    }

    // Configuração do CORS
    // É a configuração do AuthenticationManager, que é o responsável por autenticar o usuário
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // Filter para permitir o CORS
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorization) ->
                authorization
                        // hasAuthority é para verificar se o usuário possui a autorização para acessar a rota
                        .requestMatchers(HttpMethod.POST, "/carta/create").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/carta/delete/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/carta/update/**").hasAuthority("ADMIN")
                        // hasAnyAuthority é para verificar se o usuário possui alguma das autorizações para acessar a rota
                        // permitAll é para permitir que qualquer usuário acesse a rota
                        // authenticated é para verificar se o usuário está autenticado
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .anyRequest().permitAll()
        );
        // Ele não armazena estado de sessão (Stateless)
        // A utilização do STATELESS é para que não fique fazendo diretamente as autentificações
        // Retira atribuição de manter sessões no servidor, para que crie uma estratégia de autenticação
        // Caso não configurado o padrão é ALWAYS
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        // O filter é para verificar se o usuário está autenticado e se precisa de autorização
        http.addFilterBefore(new Filter(), UsernamePasswordAuthenticationFilter.class);
        http.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        List<UserDetails> users = new ArrayList<>();
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        UserDetails user = User.builder()
//                .username("admin")
//                .password(encoder.encode("admin"))
//                .roles("ADMIN")
//                .build();
//        UserDetails user2 = User.builder()
//                .username("admin")
//                .password(encoder.encode("admin"))
//                .roles("ADMIN")
//                .build();
//        users.add(user);
//        users.add(user2);
//        return new InMemoryUserDetailsManager(users);
//    }

}
