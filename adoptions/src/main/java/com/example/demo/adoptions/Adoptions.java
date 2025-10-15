package com.example.demo.adoptions;

import org.springframework.beans.factory.BeanRegistrar;
import org.springframework.beans.factory.BeanRegistry;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
//@Import(AdoptionsBeanRegistrar.class)
class Adoptions {

    @Bean
    AdoptionsService adoptionsService(DogRepository repository, ApplicationEventPublisher publisher) {
        return new AdoptionsService(repository, publisher);
    }
}

class AdoptionsBeanRegistrar implements BeanRegistrar {

    @Override
    public void register(BeanRegistry registry, Environment env) {
        registry
                .registerBean(AdoptionsService.class, as -> as
                        .supplier(supplierContext -> new AdoptionsService(supplierContext.bean(DogRepository.class),
                                supplierContext.bean(ApplicationEventPublisher.class))));
    }
}