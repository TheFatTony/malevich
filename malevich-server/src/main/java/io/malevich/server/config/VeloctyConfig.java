package io.malevich.server.config;


import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VeloctyConfig {


    @Bean
    public VelocityEngine getVelocityEngine() {
        VelocityEngine factory = new VelocityEngine();
        factory.addProperty("resource.loader", "class");
        factory.addProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return factory;
    }
}
