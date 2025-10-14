package com.example.demo.adoptions;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
class AdoptionsController {

    private final AdoptionsService adoptionsService;

    AdoptionsController(AdoptionsService adoptionsService) {
        this.adoptionsService = adoptionsService;
    }

    @GetMapping("/dogs/{dogId}/adoptions")
    void adopt(@PathVariable int dogId, @RequestParam String owner) {
        this.adoptionsService.adopt(dogId, owner);
    }
}
