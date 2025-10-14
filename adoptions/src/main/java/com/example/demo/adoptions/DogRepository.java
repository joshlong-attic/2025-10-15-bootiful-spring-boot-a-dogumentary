package com.example.demo.adoptions;

import org.jspecify.annotations.NonNull;
import org.springframework.data.repository.ListCrudRepository;

interface DogRepository extends ListCrudRepository<@NonNull Dog, @NonNull Integer> {
}
