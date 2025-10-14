package com.example.demo.adoptions;

import org.springframework.data.annotation.Id;

record Dog(@Id int id, String name, String owner, String description) {
}
