package com.example.demo.vet;

import com.example.demo.adoptions.DogAdoptedEvent;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class Dogtor {

    @ApplicationModuleListener
    void on(DogAdoptedEvent dogAdoptedEvent) throws Exception {
        IO.println("scheduling checkup for " + dogAdoptedEvent + "!");
    }
}
