package com.github.dmitritim.app;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = "/secured")
public class SecuredResource {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userHello(Principal principal) {

        return "Hello " + principal.getName() + "!";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminHello(Principal principal) {

        return "Hello " + principal.getName() + "!";
    }
}
