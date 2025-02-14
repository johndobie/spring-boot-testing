package com.johndobie.springboot.testing.cheatsheet.controller;

import com.johndobie.springboot.testing.cheatsheet.controller.swagger.EchoControllerSwagger;
import com.johndobie.springboot.testing.cheatsheet.model.Message;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class EchoController implements EchoControllerSwagger {

    public static final String ECHO_POST_ENDPOINT = "/api/echo";

    @Override
    @PostMapping(ECHO_POST_ENDPOINT)
    public Message echo(@Valid @RequestBody Message message) {
        return message;
    }

}
