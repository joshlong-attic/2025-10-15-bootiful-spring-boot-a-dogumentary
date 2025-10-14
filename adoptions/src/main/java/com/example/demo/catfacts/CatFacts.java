package com.example.demo.catfacts;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@ImportHttpServices(CatFactsClient.class)
class CatFacts {
}
