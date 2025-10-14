package com.example.demo.adoptions;

import org.springframework.beans.factory.BeanRegistrar;
import org.springframework.beans.factory.BeanRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@Configuration
@Import(Adoptions.AdoptionsBeanRegistrar.class)
class Adoptions {

    static class AdoptionsBeanRegistrar implements BeanRegistrar {

        @Override
        public void register(BeanRegistry registry, Environment env) {
            registry.registerBean(AdoptionsService.class);
        }
    }
}
