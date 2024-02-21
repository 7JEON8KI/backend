package com.hyundai.domain.login.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
public class TestController {

    @RequestMapping("/all")
    public ResponseEntity<String> testAll(){
        return ResponseEntity.ok().body("Mealkeat api all ok");
    }
    @RequestMapping("/admin")
    public ResponseEntity<String> testAdmin(){
        return ResponseEntity.ok().body("Mealkeat api admin ok");
    }
    @RequestMapping("/manager")
    public ResponseEntity<String> testManager(){
        return ResponseEntity.ok().body("Mealkeat api manager ok");
    }
    @RequestMapping("/member")
    public ResponseEntity<String> testMember(){
        return ResponseEntity.ok().body("Mealkeat api member ok");
    }
}