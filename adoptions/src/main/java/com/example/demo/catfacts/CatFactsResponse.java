package com.example.demo.catfacts;

import java.util.Collection;

record CatFactsResponse(Collection<CatFact> facts) {
}
