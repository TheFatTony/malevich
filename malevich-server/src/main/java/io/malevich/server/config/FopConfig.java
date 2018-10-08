package io.malevich.server.config;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.FopFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;


@Configuration
public class FopConfig {


    @Bean
    public FopFactory fopFactory() {
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        return fopFactory;
    }

    @Bean
    public FOUserAgent foUserAgent() {
        FOUserAgent foUserAgent = fopFactory().newFOUserAgent();
        return foUserAgent;
    }

}
