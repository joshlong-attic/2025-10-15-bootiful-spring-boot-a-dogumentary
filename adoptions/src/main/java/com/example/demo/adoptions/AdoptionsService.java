package com.example.demo.adoptions;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class AdoptionsService {

    private final DogRepository dogRepository;
    private final ApplicationEventPublisher publisher;

    AdoptionsService(DogRepository dogRepository, ApplicationEventPublisher publisher1) {
        this.dogRepository = dogRepository;
        this.publisher = publisher1;
    }

    public void adopt(int dogId, String owner) {
        this.dogRepository.findById(dogId).ifPresent(doggo -> {
            var updated = this.dogRepository.save(new Dog(dogId, doggo.name(), doggo.owner(), doggo.description()));
            IO.println("updated: " + updated);
        });
        this.publisher.publishEvent(new DogAdoptedEvent(dogId));
    }
}
