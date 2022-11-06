 package com.intrack.checkinservice.config;


 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.web.cors.CorsConfiguration;
 import org.springframework.web.cors.reactive.CorsWebFilter;
 import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

 import java.util.List;

 import static java.util.Arrays.asList;

 @Configuration
 public class CORSConfig   {

     private static final List<String> allowedHeaders = asList("Content-Type", "X-Requested-With", "Accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization", "Access-Control-Allow-Origin");
     private static final List<String> exposedHeaders = asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");
     private static final List<String> allowedMethods = asList("GET", "POST", "HEAD", "OPTIONS", "PUT", "PATCH", "DELETE", "LINK", "UNLINK");

     @Bean
     public static CorsWebFilter corsWebFilter() {
         UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
         CorsConfiguration config = new CorsConfiguration();

         config.setAllowCredentials(true);
         config.addAllowedOriginPattern("*");
         config.setAllowedHeaders(allowedHeaders);
         config.setExposedHeaders(exposedHeaders);
         config.setAllowedMethods(allowedMethods);

         source.registerCorsConfiguration("/**", config);
         return new CorsWebFilter(source);
     }

 }
