package com.example.demo.catfacts;

import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.service.annotation.GetExchange;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@ResponseBody
class CatFactsController {

    private final AtomicInteger counter = new AtomicInteger(0);

    private final CatFactsClient factsClient;

    CatFactsController(CatFactsClient factsClient) {
        this.factsClient = factsClient;
    }

    @GetExchange("/facts")
    @ConcurrencyLimit(10)
    @Retryable(maxAttempts = 5, includes = {ConnectionError.class})
    Collection<CatFact> facts() {

        if (this.counter.incrementAndGet() < 4) {
            IO.println("error");
            throw new ConnectionError();
        }

        IO.println("got the facts!");

        return this.factsClient.facts().facts();
    }
}
