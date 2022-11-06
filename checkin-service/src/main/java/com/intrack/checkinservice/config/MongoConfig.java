 package com.intrack.checkinservice.config;

 import com.mongodb.MongoClientSettings;
 import com.mongodb.MongoCredential;
 import com.mongodb.ServerAddress;
 import com.mongodb.reactivestreams.client.MongoClient;
 import com.mongodb.reactivestreams.client.MongoClients;
 import org.springframework.beans.factory.annotation.Value;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

 import java.util.Arrays;
 import java.util.List;
 import java.util.stream.Collectors;

 @Configuration
 public class MongoConfig {

     @Value("${mongodb.name}")
     private String mongoDBName;
     @Value("${mongodb.port}")
     private Integer mongoDBPort;
     @Value("${mongodb.host}")
     private String mongoDBHost;
     @Value("${mongodb.user}")
     private String mongoDBUser;
     @Value("${mongodb.password}")
     private String mongoDBPassword;

     @Bean
     public MongoClient mongoClient() {
         try {
             final MongoCredential credential = MongoCredential
                     .createScramSha1Credential(mongoDBUser, mongoDBName, mongoDBPassword.toCharArray());

             final MongoClientSettings mongoClientSettings = MongoClientSettings
                     .builder()
                     .credential(credential)
                     .applyToClusterSettings(builder ->
                             builder.hosts(getServerAddresses(mongoDBHost, mongoDBPort, ",")))
                     .build();

             return MongoClients.create(mongoClientSettings);
         } catch (final Exception e) {
             throw new InternalError(e.getMessage());
         }
     }

     private List<ServerAddress> getServerAddresses(String mongoDBHost, int mongoDBPort, String splitter) {
         return Arrays.stream(mongoDBHost.split(splitter))
                      .map(host -> new ServerAddress(host, mongoDBPort))
                      .collect(Collectors.toList());
     }

     @Bean
     public ReactiveMongoTemplate reactiveMongoTemplate() {
         return new ReactiveMongoTemplate(mongoClient(), mongoDBName);
     }
 }
