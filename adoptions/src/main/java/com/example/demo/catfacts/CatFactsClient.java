package com.example.demo.catfacts;

import org.springframework.web.service.annotation.GetExchange;

interface CatFactsClient {

    @GetExchange("https://catfacts.net/api")
    CatFactsResponse facts();

}
