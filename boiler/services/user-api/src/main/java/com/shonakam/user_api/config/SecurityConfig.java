package com.shonakam.user_api.config;


// import java.util.List;

// import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.firewall.HttpFirewall;
// import org.springframework.boot.autoconfigure.security.servlet.PathRequest;

@Profile("production")
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf((csrf) -> csrf
					.ignoringRequestMatchers("/**")
			)
	
			.authorizeHttpRequests(customizer -> customizer
					// .requestMatchers("/api/auth").authenticated()
					.requestMatchers("/**", "/health").permitAll()  // すべてのリクエストを許可
					// .anyRequest().denyAll()
			);
		return http.build();
	}
}
